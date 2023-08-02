# game-are-new

# Configurando JARs que não têm módulo no Java 9+

- Exemplo: https://www.youtube.com/watch?v=bO6f3U4i0A0 (PostgreSQL JDBC Driver)

1. jdeps --ignore-missing-deps --generate-module-info jars jars/postgresql-42.2.18.jar
2. javac --patch-module org.postgresql.jdbc=jars/postgresql-42.2.18.jar jars/module-info.java
3. jar uf jars/postgresql-42.2.18.jar -C jars module-info.class 

