package pl.swapmed.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Range;
import pl.swapmed.validator.Month;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Range(min = 1, max = 12)
    //@Month(message = "*miesiąc nie jest poprawny")
    Integer month;
    @Range(min = 2022,max = 2030)
    Integer year;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "workplace_id", nullable = false)
    Workplace workplace;
    @OneToMany(mappedBy = "schedule")
    Set<Duty> duties = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    User user;




}
