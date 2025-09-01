package antoine.alerts_vaadin.views.home.components;

import antoine.alerts_vaadin.entities.Alert;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import org.springframework.scheduling.support.CronExpression;

public class AlertBinder extends Binder<Alert> {

    public AlertBinder(TextField title, TextField message, TextField cron) {
        super(Alert.class);
        this.forField(title)
            .asRequired("Title is empty")
            .bind(Alert::getTitle, Alert::setTitle);
        this.forField(message)
            .asRequired("Message is empty")
            .bind(Alert::getMessage, Alert::setMessage);
        this.forField(cron)
            .asRequired((value, context) ->
                CronExpression.isValidExpression(value)
                    ? ValidationResult.ok()
                    : ValidationResult.error("Cron expression is not valid")
            )
            .bind(Alert::getCron, Alert::setCron);
    }

    public void handleInvalidForm() {
        this.validate();
        Notification.show(
            "The form is not filled properly",
            4000,
            Position.TOP_CENTER,
            true
        );
    }
}
