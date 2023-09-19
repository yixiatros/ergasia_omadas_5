package com.omada5.ergasia_omadas_5.task;

import com.omada5.ergasia_omadas_5.bidding.Offer;
import com.omada5.ergasia_omadas_5.bidding.OfferRepository;
import com.omada5.ergasia_omadas_5.user.User;
import com.omada5.ergasia_omadas_5.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private  final SubcategoryRepository subcategoryRepository;
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final TaskCommentRepository taskCommentRepository;

    @GetMapping
    public List<Task> getTasks(){
        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findTaskById(taskId);
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoriesById(Long id) {
        return categoryRepository.getReferenceById(id);
    }

    public List<Subcategory> getSubCategories() {
        return subcategoryRepository.findAll();
    }

    public Subcategory getSubcategoriesById(Long id) {
        return subcategoryRepository.getReferenceById(id);
    }

    public void addNewTask(Task task){
        taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        boolean exists = taskRepository.existsById(taskId);

        if(!exists)
            throw new IllegalStateException("Task with id " + taskId + " does not exist.");

        taskRepository.deleteById(taskId);
    }

    public List<Task> searchTask(String search){
        return taskRepository.findTasksBySearch(search);
    }

    public List<Offer> getActiveOffersOfTask(Long taskId){
        return taskRepository.findActiveOffersOfTaskById(taskId);
    }

    public List<Offer> getOffersOfUser(Long taskId, String username) {
        return taskRepository.findOffersOfUserToTask(taskId, username);
    }

    public void saveOffer(float offerPrice, Task task) {
        Optional<User> userOptional = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!userOptional.isPresent())
            return;

        List<Offer> offers = getOffersOfUser(task.getId(), userOptional.get().getUsername());
        for (Offer o : offers)
            if (o.isActive())
                o.setActive(false);
        offerRepository.saveAll(offers);

        var offer = Offer.builder()
                .task(task)
                .bidder(userOptional.get())
                .price(offerPrice)
                .offerDateTime(LocalDateTime.now())
                .isActive(true)
                .build();
        offerRepository.save(offer);

        offers = getActiveOffersOfTask(task.getId());
        Offer maxOffer = offers.stream().min(Comparator.comparing(Offer::getPrice)).get();
        task.setActiveLowestPrice(maxOffer.getPrice());
        taskRepository.save(task);
    }

    public void deleteOffers(Long taskId) {
        Optional<User> userOptional = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!userOptional.isPresent())
            return;

        List<Offer> offers = getOffersOfUser(taskId, userOptional.get().getUsername());
        for (Offer o : offers)
            if (o.isActive())
                o.setActive(false);
        offerRepository.saveAll(offers);
    }

    public List<TaskComment> getCommentsOfTask(Long taskId) {
        return taskRepository.findCommentsOfTask(taskId);
    }

    public void saveComment(String comment, Task task) {
        Optional<User> userOptional = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!userOptional.isPresent())
            return;

        var taskComment = TaskComment.builder()
                .task(task)
                .commenter(userOptional.get())
                .comment(comment)
                .dateTime(LocalDateTime.now())
                .build();
        taskCommentRepository.save(taskComment);
    }
}
