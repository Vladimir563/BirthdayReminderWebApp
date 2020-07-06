package itmo.vladimir.BirthdayReminderWebApp.repository;

import itmo.vladimir.BirthdayReminderWebApp.entity.BirthdayBoy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;


public interface BirthdayBoyRepository extends CrudRepository<BirthdayBoy, Integer>
{
    @Query("SELECT bb FROM BirthdayBoy bb WHERE bb.username = ?1")
    Iterable<BirthdayBoy> findAllBBbyUserName(String username);

    @Query("SELECT bb FROM BirthdayBoy bb WHERE bb.bbName = ?1 and bb.bbSurname = ?2")
    Optional<BirthdayBoy> findBBbyNameAndSurname(String bbName, String bbSurname);

    @Query("SELECT bb FROM BirthdayBoy bb WHERE bb.email = ?1")
    Optional<BirthdayBoy> findBBbyEmail(String email);
}
