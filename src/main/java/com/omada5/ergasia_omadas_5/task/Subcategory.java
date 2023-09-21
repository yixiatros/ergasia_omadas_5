package com.omada5.ergasia_omadas_5.task;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "subcategories")
public class Subcategory {
    @Id
    @SequenceGenerator(
            name = "subcategory_sequence",
            sequenceName = "subcategory_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "subcategory_sequence"
    )
    private Long id;
    private String name;
    @ManyToOne
    @JoinTable(
            name = "categories_subcategories",
            joinColumns = @JoinColumn(name = "subcategory_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    public Subcategory() {
    }

    public Subcategory(Long id, String name, Category category) {
        this.id = id;
        this.name = name;
        this.category = category;
    }

    public Subcategory(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
