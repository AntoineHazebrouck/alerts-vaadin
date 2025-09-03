package antoine.alerts_vaadin.services;

import antoine.alerts_vaadin.entities.Alert;
import antoine.alerts_vaadin.repositories.AlertsRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindAllAlerts implements Supplier<List<Alert>> {

    private final AlertsRepository alerts;

    @Override
    public List<Alert> get() {
        return alerts.findAll();
    }
}
