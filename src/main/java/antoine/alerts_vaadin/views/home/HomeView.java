package antoine.alerts_vaadin.views.home;

import antoine.alerts_vaadin.entities.Alert;
import antoine.alerts_vaadin.repositories.AlertsRepository;
import antoine.alerts_vaadin.views.home.components.AlertBinder;
import antoine.alerts_vaadin.views.home.components.AlertForm;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Route("")
public class HomeView extends Composite<VerticalLayout> {

    private final AlertsRepository alerts;

    Grid<Alert> grid = new Grid<>(Alert.class, false);

    @Override
    protected VerticalLayout initContent() {
        refreshGrid();

        TextField title = new TextField();
        TextField message = new TextField();
        TextField cron = new TextField();

        Editor<Alert> editor = grid.getEditor();
        AlertBinder binder = new AlertBinder(title, message, cron);

        editor.setBuffered(true);
        editor.setBinder(binder);
        editor.addSaveListener(event -> {
            alerts.save(event.getItem());
            refreshGrid();
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

        var column = new VerticalLayout();

        column.add(new H1("Alerts"));

        column.add(
            new AlertForm(newAlert -> {
                alerts.save(newAlert);
                refreshGrid();
            }),
            grid
        );
        return column;
    }

    private void refreshGrid() {
        grid.setItems(alerts.findAll()).setIdentifierProvider(Alert::getId);
    }
}
