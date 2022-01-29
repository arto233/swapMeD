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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime start;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime end;
    @ManyToOne
    @JoinColumn(name = "schedule_id")
    Schedule schedule;
}
