package com.omada5.ergasia_omadas_5.task;

import com.omada5.ergasia_omadas_5.bidding.Offer;
import com.omada5.ergasia_omadas_5.bidding.OfferRepository;
import com.omada5.ergasia_omadas_5.notification.Notification;
import com.omada5.ergasia_omadas_5.notification.NotificationRepository;
import com.omada5.ergasia_omadas_5.user.User;
import com.omada5.ergasia_omadas_5.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskRepository taskRepository;
    private final UserService userService;
    private final OfferRepository offerRepository;
    private final NotificationRepository notificationRepository;

    public List<Task> getTasks(){
        List<Task> allTasks = taskService.getTasks();
        List<Task> publicTasks = new ArrayList<>();
        for (Task task : allTasks)
            if (task.isPublic())
                publicTasks.add(task);
        return publicTasks;
    }

    @GetMapping("/tasks")
    public String showTasks(Model model){
        putUsername(model);

        List<Task> tasks = getTasks();
        List<Category> categories = taskService.getCategories();
        List<Subcategory> subcategories = taskService.getSubCategories();
        model.addAttribute("tasks", tasks);
        model.addAttribute("tasksSize", tasks.size());
        model.addAttribute("categories", categories);
        model.addAttribute("subcategories", subcategories);
        model.addAttribute("languages", AvailableLanguages.values());
        List<String> technologies = new ArrayList<>();
        for (var proposedTechnology : ProposedTechnologies.values()) {
            technologies.add(proposedTechnology.getTechnology());
        }
        model.addAttribute("proposedTechnologies", technologies);
        return "task_search";
    }

    @GetMapping("/task_search")
    public String searchTask(@RequestParam("keyword") String keyword,
                             @RequestParam(name = "category", defaultValue = "-1") List<Long> categoryId,
                             @RequestParam(name = "subcategory", defaultValue = "-1") List<Long> subcategoryId,
                             @RequestParam(name = "language", defaultValue = "empty") List<String> languages,
                             @RequestParam(name = "technology", defaultValue = "empty") List<String> technologies,
                             Model model){

        putUsername(model);

        List<Task> tasks = new ArrayList<>();
        if (keyword.equals(""))
            tasks = taskService.getTasks();
        else
            tasks = taskService.searchTask(keyword);

        List<String> tech = new ArrayList<>();
        for (var proposedTechnology : ProposedTechnologies.values()) {
            tech.add(proposedTechnology.getTechnology());
        }

        tasks = filterTasksByCategory(tasks, categoryId);
        tasks = filterTasksBySubcategory(tasks, subcategoryId);
        tasks = filterTasksByLanguage(tasks, languages);
        tasks = filterTasksByTechnologies(tasks, technologies);
        List<Category> categories = taskService.getCategories();
        List<Subcategory> subcategories = taskService.getSubCategories();
        model.addAttribute("tasks", tasks);
        model.addAttribute("tasksSize", tasks.size());
        model.addAttribute("categories", categories);
        model.addAttribute("subcategories", subcategories);
        model.addAttribute("languages", AvailableLanguages.values());
        model.addAttribute("proposedTechnologies", tech);
        List<Category> appliedCategories = new ArrayList<>();
        for (Long id:
             categoryId) {
            appliedCategories.add(taskService.getCategoriesById(id));
        }
        model.addAttribute("appliedCategories", appliedCategories);
        List<Subcategory> appliedSubcategories = new ArrayList<>();
        for (Long id:
                subcategoryId) {
            appliedSubcategories.add(taskService.getSubcategoriesById(id));
        }
        model.addAttribute("appliedSubcategories", appliedSubcategories);
        return "task_search";
    }

    private List<Task> filterTasksByTechnologies(List<Task> tasks, List<String> technologies) {
        if (technologies.get(0).equals("empty"))
            return tasks;

        if (technologies.size() == 0)
            return tasks;

        List<Task> newTasks = new ArrayList<>();
        for (Task task: tasks) {
            for (String technology: technologies) {
                if (task.getProposedTechnology().getTechnology().equals(technology)){
                    newTasks.add(task);
                    break;
                }
            }
        }

        return newTasks;
    }

    private List<Task> filterTasksByLanguage(List<Task> tasks, List<String> languages) {
        if (languages.get(0).equals("empty"))
            return tasks;

        if (languages.size() == 0)
            return tasks;

        List<Task> newTasks = new ArrayList<>();
        for (Task task: tasks) {
            for (String language: languages) {
                if (task.getAvailableLanguage().toString().equals(language)){
                    newTasks.add(task);
                    break;
                }
            }
        }

        return newTasks;
    }

    private List<Task> filterTasksByCategory(List<Task> tasks, List<Long> categoryId){
        if (categoryId.get(0) == -1)
            categoryId.remove(0);

        if (categoryId.size() == 0)
            return tasks;

        List<Category> categories = new ArrayList<>();
        for (Long id:
             categoryId) {
            categories.add(taskService.getCategoriesById(id));
        }


        List<Task> newTasks = new ArrayList<>();
        for (Task task: tasks) {
            for (Category category:
                 categories) {
                if (task.getCategory() == category){
                    newTasks.add(task);
                    break;
                }
            }
        }

        return newTasks;
    }

    private List<Task> filterTasksBySubcategory(List<Task> tasks, List<Long> subcategoryId) {
        if (subcategoryId.get(0) == -1)
            subcategoryId.remove(0);

        if (subcategoryId.size() == 0)
            return tasks;

        List<Subcategory> subcategories = new ArrayList<>();
        for (Long id:
                subcategoryId) {
            subcategories.add(taskService.getSubcategoriesById(id));
        }

        List<Task> newTasks = new ArrayList<>();
        for (Task task:
                tasks) {

            for (Subcategory subcategory :
                    subcategories) {
                if (task.getSubcategory() == subcategory) {
                    newTasks.add(task);
                    break;
                }
            }
        }

        return newTasks;
    }

    @GetMapping("/task_create")
    public String createTask(Model model){
        putUsername(model);

        model.addAttribute("currentDateTime", LocalDate.now());
        List<Category> categories = taskService.getCategories();
        List<Subcategory> subcategories = taskService.getSubCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("subcategories", subcategories);
        model.addAttribute("languages", AvailableLanguages.values());
        List<String> technologies = new ArrayList<>();
        for (var proposedTechnology : ProposedTechnologies.values()) {
            technologies.add(proposedTechnology.getTechnology());
        }
        model.addAttribute("proposedTechnologies", technologies);
        return "task_create";
    }

    public List<Subcategory> getSubcategoriesByCategories(Long categoryId){
        List<Subcategory> subcategories = taskService.getSubCategories();
        List<Subcategory> newSubcategories = new ArrayList<>();
        for (Subcategory sub:
             subcategories) {
            if (sub.getCategory() != null)
                if (Objects.equals(sub.getCategory().getId(), categoryId))
                    newSubcategories.add(sub);
        }
        return newSubcategories;
    }

    @PostMapping("/task_create")
    public RedirectView setCreatedTask(@RequestParam("title") String title,
                                       @RequestParam("description") String description,
                                       @Param("isPublic") boolean isPublic,
                                       @Param("showPrice") boolean showPrice,
                                       @Param("isPayingByTheHour") boolean isPayingByTheHour,
                                       @RequestParam("creatorUsername") String creatorUsername,
                                       @RequestParam("maxPrice") float maxPrice,
                                       @RequestParam("endDate") LocalDateTime endDate,
                                       @RequestParam("category") String categoryId,
                                       @RequestParam(value = "subcategory", required = false, defaultValue = "-1") String subcategoryId,
                                       @RequestParam(name = "language", defaultValue = "") String language,
                                       @RequestParam(name = "technology", defaultValue = "") String technology){

        Optional<Task> userOptional = taskService.getTasks().stream()
                .filter(u->title.equals(u.getTitle()))
                .findAny();

        if (userOptional.isPresent())
            return new RedirectView("/task_search");

        Task task = new Task(
                title,
                description,
                isPublic,
                showPrice,
                isPayingByTheHour,
                maxPrice,
                endDate
        );

        Category category = taskService.getCategoriesById(Long.valueOf(categoryId));
        task.setCategory(category);

        if (!subcategoryId.equals("-1")){
            Subcategory subcategory = taskService.getSubcategoriesById(Long.valueOf(subcategoryId));
            task.setSubcategory(subcategory);
        }

        Optional<User> user = userService.getUserByUsername(creatorUsername);
        user.ifPresent(task::setCreator);

        if (!language.equals(""))
            task.setAvailableLanguage(AvailableLanguages.valueOf(language));

        if (!technology.equals(""))
            for (var proposedTechnology : ProposedTechnologies.values())
                if (proposedTechnology.getTechnology().equals(technology)){
                    task.setProposedTechnology(proposedTechnology);
                    break;
                }

        taskService.addNewTask(task);

        return new RedirectView("/task_view/" + task.getId());
    }

    @PostMapping(path = "/task_view/{taskId}/change_info")
    public RedirectView changeTaskInfo(@PathVariable("taskId") Long taskId,
                                       @RequestParam(name = "title", defaultValue = "empty") String title,
                                       @RequestParam(name = "description", defaultValue = "empty") String description,
                                       @Param("isPublic") boolean isPublic,
                                       @Param("showPrice") boolean showPrice,
                                       @RequestParam(name = "maxPrice", defaultValue = "-1") float maxPrice,
                                       @Param("endDate") LocalDateTime endDate,
                                       RedirectAttributes redirectAttributes) {
        Optional<Task> optionalTask = taskService.getTaskById(taskId);
        if (!optionalTask.isPresent()){
            redirectAttributes.addFlashAttribute("somethingWentWrong", true);
            return new RedirectView("/index");
        }

        Task task = optionalTask.get();

        if (!title.equals("empty"))
            task.setTitle(title);

        if (!description.equals("empty"))
            task.setDescription(description);

        task.setPublic(isPublic);
        task.setShowPrice(showPrice);

        if (maxPrice != -1)
            task.setMaxPrice(maxPrice);

        if (endDate != null)
            task.setEndDate(endDate);

        taskRepository.save(task);

        redirectAttributes.addFlashAttribute("successfulChange", true);
        return new RedirectView("/task_view/" + taskId);
    }

    @GetMapping(path = "/task_view/{taskId}")
    public String seeTask(@PathVariable("taskId") Long taskId,
                          Model model,
                          HttpServletResponse httpServletResponse,
                          RedirectAttributes redirectAttributes) throws IOException {

        putUsername(model);

        model.addAttribute("currentDateTime", LocalDate.now());

        Optional<Task> optionalTask = taskService.getTaskById(taskId);
        if (!optionalTask.isPresent()){
            redirectAttributes.addFlashAttribute("somethingWentWrong", true);
            httpServletResponse.sendRedirect("/index");
            return null;
        }
        Task task = optionalTask.get();

        model.addAttribute("task", task);
        model.addAttribute("endDate", task.getEndDate());

        Optional<User> userOptional = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!userOptional.isPresent())
            return "task_view";

        List<Offer> thisUserOffers = taskService.getOffersOfUser(taskId, userOptional.get().getUsername());
        for (Offer o : thisUserOffers) {
            if (o.isActive()) {
                model.addAttribute("myActiveOffer", o.getPrice());
                break;
            }
        }

        List<Offer> offers = taskService.getActiveOffersOfTask(taskId);
        if (offers.size() > 0){
            model.addAttribute("offers", offers);
        }

        List<TaskComment> taskComments = taskService.getCommentsOfTask(taskId);
        model.addAttribute("taskComments", taskComments);

        return "task_view";
    }

    @PostMapping(path = "/task_view/hire")
    public RedirectView hireDeveloper(@RequestParam("hireBidderId") Long hireBidderId, @RequestParam("currentTask") Long taskId, RedirectAttributes redirectAttributes) {
        Optional<User> currentUserOptional = userService.getUserByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        if (!currentUserOptional.isPresent())
            return new RedirectView("/index");

        List<Notification> notifications = notificationRepository.findNotificationByUserIdAndTaskTitle(hireBidderId, taskService.getTaskById(taskId).get().getTitle());
        if (notifications.size() > 0){
            redirectAttributes.addFlashAttribute("hireBidderUserHireRequestFail", userService.getUserById(hireBidderId));
            return new RedirectView("/task_view/" + taskId);
        }

        User currentUser = currentUserOptional.get();
        userService.saveNotification(
                userService.getUserById(hireBidderId).get(),
                taskService.getTaskById(taskId).get(),
                "Hire request at task '" + taskService.getTaskById(taskId).get().getTitle() + "'.",
                "User '" + currentUser.getUsername() + "' has elected you to develop his/her task.",
                false,
                true
        );

        redirectAttributes.addFlashAttribute("hasSendHireRequest", true);

        return new RedirectView("/task_view/" + taskId.toString());
    }

    @GetMapping(path = "/task_view/{taskId}/accept_hire/{userId}/{notificationId}")
    public RedirectView acceptHireRequest(@PathVariable("taskId") Long taskId,
                                          @PathVariable("userId") Long userId,
                                          @PathVariable("notificationId") Long notificationId,
                                          RedirectAttributes redirectAttributes) {
        Optional<Task> taskOptional = taskService.getTaskById(taskId);
        if (!taskOptional.isPresent())
            return new RedirectView("/index");

        Task task = taskOptional.get();
        if (task.getAssignedDeveloper() != null){
            userService.deleteNotification(userService.getNotificationById(notificationId).get());
            redirectAttributes.addFlashAttribute("hasAnotherAccepted", true);
            return new RedirectView("/task_view/" + taskId);
        }

        User assignUser = userService.getUserById(userId).get();
        task.setAssignedDeveloper(assignUser);
        taskRepository.save(task);
        userService.deleteNotification(userService.getNotificationById(notificationId).get());

        userService.saveNotification(
                task.getCreator(),
                "Hire request ACCEPTED!",
                "User '" + assignUser.getUsername() + "' has ACCEPTED your hire request at task '" + task.getTitle() + "'. <a href=\"http://localhost:8080/task_view/" + taskId + "\" style=\"text-decoration: underline; cursor: pointer; color: blue;\">click here to go to the task</a>",
                true,
                false
        );

        return new RedirectView("/task_view/" + taskId.toString());
    }

    @GetMapping(path = "/task_view/{taskId}/reject_hire/{userId}/{notificationId}")
    public RedirectView rejectHireRequest(@PathVariable("taskId") Long taskId, @PathVariable("userId") Long userId, @PathVariable("notificationId") Long notificationId) {
        Optional<Task> taskOptional = taskService.getTaskById(taskId);
        if (!taskOptional.isPresent())
            return new RedirectView("/index");
        Task task = taskOptional.get();

        User assignUser = userService.getUserById(userId).get();
        userService.deleteNotification(userService.getNotificationById(notificationId).get());

        userService.saveNotification(
                task.getCreator(),
                "Hire request REJECTED.",
                "User '" + assignUser.getUsername() + "' has REJECTED your hire request at task '" + task.getTitle() + "'. <a href=\"http://localhost:8080/task_view/" + taskId + "\" style=\"text-decoration: underline; cursor: pointer; color: blue;\">click here to go to the task</a>",
                true,
                false
        );

        return new RedirectView("/users/profile_view/notifications/" + assignUser.getId());
    }

    @PostMapping(path = "/bid")
    public RedirectView makeOffer(@RequestParam("offer") float offerPrice, @RequestParam("currentTask") Long taskId, RedirectAttributes redirectAttributes){
        if (taskService.getTaskById(taskId).get().hasBiddingEnded()){
            redirectAttributes.addFlashAttribute("hasBiddingEndedBeforeBid", true);
            return new RedirectView("/task_view/" + taskId);
        }

        taskService.saveOffer(offerPrice, taskService.getTaskById(taskId).get());
        return new RedirectView("/task_view/" + taskId.toString());
    }

    @GetMapping(path = "/task_view/{taskId}/complete/{userId}")
    public RedirectView completeTask(@PathVariable("taskId") Long taskId, @PathVariable("userId") Long userId) {
        Optional<Task> taskOptional = taskService.getTaskById(taskId);
        Optional<User> userOptional = userService.getUserById(userId);
        if (!taskOptional.isPresent() || !userOptional.isPresent())
            return new RedirectView("/index");

        Task task = taskOptional.get();
        User user = userOptional.get();
        if (task.getAssignedDeveloper().getId().equals(user.getId())){
            task.setDeveloperHasCompleted(true);
            userService.saveNotification(
                    task.getCreator(),
                    "Task '" + task.getTitle() + "' has been COMPLETED.",
                    "The developer '" + user.getUsername() + "' has completed the task. Waiting for the creator of the task to approve it. <a href=\"http://localhost:8080/task_view/" + taskId + "\" style=\"text-decoration: underline; cursor: pointer; color: blue;\">click here to go to the task</a>",
                    true,
                    false
            );

            taskRepository.save(task);
            return new RedirectView("/task_view/" + taskId.toString());
        }

        // if (task.getCreator().getId().equals(user.getId()))
        task.setClientHasCompleted(true);
        userService.saveNotification(
                task.getAssignedDeveloper(),
                "Task '" + task.getTitle() + "' has been APPROVED.",
                "The creator '" + user.getUsername() + "' has approved the task. Now the task has been completed. <a href=\"http://localhost:8080/task_view/" + taskId + "\" style=\"text-decoration: underline; cursor: pointer; color: blue;\">click here to go to the task</a>",
                true,
                false
        );
        taskRepository.save(task);
        return new RedirectView("/users/profile_view/rate/" + task.getAssignedDeveloper().getId());
    }

    @GetMapping(path = "/delete_offers/{taskId}")
    public RedirectView makeOffer(@PathVariable("taskId") Long taskId, RedirectAttributes redirectAttributes) {
        if (taskService.getTaskById(taskId).get().hasBiddingEnded()){
            redirectAttributes.addFlashAttribute("hasBiddingEndedBeforeBid", true);
            return new RedirectView("/task_view/" + taskId);
        }

        taskService.deleteOffers(taskId);
        return new RedirectView("/task_view/" + taskId.toString());
    }

    @GetMapping(path = "/comment/{taskId}")
    public RedirectView comment(@PathVariable("taskId") Long taskId, @RequestParam("comment") String comment) {
        taskService.saveComment(comment, taskService.getTaskById(taskId).get());
        return new RedirectView("/task_view/" + taskId.toString());
    }

    @GetMapping(path = "/task_view/{taskId}/delete")
    public RedirectView deleteTask(@PathVariable("taskId") Long taskId, RedirectAttributes redirectAttributes) {
        taskService.deleteTask(taskId);
        redirectAttributes.addFlashAttribute("hasDeletedTask", true);
        return new RedirectView("/index");
    }

    private void putUsername(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("logedInUsername", authentication.getName());
            Optional<User> userOptional = userService.getUserByUsername(authentication.getName());
            if (userOptional.isPresent()){
                model.addAttribute("logedInUserID", userOptional.get().getId());
                List<Notification> notifications = userService.getNotificationsOfUser(userOptional.get().getId());
                model.addAttribute("notifications", notifications);
            }
        }
    }
}
