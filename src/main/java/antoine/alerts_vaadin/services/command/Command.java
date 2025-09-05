package antoine.alerts_vaadin.services.command;

public interface Command<I, O> {
    O execute(I input);
}
