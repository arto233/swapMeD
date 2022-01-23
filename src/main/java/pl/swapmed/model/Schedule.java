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
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotEmpty
    String month;
    @ManyToOne
    @JoinColumn(name = "workplace_id", nullable = false)
    Workplace workplace;
    @OneToMany(mappedBy = "schedule")
    Set<Duty> duties = new HashSet<>();
}
