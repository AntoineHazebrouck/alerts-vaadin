package antoine.alerts_vaadin;

import antoine.alerts_vaadin.repositories.AlertsRepository;
import antoine.alerts_vaadin.services.AlertsScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class OnStartup {

    private final AlertsRepository alerts;
    private final AlertsScheduler scheduler;

    public void run() {
        alerts.findAll().stream().forEach(scheduler::schedule);
    }
}
