package com.omada5.ergasia_omadas_5.task;

import com.omada5.ergasia_omadas_5.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @SequenceGenerator(
            name = "task_sequence",
            sequenceName = "task_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "task_sequence"
    )
    private Long id;
    private String title;
    private String description;
    private boolean isPublic;
    private boolean showPrice;
    @ManyToOne
    @JoinTable(
            name = "tasks_categories",
            joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    private Category category;
    @ManyToOne
    @JoinTable(
            name = "tasks_subcategories",
            joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "subcategory_id", referencedColumnName = "id")
    )
    private Subcategory subcategory;
    private boolean isPayingByTheHour;
    private float maxPrice;
    private float maxBiddingTime; // in minutes
    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "tasks_users",
            joinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private User creator;

    public Task() {
    }

    public Task(String title,
                String description,
                boolean isPublic,
                boolean showPrice,
                Category category,
                Subcategory subcategory,
                boolean isPayingByTheHour,
                float maxPrice,
                float maxBiddingTime,
                User creator) {
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
        this.showPrice = showPrice;
        this.category = category;
        this.subcategory = subcategory;
        this.isPayingByTheHour = isPayingByTheHour;
        this.maxPrice = maxPrice;
        this.maxBiddingTime = maxBiddingTime;
        this.creator = creator;
    }

    public Task(String title,
                String description,
                boolean isPublic,
                boolean showPrice,
                boolean isPayingByTheHour,
                float maxPrice,
                float maxBiddingTime,
                User creator) {
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
        this.showPrice = showPrice;
        this.isPayingByTheHour = isPayingByTheHour;
        this.maxPrice = maxPrice;
        this.maxBiddingTime = maxBiddingTime;
        this.creator = creator;
    }

    public Task(String title,
                String description,
                boolean isPublic,
                boolean showPrice,
                Category category,
                Subcategory subcategory,
                boolean isPayingByTheHour,
                float maxPrice,
                float maxBiddingTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
        this.showPrice = showPrice;
        this.category = category;
        this.subcategory = subcategory;
        this.isPayingByTheHour = isPayingByTheHour;
        this.maxPrice = maxPrice;
        this.maxBiddingTime = maxBiddingTime;
    }

    public Task(String title,
                String description,
                boolean isPublic,
                boolean showPrice,
                Category category,
                Subcategory subcategory,
                boolean isPayingByTheHour) {
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
        this.showPrice = showPrice;
        this.category = category;
        this.subcategory = subcategory;
        this.isPayingByTheHour = isPayingByTheHour;
    }

    public Task(String title,
                String description,
                boolean isPublic,
                boolean showPrice,
                boolean isPayingByTheHour,
                float maxPrice,
                float maxBiddingTime) {
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
        this.showPrice = showPrice;
        this.isPayingByTheHour = isPayingByTheHour;
        this.maxPrice = maxPrice;
        this.maxBiddingTime = maxBiddingTime;
    }

    public Task(String title,
                String description,
                boolean isPublic,
                boolean showPrice,
                boolean isPayingByTheHour) {
        this.title = title;
        this.description = description;
        this.isPublic = isPublic;
        this.showPrice = showPrice;
        this.isPayingByTheHour = isPayingByTheHour;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public boolean isShowPrice() {
        return showPrice;
    }

    public void setShowPrice(boolean showPrice) {
        this.showPrice = showPrice;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
    }

    public boolean isPayingByTheHour() {
        return isPayingByTheHour;
    }

    public void setPayingByTheHour(boolean payingByTheHour) {
        isPayingByTheHour = payingByTheHour;
    }

    public float getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(float maxPrice) {
        this.maxPrice = maxPrice;
    }

    public float getMaxBiddingTime() {
        return maxBiddingTime;
    }

    public void setMaxBiddingTime(float maxBiddingTime) {
        this.maxBiddingTime = maxBiddingTime;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User user) {
        this.creator = user;
    }
}
