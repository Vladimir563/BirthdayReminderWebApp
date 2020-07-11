package itmo.vladimir.BirthdayReminderWebApp.entity;

import lombok.Data;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
@Data //автоматически проставляет get и set (lombok)
public class User
{
    @Id
    private String id;
    @Size(min = 5, message = "User name must be more 5 symbols!")
    @Column(unique = true, nullable = false)
    private String userName;
    private LocalDateTime lastVisit;
    private String role;

    @OneToMany(mappedBy = "user", //связь между классами в джава коде
            cascade = CascadeType.ALL, //каскадное удаление данных
            orphanRemoval = true) //если на уровне кода удалим именинника из списка, то этот он удалится и из таблички тоже
    private List<BirthdayBoy> birthdayBoys = new ArrayList<>();
}
