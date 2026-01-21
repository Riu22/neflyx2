# 1. Usamos una imagen base con Java 21 que sea ligera (alpine)
FROM eclipse-temurin:21-jre-alpine

# 2. Creamos una carpeta de trabajo dentro del contenedor
WORKDIR /app

# 3. Copiamos el archivo JAR que ya tienes en 'target' al contenedor
# Lo renombramos a 'app.jar' para que sea más fácil de manejar
COPY target/neflyx2-0.0.1-SNAPSHOT.jar app.jar

# 4. Exponemos el puerto 8080 (el que usa Spring Boot por defecto)
EXPOSE 8080

# 5. Comando para ejecutar la aplicación al iniciar el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]