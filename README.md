# 🎬 NextWatch

A Java-based platform for tracking movies and TV shows - think Letterboxd, but built from scratch.
Rate titles, write reviews, manage your watchlist and discover what's worth watching next.

Etapa II adauga persistenta PostgreSQL prin JDBC si audit CSV.

## PostgreSQL

1. Creeaza baza de date:

```sql
CREATE DATABASE nextwatch;
```

2. Ruleaza schema:

```bash
psql -d nextwatch -f database/schema.sql
```

3. Configureaza conexiunea prin variabile de mediu sau proprietati Java:

```bash
export NEXTWATCH_DB_URL=jdbc:postgresql://localhost:5432/nextwatch
export NEXTWATCH_DB_USER=robertobaciu
```

Pentru rulare este necesar driverul PostgreSQL JDBC in classpath.

## Audit

`AuditService` scrie automat in `audit.csv`, cu formatul:

```csv
nume_actiune,timestamp
```

Fisierul poate fi schimbat prin `NEXTWATCH_AUDIT_FILE` sau proprietatea Java `nextwatch.audit.file`.
