package itmo.vladimir.BirthdayReminderWebApp.repository;

import itmo.vladimir.BirthdayReminderWebApp.entity.BirthdayBoy;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface BirthdayBoyRepository extends CrudRepository<BirthdayBoy, Integer>
{
    @Query("SELECT bb FROM BirthdayBoy bb WHERE bb.username = ?1")
    List<BirthdayBoy> allBBbyUserName(String username);
}
