package pl.swapmed.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import pl.swapmed.validator.Month;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotEmpty
    //@Month(message = "*miesiąc nie jest poprawny")
    String month;
    @NotEmpty
    String year;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "workplace_id", nullable = false)
    Workplace workplace;
    @OneToMany(mappedBy = "schedule")
    Set<Duty> duties = new HashSet<>();
    @ManyToMany
    @JoinTable(name="schedule_user", joinColumns = @JoinColumn(name = "schedule_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    Set<User> users = new HashSet<>();

    public void addUser(User user) {
        this.users.add(user);
        user.getSchedules().add(this);
    }

    public void removeUser (User user) {
        this.users.remove(user);
        user.getSchedules().remove(this);
    }
}
