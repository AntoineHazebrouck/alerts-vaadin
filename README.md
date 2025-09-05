# Alerts Vaadin

## Watch logs

```shell
heroku logs --tail --app alerts-vaadin
```

## All sql updates history

```shell
heroku pg:info postgresql-rigid-24409 --app alerts-vaadin
heroku pg:psql postgresql-rigid-24409 --app alerts-vaadin
```

```sql
CREATE TABLE alert (
	id serial primary key,
    cron text,
    title text,
    message text
);
```