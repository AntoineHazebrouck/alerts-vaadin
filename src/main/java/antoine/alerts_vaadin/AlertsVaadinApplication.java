package antoine.alerts_vaadin;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Theme("my-theme")
public class AlertsVaadinApplication implements AppShellConfigurator {

    public static void main(String[] args) {
        var context = SpringApplication.run(
            AlertsVaadinApplication.class,
            args
        );
        context.getBean(OnStartup.class).run();
    }
}
