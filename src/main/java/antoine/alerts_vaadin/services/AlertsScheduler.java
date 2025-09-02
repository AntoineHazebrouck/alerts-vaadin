package antoine.alerts_vaadin.services;

import antoine.alerts_vaadin.repositories.AlertsRepository;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AlertsScheduler {

    private final AlertsRepository alerts;
    // private final TaskScheduler scheduler = new ThreadPoolTaskScheduler();
    private final TaskScheduler scheduler;
    private final JavaMailSender emails;

    @Value("${spring.mail.password}")
    String toto;

    private List<? extends ScheduledFuture<?>> tasks;

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedDelay = 30)
    public void refresh() {
        log.info("toto {}", toto);
        if (tasks != null) {
            log.info("Cancelling all previous scheduled alerts");
            tasks.forEach(task -> task.cancel(true));
        }

        tasks = alerts
            .findAll()
            .stream()
            .map(alert -> {
                var task = scheduler.schedule(
                    () -> {
                        var email = new SimpleMailMessage();
                        email.setFrom("antoine.haz@gmail.com");
                        email.setTo("antoine.haz@gmail.com");
                        email.setSubject(alert.getTitle());
                        email.setText(alert.getMessage());
                        emails.send(email);
                    },
                    new CronTrigger(alert.getCron())
                );
                log.info(
                    "Scheduling alert with id {} and cron {}",
                    alert.getId(),
                    alert.getCron()
                );
                return task;
            })
            .toList();
    }
}
