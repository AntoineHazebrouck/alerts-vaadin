package antoine.alerts_vaadin.views.home.components;

import antoine.alerts_vaadin.entities.Alert;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlertForm extends Composite<HorizontalLayout> {

    private final Consumer<Alert> callback;

    TextField title = new TextField("Title");
    TextField message = new TextField("Message");
    TextField cron = new TextField("Cron");

    @Override
    protected HorizontalLayout initContent() {
        AlertBinder binder = new AlertBinder(title, message, cron);

        var row = new HorizontalLayout();
        row.add(
            title,
            message,
            cron,
            new Button("Add", event -> {
                if (!binder.isValid()) {
                    binder.handleInvalidForm();
                } else {
                    var newAlert = new Alert();
                    newAlert.setTitle(title.getValue());
                    newAlert.setMessage(message.getValue());
                    newAlert.setCron(cron.getValue());

                    callback.accept(newAlert);

                    binder.refreshFields();
                }
            })
        );
        row.setAlignItems(Alignment.END);
        return row;
    }
}
