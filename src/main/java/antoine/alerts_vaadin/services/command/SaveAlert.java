package antoine.alerts_vaadin.services.command;

import antoine.alerts_vaadin.entities.Alert;
import antoine.alerts_vaadin.repositories.AlertsRepository;
import antoine.alerts_vaadin.services.AlertsScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SaveAlert implements Command<Alert, Alert> {

    private final AlertsRepository alerts;
    private final AlertsScheduler scheduler;

    @Override
    public Alert execute(Alert alert) {
        var saved = alerts.save(alert);
        scheduler.schedule(saved);
        return saved;
    }
}
