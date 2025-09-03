package antoine.alerts_vaadin.services;

import antoine.alerts_vaadin.entities.Alert;
import java.util.concurrent.ScheduledFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AlertsScheduler {

    private final TaskScheduler scheduler;
    private final JavaMailSender emails;

    public ScheduledFuture<?> schedule(Alert alert) {
        var task = scheduler.schedule(
            () -> {
                log.info("Alert with id {} is being sent", alert.getId());

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
    }
}
