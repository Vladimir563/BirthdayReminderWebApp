package itmo.vladimir.BirthdayReminderWebApp.service;

import itmo.vladimir.BirthdayReminderWebApp.components.CongratulationsThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


@EnableScheduling
@Service
public class CongratulationsService
{
    private TaskExecutor executor;
    private ApplicationContext context; //хранилище сохраненных ранее обьектов

    @Autowired
    public CongratulationsService(@Qualifier("executor") TaskExecutor executor, ApplicationContext context)
    {
        this.executor = executor;
        this.context = context;
    }


    @Scheduled(fixedRate = 180000)// метод должен вызываться по расписанию (в миллисекундах)
    public void start()
    {
        System.out.println("starting new congratulations thread...");
        //получаю компонент из контекста созданных компонентов
        CongratulationsThread congratulationsThread = context.getBean(CongratulationsThread.class);
        executor.execute(congratulationsThread); //передаем набор инструкций в пул потоков
    }
}
