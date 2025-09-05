package antoine.alerts_vaadin.services.queries;

public interface Query<I, O> {
    O get(I input);
}
