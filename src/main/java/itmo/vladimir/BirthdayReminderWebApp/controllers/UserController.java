
package itmo.vladimir.BirthdayReminderWebApp.controllers;

import itmo.vladimir.BirthdayReminderWebApp.entity.User;
import itmo.vladimir.BirthdayReminderWebApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.Collections;


@SpringBootApplication
@RestController
@RequestMapping("/user")
public class UserController
{

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }


    //FIXME: oauth2 авторизация
//    @GetMapping(value = "/")
//    public String user(@AuthenticationPrincipal OAuth2User principal)
//    {
//        //посмотреть все доступные атрибуты
//        //System.out.println(Collections.singletonList(principal.getAttributes()));
//
//        String userName = Collections.singletonList(principal.getAttribute("name")).toString();
//        String userNameParsed = userName.substring(1,userName.length()-1);
//
//        String userId = Collections.singletonList(principal.getAttribute("id")).toString();
//        String userIdParsed = userId.substring(1,userId.length()-1);
//
//        String userRole = Collections.singletonList(principal.getAuthorities()).toString();
//        String[] userRoleParsed = userRole.replaceAll("[^A-Za-zА-Яа-я0-9]", " ").trim().split(" ");
//
//        User authorizedUser = null;
//
//        if(!userRepository.findById(userIdParsed).isPresent())
//        {
//            authorizedUser = new User();
//            authorizedUser.setId(userIdParsed);
//            authorizedUser.setUserName(userNameParsed);
//            authorizedUser.setRole(userRoleParsed[1]);
//            authorizedUser.setLastVisit(LocalDateTime.now());
//            userRepository.save(authorizedUser);
//            return "new user " + userNameParsed + " added to database";
//        }
//
//        authorizedUser = userRepository.findById(userIdParsed).get();
//        authorizedUser.setLastVisit(LocalDateTime.now());
//        userRepository.save(authorizedUser);
//
//        return "authorized";
//    }

    @PostMapping(value = "/reg") //регистрация пользователя (временно)
    public @ResponseBody String registration(@RequestBody User user)
    {
        System.out.println(user.toString());
        user.setLastVisit(LocalDateTime.now());
        userRepository.save(user);
        return "200 OK";
    }


    @GetMapping(value = "/ok")
    public @ResponseBody String okmethod(@RequestParam String first, @RequestParam String second)
    {
        System.out.println(first + " : " + second);
        return "ok!!!!!!!!!!!!!!!!!!!!!";
    }

}

