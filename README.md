# Alerts Vaadin

## Watch logs

```shell
heroku logs --tail --app alerts-vaadin
```

## All sql updates history

```shell
heroku pg:psql postgresql-lively-33193 --app alerts-vaadin
```

```sql
CREATE TABLE alert (
	id serial primary key,
    cron text,
    title text,
    message text
);
```