package pl.swapmed.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "duties")
public class Duty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    //@Column(unique = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime start;
    //@Column(unique = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    LocalDateTime end;
    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "schedule_id")
    Schedule schedule;
    @ManyToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    User user;
}
