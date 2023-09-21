package com.omada5.ergasia_omadas_5.notification;

import com.omada5.ergasia_omadas_5.task.Task;
import com.omada5.ergasia_omadas_5.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User sender;
    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User recipient;
    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Task task;

    private String title;
    @Column(length = 2000)
    private String description;

    private boolean isDeletable;
    private boolean isHireRequest;

    public boolean getIsDeletable(){
        return isDeletable;
    }
    public void setIsDeletable(boolean isDeletable){
        this.isDeletable = isDeletable;
    }

    public boolean getIsHireRequest(){
        return isHireRequest;
    }
    public void setIsHireRequest(boolean isHireRequest){
        this.isHireRequest = isHireRequest;
    }
}
