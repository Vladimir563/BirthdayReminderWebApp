package itmo.vladimir.BirthdayReminderWebApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "birthday_boys")
@Data
public class BirthdayBoy
{
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int id; //todo: генерация id будет осуществляться инкрементально

    private String bbName;
    private String bbSurname;
    /** this field contains patronymic of birthday boy */
    private String bbPatronymic; //todo: отчество
    /**this field contains relation degree information of birthday boy*/
    private String relationDegree; //todo: степень родства
    private LocalDateTime birthday;
    @Column(unique = true, nullable = false)
    private String email;
    private String username;
    private String sex;

    @Transient //не войдет в сериализацию и не будет столбцом БД, нужно чтобы получить id пользователя из JSON
    // и по данному id из базы присвоить пользователя имениннику
    private String userId;

    @ManyToOne
    @JoinColumn
    @JsonIgnore //todo: чтобы избежать рекурсии при возврате JSON
    private User user;
}
