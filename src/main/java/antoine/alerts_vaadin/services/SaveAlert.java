package antoine.alerts_vaadin.services;

import antoine.alerts_vaadin.entities.Alert;
import antoine.alerts_vaadin.repositories.AlertsRepository;
import java.util.function.UnaryOperator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SaveAlert implements UnaryOperator<Alert> {

    private final AlertsRepository alerts;
    private final AlertsScheduler scheduler;

    @Override
    public Alert apply(Alert alert) {
        var saved = alerts.save(alert);
        scheduler.schedule(saved);
        return saved;
    }
}
