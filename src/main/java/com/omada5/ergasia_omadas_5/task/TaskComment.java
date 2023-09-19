package com.omada5.ergasia_omadas_5.task;

import com.omada5.ergasia_omadas_5.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task_comments")
public class TaskComment {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User commenter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;

    @Column(length = 2000)
    private String comment;

    private LocalDateTime dateTime;

    public String getFormattedDateTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return dateTime.format(formatter);
    }

    public String getHowLongAgo(){
        int seconds = (int) Duration.between(this.dateTime, LocalDateTime.now()).getSeconds();
        if (seconds < 60)
            return seconds + " seconds ago";
        if (seconds / 60 < 60)
            return (seconds / 60) + " minutes ago";
        if (seconds / 3600 < 24)
            return (seconds / 3600) + " hours ago";

        int days = Period.between(LocalDate.from(this.dateTime), LocalDate.now()).getDays();
        if (days < 30)
            return days + " days ago";

        return Period.between(LocalDate.from(this.dateTime), LocalDate.now()).getMonths() + " months ago";
    }
}
