package antoine.alerts_vaadin.views.home;

import antoine.alerts_vaadin.entities.Alert;
import antoine.alerts_vaadin.services.FindAllAlerts;
import antoine.alerts_vaadin.services.SaveAlert;
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

    public HomeView(SaveAlert saveAlert, FindAllAlerts findAllAlerts) {
        Consumer<Alert> saveAndRefreshGrid = newAlert -> {
            saveAlert.apply(newAlert);
            grid.refreshItems(findAllAlerts);
        };
        this.grid = new AlertGrid(saveAndRefreshGrid);
        this.form = new AlertForm(saveAndRefreshGrid);
    }

    @Override
    protected VerticalLayout initContent() {
        var column = new VerticalLayout();

        column.add(new H1("Alerts"), form, grid);

        return column;
    }
}
