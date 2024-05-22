package ru.kpfu.itis.belskaya.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Elizaveta Belskaya
 */
@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ie_order")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private Long id;

    @Column(nullable = false)
    private String skill;

    @ManyToOne(cascade = { CascadeType.DETACH }, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id",  referencedColumnName = "id")
    private User author;

    private String description;

    @Column(name = "recipient_gender", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Gender recipientGender;

    @Column(name = "min_rating")
    private float minRating = 0;

    @Column(name = "created_at")
    private LocalDateTime creationDate;

    @ManyToOne(cascade = { CascadeType.DETACH }, fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    @JsonIgnoreProperties("orders")
    private User recipient;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_recipient",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "recipient_id", referencedColumnName = "id")
    )
    @JsonIgnoreProperties("orders")
    private List<User> candidates;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private State state;

    public enum State {
        ACTUAL,
        DELETED,
        BANNED
    }

    public enum Format {
        ONLINE,
        OFFLINE,
        BOTH
    }

    @PrePersist
    private void updateColumns() {
        creationDate = LocalDateTime.now();
        state = State.ACTUAL;
    }

}
