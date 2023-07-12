# ChallengeBackEndQE (Back de sitio Universitario) -  Spring Boot


## Descripción
 El Challenge se enfoca en el desarrollo del sistema BackEnd para un sitio universitario.
 
## Funcionalidades
El administrador tiene acceso a toda la información de la base de datos y puede realizar operaciones de creación, edición y eliminación de usuarios (profesores y alumnos), cursos y categorías.
El administrador puede buscar alumnos por su nombre o filtrarlos por una letra específica y el curso en el que están inscritos.
El administrador puede agregar y eliminar cursos que un profesor imparte, así como también modificar la información de los cursos y categorías.
El alumno puede modificar su información adicional, como darse de baja de los cursos en los que está inscrito e inscribirse en nuevos cursos.
 
 
## Areas Claves
Las siguientes areas claves fueron desarrolladas durante este proyecto:

### Autenticación y Autorización
Se implementó un sistema de autenticación y autorización básica basada en credenciales. Esto significa que los usuarios deben iniciar sesión con sus credenciales para acceder a las funcionalidades del sistema. Se utilizaron técnicas de encriptación para almacenar las contraseñas de forma segura. Además, se definieron roles de usuario (administrador, profesor, alumno) para limitar el acceso a diferentes partes del sistema.

### DTOs (Data Transfer Objects)
Se utilizaron DTOs para definir objetos de transferencia de datos. Estos objetos permiten transferir datos entre el BackEnd y el FrontEnd de manera eficiente y estructurada.

### Base de Datos MySQL
Se utilizó una base de datos MySQL para almacenar y gestionar los datos del sistema. Se configuró la conexión a la base de datos y se utilizaron anotaciones JPA para mapear las entidades de Java a tablas de la base de datos.

### CRUD (Create, Read, Update, Delete)
Se implementaron operaciones CRUD completas para las entidades principales del sistema, como usuarios (profesores y alumnos), cursos y categorías. Esto permite crear, leer, actualizar y eliminar registros en la base de datos de manera eficiente.

### MVC (Modelo-Vista-Controlador)
Se siguió el patrón de diseño Modelo-Vista-Controlador (MVC) para organizar y estructurar el código.

### Test Unitarios
Se realizaron pruebas unitarias para garantizar el correcto funcionamiento de los métodos individuales del código. Se probaron los métodos en los servicios que manejan los repositorios para asegurar que los datos se manejan adecuadamente.

## Tecnologías utilizadas

- Java: Lenguaje de programación principal utilizado en el desarrollo del BackEnd.
- Spring Boot: Framework de desarrollo de aplicaciones Java que simplifica la creación de aplicaciones basadas en Spring.
- MySQL: Sistema de gestión de bases de datos relacional utilizado para almacenar los datos del sistema.
- JPA (Java Persistence API): API de persistencia de Java utilizada para mapear objetos Java a entidades de base de datos.
- Thymeleaf: Motor de plantillas utilizado para generar vistas dinámicas en el FrontEnd.

## Configuración y Ejecución

Para configurar y ejecutar la aplicación:

1. Clona el repositorio en tu máquina local.
2. Configura la base de datos según la base de datos de tu elección.
3. Actualiza la configuración de la base de datos en el archivo de configuración de la aplicación (`application.properties` o `application.yml`).
4. Ejecuta la aplicación utilizando tu IDE preferido o mediante la línea de comandos.
5. Realiza las solicitudes HTTP correspondientes utilizando herramientas como Postman o cURL.

 

[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/matiasjb95/)

https://app.aluracursos.com/emprega-one/profile/matiasss95
