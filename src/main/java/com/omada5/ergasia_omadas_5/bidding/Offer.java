package com.omada5.ergasia_omadas_5.bidding;


import com.omada5.ergasia_omadas_5.task.Task;
import com.omada5.ergasia_omadas_5.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User bidder;

    private float price;

    private LocalDateTime offerDateTime;

    private boolean isActive;

    public Offer(User bidder, Task task, float price) {
        this.bidder = bidder;
        this.task = task;
        this.price = price;
    }

    public String getFormattedOfferDateTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return offerDateTime.format(formatter);
    }
}
