Question 3 answer
=================

I used an Ansible playbook to SSH into the CHOICE_A web server, verify the project had no local changes, pull the latest code, build the Spring Boot project with Maven, run the tests with SQLite as the test database, and export a MySQL backup to `backup.sql`.

Command used to run the playbook:

```bash
ansible-playbook -i ansible/inventory.ini ansible/deploy.yml
```

Command used by the playbook to test with SQLite:

```bash
mvn test
```

The SQLite test database is configured in:

```text
src/test/resources/application.properties
```

The production MySQL database is configured in:

```text
src/main/resources/application.properties
```

The MySQL backup file created by Ansible is:

```text
/app/backup.sql
```
