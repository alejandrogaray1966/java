# Trabajo Práctico Especial :JPA:
---

## Segunda entrega:

- Proyecto Java orientado a la persistencia de datos usando JPA y DAO pattern.
- Gestión de entidades (Estudiantes, Carreras, Inscripciones) conectadas a una base de datos MySQL.
- Lectura de datos desde archivos CSV.
- Generación de reportes y consultas avanzadas.
- Tabla de Contenidos

### Proyecto
- [Descripción](#descripcion)
- [Arquitectura](#arquitectura)
- [Instalación](#instalacion)
- [Uso](#uso)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Diagrama de Entidades](#der)


### Descripcion
- Descripción Esta segunda entrega amplía el sistema, incorporando la gestión de Estudiantes, Carreras e Inscripciones. Se utiliza JPA para la persistencia y se agregan funcionalidades para importar datos desde archivos CSV y generar reportes.

### Arquitectura
- Lenguaje: Java 22
- Persistencia: JPA con MySQL
- Gestión de dependencias: Maven
- Patrón usado: DAO
- Lectura de datos: CSV
- Requisitos Antes de instalar el proyecto, asegurate de tener:
- Java JDK 22+
- Apache Maven
- MySQL 8
- Docker (opcional, si querés levantar la DB en contenedor)

### Instalacion
- Clonar el repositorio: git clone https://github.com/...git cd ...
- Crear la base de datos (ejemplo): CREATE DATABASE integrador2;
- Configurar el archivo src/main/resources/META-INF/persistence.xml con tus credenciales de MySQL.

### Uso
- Ejecutar la aplicación desde la clase src/main/java/main/Main.java.
- Los archivos CSV deben estar en src/main/resources/csv/.
- Los reportes se generan en consola o en archivos según la configuración.

### Estructura del Proyecto
- src/ main/java/dto Clases DTO para reportes y transferencias 
- main/java/entities Entidades del sistema (Estudiante, Carrera, Inscripcion) 
- main/java/factory Factories para EntityManager 
- main/java/main Clase principal 
- main/java/repository Repositorios JPA 
- main/java/utils Utilidades para leer CSV 
- main/resources/csv Archivos CSV de datos 
- main/resources/META-INF Configuración de JPA

### DER
![DIAGRAMA ENTIEDAD RELACION](Integrador2/diagrama/der.png)

### DIAGRAMA DE OBJETOS
![DIAGRAMA DE CLASES](Integrador2/diagrama/diagrama-de-clases.png)

## Autores
| Nombre    | Apellido | DNI         | E-mail                        | Sede   |
|-----------|----------|-------------|-------------------------------|--------|
| Matías    | Bava     | 38.961.362  | matiasbavacc@gmail.com        | Tandil |
| Alejandro | Garay    | 18.038.228  | alejandrogaray1966@gmail.com  | Tandil |

