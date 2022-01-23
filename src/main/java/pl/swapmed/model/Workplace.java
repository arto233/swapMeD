package pl.swapmed.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

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
public class Workplace {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotEmpty(message = "*Wprowadź miasto")
    String city;
    @NotEmpty(message = "*Wprowadź nazwę placówki")
    String name;
    @NotEmpty(message = "*Wprowadź adres")
    String address;
    @NotEmpty(message = "*Wprowadź nazwę oddziału")
    String division;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "workplace_user",
            joinColumns = @JoinColumn(name = "workplace_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    Set<User> users = new HashSet<>();
    @OneToMany(mappedBy = "workplace")
    Set<Schedule> schedules = new HashSet<>();

    public void addUser(User user) {
        this.users.add(user);
        user.getWorkplaces().add(this);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.getWorkplaces().remove(this);
    }

    /*public void addUser(User user) {
        if(users == null) {
            users = new HashSet<>();
        }
        users.add(user);
    }

     */


}
