package itmo.vladimir.BirthdayReminderWebApp.components;

import itmo.vladimir.BirthdayReminderWebApp.entity.BirthdayBoy;
import itmo.vladimir.BirthdayReminderWebApp.entity.User;
import itmo.vladimir.BirthdayReminderWebApp.repository.BirthdayBoyRepository;
import itmo.vladimir.BirthdayReminderWebApp.repository.UserRepository;
import itmo.vladimir.BirthdayReminderWebApp.service.GMailSender;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import javax.mail.MessagingException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;


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
        User user = null;
        try
        {
            user = userRepository.findById("329432").get();//находим в БД пользователя по id (взял мой для теста)
        }
        catch (NoSuchElementException e)
        {
            System.out.println(TextColours.ANSI_RED.getCode() + e.getMessage()+"[Нет зарегистрированных пользователей]" + TextColours.ANSI_RESET.getCode());
        }

        for(BirthdayBoy birthdayBoy : birthdayBoys)
        {
            if(birthdayBoy.getBirthday().getMonth() == LocalDateTime.now().getMonth() && birthdayBoy.getBirthday().getDayOfMonth() == LocalDateTime.now().getDayOfMonth())
            {
                //todo: формируем и отправляем поздравление
                String congratulationText = String.format("С днем рождения уважаем%s, %s %s!",birthdayBoy.getSex().equals("жен")?"ая":"ый",birthdayBoy.getBbName(),birthdayBoy.getBbPatronymic());
                String congratulationMessage = String.format("%s\n(%s, с наилучшими пожеланиями)",congratulationText,user.getUserName());
                try
                {
                    System.out.println(TextColours.ANSI_PURPLE.getCode() + "[попытка отправки сообщения...]" + TextColours.ANSI_RESET.getCode());
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

