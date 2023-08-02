# game-are-new

## Configurando o projeto

1. Instale o Java 11 (https://adoptopenjdk.net/)
2. Instale o Maven (https://maven.apache.org/download.cgi)
3. Abre o projeto em alguma IDE (Eclipse, IntelliJ (Preferência), NetBeans, etc)
4. Configure o projeto para usar o Java 11
5. Configure o projeto para usar o Maven e baixe as dependências
6. Configure o POM.xml para usar o caminho do JLINK nas propriedades (`<jlinkExecutable>path-jdk-11\bin\jlink</jlinkExecutable>`)
7. Configure o application.yaml (Informações do banco de dados (SQLITE), caminho das Sprites, etc)
8. Execute o comando `mvn clean compile javafx:run` para executar o projeto
8. Execute o comando `mvn clean compile javafx:jlink` para gerar o ZIP
9. Extraia o ZIP e execute o arquivo `app.exe` (Windows) ou `app` (Linux) dentro da pasta `bin`

## Configurando JARs que não têm módulo no Java 9+ (Apenas para desenvolvimento)

- Exemplo: https://www.youtube.com/watch?v=bO6f3U4i0A0 (PostgreSQL JDBC Driver)

1. jdeps --ignore-missing-deps --generate-module-info jars jars/postgresql-42.2.18.jar
2. javac --patch-module org.postgresql.jdbc=jars/postgresql-42.2.18.jar jars/module-info.java
3. jar uf jars/postgresql-42.2.18.jar -C jars module-info.class 

