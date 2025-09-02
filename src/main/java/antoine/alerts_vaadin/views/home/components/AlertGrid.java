package antoine.alerts_vaadin.views.home.components;

import antoine.alerts_vaadin.entities.Alert;
import antoine.alerts_vaadin.repositories.AlertsRepository;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlertGrid extends Composite<Grid<Alert>> {

    private final AlertsRepository alerts;

    Grid<Alert> grid = new Grid<>(Alert.class, false);

    TextField title = new TextField();
    TextField message = new TextField();
    TextField cron = new TextField();

    @Override
    protected Grid<Alert> initContent() {
        Editor<Alert> editor = grid.getEditor();
        AlertBinder binder = new AlertBinder(title, message, cron);

        editor.setBuffered(true);
        editor.setBinder(binder);
        editor.addSaveListener(event -> {
            alerts.save(event.getItem());
            refreshItems();
        });

        grid
            .addColumn(Alert::getTitle)
            .setHeader("title")
            .setEditorComponent(title);
        grid
            .addColumn(Alert::getMessage)
            .setHeader("message")
            .setEditorComponent(message);
        grid
            .addColumn(Alert::getCron)
            .setHeader("cron")
            .setEditorComponent(cron);

        grid
            .addComponentColumn(alert -> {
                Button editButton = new Button("Edit");
                editButton.addClickListener(e -> {
                    if (editor.isOpen()) editor.cancel();
                    editor.editItem(alert);
                });
                return editButton;
            })
            .setEditorComponent(
                new HorizontalLayout(
                    new Button("Save", e -> {
                        editor.save();
                    }),
                    new Button(VaadinIcon.CLOSE.create(), e -> editor.cancel())
                )
            );

        refreshItems();
        return grid;
    }

    public void refreshItems() {
        grid.setItems(alerts.findAll()).setIdentifierProvider(Alert::getId);
    }
}
