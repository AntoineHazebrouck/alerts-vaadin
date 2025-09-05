package antoine.alerts_vaadin.services.command;

import antoine.alerts_vaadin.entities.Alert;
import antoine.alerts_vaadin.repositories.AlertsRepository;
import antoine.alerts_vaadin.services.AlertsScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteAlert implements Command<Alert, Void> {

    private final AlertsRepository alerts;
    private final AlertsScheduler scheduler;

    @Override
    public Void execute(Alert alert) {
        alerts.delete(alert);
        scheduler.cancel(alert);
        return null;
    }
}
