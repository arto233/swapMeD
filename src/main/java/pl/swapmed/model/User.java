package pl.swapmed.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false, unique = true, length = 60)
    @Length(min = 5, message = "*Nazwa użytkownika musi mieć co najmniej 5 znaków")
    @NotEmpty(message = "*Wprowadź nazwę użytkownika")
    String username;
    @Column(nullable = false, unique = true)
    @Email(message = "*Wprowadź poprawny email")
    @NotEmpty(message = "*Wprowadź adres email")
    String email;
    @Column(nullable = false)
    @Length(min=4, message = "*Hasło musi składać się z przynajmniej 4 znaków")
    @NotEmpty(message = "*Wprowadź hasło")
    String password;
    @NotEmpty(message = "*Wprowadź swoje imię")
    String name;
    @NotEmpty(message = "*Wprowadź swoje nazwisko")
    String lastName;
    Boolean active;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles;
    @ManyToMany(mappedBy = "users")
    Set<Workplace> workplaces = new HashSet<>();

    @ManyToMany(mappedBy = "users")//(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    Set<Schedule> schedules;


}
