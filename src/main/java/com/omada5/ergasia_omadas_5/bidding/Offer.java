package com.omada5.ergasia_omadas_5.bidding;


import com.omada5.ergasia_omadas_5.task.Task;
import com.omada5.ergasia_omadas_5.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User bidder;

    private float price;

    private LocalDateTime offerDateTime;

    private boolean isActive;

    public Offer(User bidder, Task task, float price) {
        this.bidder = bidder;
        this.task = task;
        this.price = price;
    }
}
