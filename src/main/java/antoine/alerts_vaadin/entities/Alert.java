package antoine.alerts_vaadin.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Alert {

    @Id
    @GeneratedValue
    private Integer id;

    private String cron;
    private String title;
    private String message;
}
