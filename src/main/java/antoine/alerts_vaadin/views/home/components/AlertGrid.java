package antoine.alerts_vaadin.views.home.components;

import antoine.alerts_vaadin.entities.Alert;
import antoine.alerts_vaadin.services.command.DeleteAlert;
import antoine.alerts_vaadin.services.queries.FindAllAlerts;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AlertGrid extends Composite<Grid<Alert>> {

    private final Consumer<Alert> saveAndRefreshGrid;
    private final FindAllAlerts findAllAlerts;
    private final DeleteAlert deleteAlert;

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
            saveAndRefreshGrid.accept(event.getItem());
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
            .addComponentColumn(alert ->
                new Button("Edit", e -> {
                    if (editor.isOpen()) editor.cancel();
                    editor.editItem(alert);
                })
            )
            .setEditorComponent(
                new HorizontalLayout(
                    new Button("Save", e -> {
                        editor.save();
                    }),
                    new Button(VaadinIcon.CLOSE.create(), e -> editor.cancel())
                )
            );

        grid
            .addComponentColumn(alert -> {
                var delete = new Button("Delete", e -> {
                    deleteAlert.execute(alert);
                    refreshItems();
                });
                delete.addThemeVariants(ButtonVariant.LUMO_WARNING);
                return delete;
            })
            .setEditorComponent(new Button("Delete"));

        grid.setItems(findAllAlerts.get()).setIdentifierProvider(Alert::getId);
        return grid;
    }

    public void refreshItems() {
        grid.setItems(findAllAlerts.get()).setIdentifierProvider(Alert::getId);
    }
}
