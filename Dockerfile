# Construcción (JDK)
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar pom.xml
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar código y compilar
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime (JRE)
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copiamos solo el artefacto generado en la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto (mismo que application.yml)
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]