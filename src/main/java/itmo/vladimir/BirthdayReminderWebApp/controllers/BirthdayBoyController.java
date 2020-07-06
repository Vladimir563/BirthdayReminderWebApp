package itmo.vladimir.BirthdayReminderWebApp.controllers;

import itmo.vladimir.BirthdayReminderWebApp.entity.BirthdayBoy;
import itmo.vladimir.BirthdayReminderWebApp.entity.User;
import itmo.vladimir.BirthdayReminderWebApp.repository.BirthdayBoyRepository;
import itmo.vladimir.BirthdayReminderWebApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@SpringBootApplication
@RestController
@RequestMapping("/birthday_boy")
public class BirthdayBoyController
{
    private BirthdayBoyRepository birthdayBoyRepository;
    private UserRepository userRepository;

    @Autowired
    public void setBirthdayBoyRepository(BirthdayBoyRepository birthdayBoyRepository) {
        this.birthdayBoyRepository = birthdayBoyRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    //FIXME: нереализовано!!!
    @GetMapping(value = "/get_all_bb") //todo: получить список всех именинников пользователя
    public Iterable<BirthdayBoy> findAllBBbyUserName(@RequestParam String username)
    {
        Iterable<BirthdayBoy> birthdayBoys = birthdayBoyRepository.findAllBBbyUserName(username);
        for(BirthdayBoy birthdayBoy : birthdayBoys)
        {
            birthdayBoy.setUserId(userRepository.findUserByName(username).get().getId());
        }
        return birthdayBoys;
    }

    @GetMapping(value = "/find_by_surname_and_name")
    public Optional<BirthdayBoy> findBBbyNameAndSurname(@RequestParam String bbSurname, @RequestParam String bbName)
    {
        return birthdayBoyRepository.findBBbyNameAndSurname(bbName,bbSurname);
    }


    //TODO: получение обьекта из запроса пользователя
    @PostMapping(value = "/add") //todo: гарантировано будет POST запрос
    public @ResponseBody String addBirthdayBoy(@RequestBody BirthdayBoy birthdayBoy)
    {
        if(birthdayBoyRepository.findBBbyEmail(birthdayBoy.getEmail()).isPresent())
        {
            return "This email registered in database already. Please try with another (unregistered) email";
        }
        User user = userRepository.findById(birthdayBoy.getUserId()).get(); //получаю пользователя по id из запроса
        birthdayBoy.setUser(user); //устанавливаю данного пользователя для именинника
        birthdayBoy.setUsername(user.getUserName()); //устанавливаю имя пользователя для именинника
        user.getBirthdayBoys().add(birthdayBoy); //устанавливаю взаимную ссылку на именинника пользователю
        birthdayBoyRepository.save(birthdayBoy); //сохраняю именинника в БД
        return "200 OK";
    }
}
