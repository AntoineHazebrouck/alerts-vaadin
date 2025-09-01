package antoine.alerts_vaadin.repositories;

import antoine.alerts_vaadin.entities.Alert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertsRepository extends JpaRepository<Alert, Integer> {}
