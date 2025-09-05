package antoine.alerts_vaadin.views.home;

import antoine.alerts_vaadin.entities.Alert;
import antoine.alerts_vaadin.services.command.DeleteAlert;
import antoine.alerts_vaadin.services.command.SaveAlert;
import antoine.alerts_vaadin.services.queries.FindAllAlerts;
import antoine.alerts_vaadin.views.home.components.AlertForm;
import antoine.alerts_vaadin.views.home.components.AlertGrid;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import java.util.function.Consumer;

@Route("")
public class HomeView extends Composite<VerticalLayout> {

    AlertGrid grid;
    AlertForm form;

    public HomeView(
        SaveAlert saveAlert,
        FindAllAlerts findAllAlerts,
        DeleteAlert deleteAlert
    ) {
        Consumer<Alert> saveAndRefreshGrid = newAlert -> {
            saveAlert.execute(newAlert);
            grid.refreshItems();
        };
        this.grid = new AlertGrid(
            saveAndRefreshGrid,
            findAllAlerts,
            deleteAlert
        );
        this.form = new AlertForm(saveAndRefreshGrid);
    }

    @Override
    protected VerticalLayout initContent() {
        var column = new VerticalLayout();

        column.add(new H1("Alerts"), form, grid);

        return column;
    }
}
