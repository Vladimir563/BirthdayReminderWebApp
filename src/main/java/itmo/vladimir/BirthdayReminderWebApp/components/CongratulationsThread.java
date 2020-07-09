package itmo.vladimir.BirthdayReminderWebApp.components;

import itmo.vladimir.BirthdayReminderWebApp.entity.BirthdayBoy;
import itmo.vladimir.BirthdayReminderWebApp.entity.User;
import itmo.vladimir.BirthdayReminderWebApp.repository.BirthdayBoyRepository;
import itmo.vladimir.BirthdayReminderWebApp.repository.UserRepository;
import itmo.vladimir.BirthdayReminderWebApp.service.GMailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;


@Component
@Scope("prototype") // позволит создавать несколько объектов данного
// класса (по каждому запросу - создание объекта)
public class CongratulationsThread implements Runnable
{
    private BirthdayBoyRepository birthdayBoyRepository;
    private UserRepository userRepository;
    private GMailSender gMailSender;

    @Autowired
    public void setgMailSender(GMailSender gMailSender)
    {
        this.gMailSender = gMailSender;
    }
    @Autowired
    public void setBirthdayBoyRepository(BirthdayBoyRepository birthdayBoyRepository) {
        this.birthdayBoyRepository = birthdayBoyRepository;
    }
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run()
    {
        Iterable<BirthdayBoy> birthdayBoys = birthdayBoyRepository.findAll();
        String userId = "";//получаем id пользователя после авторизации
        User user = userRepository.findById("329432").get(); //находим в БД пользователя по id (взял мой для теста)
        for(BirthdayBoy birthdayBoy : birthdayBoys)
        {
            if(birthdayBoy.getBirthday().getMonth() == LocalDateTime.now().getMonth() && birthdayBoy.getBirthday().getDayOfMonth() == LocalDateTime.now().getDayOfMonth())
            {
                //todo: формируем и отправляем поздравление
                String congratulationText = String.format("С днем рождения, %s %s!\nСчастья, здоровья!",birthdayBoy.getBbName(),birthdayBoy.getBbPatronymic());
                String congratulationMessage = String.format("%s\n(%s, с наилучшими пожеланиями)",congratulationText,user.getUserName());
                try
                {
                    gMailSender.sendMessage(user.getUserName(),birthdayBoy.getEmail(),"С днем рождения!",congratulationMessage);
                }
                catch (IOException | MessagingException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
