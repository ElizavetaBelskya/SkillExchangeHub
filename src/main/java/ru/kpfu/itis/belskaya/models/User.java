package ru.kpfu.itis.belskaya.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
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
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "ie_recipient")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "phone", unique = true, nullable = true)
    private String phone;
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    private float rating;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToMany(cascade = { CascadeType.DETACH }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "recipient_skill",
            joinColumns = { @JoinColumn(name = "skill_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "recipient_id", referencedColumnName = "id") }
    )
    private List<Skill> skillList;

    @ManyToMany(mappedBy = "candidates")
    @JsonIgnoreProperties("candidates")
    private List<Order> orders;

    public void setAccount(Account account) {
        this.account = account;
    }


}
