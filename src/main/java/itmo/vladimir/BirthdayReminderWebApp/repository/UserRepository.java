package itmo.vladimir.BirthdayReminderWebApp.repository;

import itmo.vladimir.BirthdayReminderWebApp.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User, String>
{
    @Query("SELECT u FROM User u WHERE u.userName = ?1")
    Optional<User> findUserByName(String userName);
}
