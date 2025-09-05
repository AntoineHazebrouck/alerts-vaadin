package antoine.alerts_vaadin.services.command;

import antoine.alerts_vaadin.entities.Alert;
import antoine.alerts_vaadin.repositories.AlertsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DeleteAlert implements Command<Alert, Void> {

    private final AlertsRepository alerts;

    @Override
    public Void execute(Alert alert) {
        alerts.delete(alert);
        return null;
    }
}
