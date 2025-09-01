package antoine.alerts_vaadin.views.home;

import antoine.alerts_vaadin.repositories.AlertsRepository;
import antoine.alerts_vaadin.views.home.components.AlertForm;
import antoine.alerts_vaadin.views.home.components.AlertGrid;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("")
public class HomeView extends Composite<VerticalLayout> {

    AlertGrid grid;
    AlertForm form;

    public HomeView(AlertsRepository alerts) {
        this.grid = new AlertGrid(alerts);
        this.form = new AlertForm(newAlert -> {
            alerts.save(newAlert);
            grid.refreshItems();
        });
    }

    @Override
    protected VerticalLayout initContent() {
        var column = new VerticalLayout();

        column.add(new H1("Alerts"), form, grid);

        return column;
    }
}
