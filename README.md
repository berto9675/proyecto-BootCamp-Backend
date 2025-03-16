# CodeCampDev: Plataforma de Recursos para Bootcamp 

## Propiedad de: CodeCampDev

## Descripción del Proyecto

La Plataforma de Recursos para Bootcamp es una solución web centralizada diseñada para apoyar a estudiantes, programadores junior e instructores en su aprendizaje y colaboración. Ofrece recursos educativos confiables y organizados, estructurados en una interfaz moderna y amigable.

### Objetivo principal:

Mejorar la experiencia de aprendizaje, reducir la curva de aprendizaje y crear una comunidad activa donde el conocimiento fluya de manera estructurada y efectiva.

## Equipo de Desarrollo





## Funcionalidades Principales

### Frontend

* ✔️ Página principal con información sobre el bootcamp
* ✔️ Registro e inicio de sesión de usuarios
* ✔️ Panel de usuario con acceso a:
    * Documentación con ejemplos de código
    * Ejercicios prácticos organizados por temas
    * Banco de videos educativos
    * Sistema de subida de recursos (archivos o enlaces)
    * Diseño responsive para adaptarse a cualquier dispositivo
    * Sistema de comentarios y valoraciones

### Backend

* API REST para gestionar usuarios y recursos
* Base de datos H2 para almacenar información de usuarios y recursos
* Gestión de comentarios y valoraciones de recursos
* Servicios para subida de archivos y enlaces externos

## Testing

* ️ Pruebas unitarias con JUnit para validar funcionalidades clave
* ️ Pruebas de integración con Hamcrest para garantizar el correcto funcionamiento de la API REST
* Cobertura unitaria actual: 46%, en expansión constante

## MVP (Producto Mínimo Viable)

La primera versión de la plataforma incluye:

* Acceso a recursos organizados por categorías para usuarios registrados
* Subida y visualización de archivos y enlaces
* Sistema de comentarios y valoraciones
* Página principal con información básica para usuarios no registrados
* Acceso exclusivamente online, sin posibilidad de descarga

## Criterios de Éxito

* ✔️ Plataforma completamente funcional con gestión de usuarios y recursos
* ✔️ Interfaz moderna, intuitiva y accesible
* ✔️ Backend escalable, seguro y eficiente
* ✔️ Validación de calidad mediante pruebas unitarias e integración
* ✔️ Feedback constante para mejoras continuas

## Tecnologías Usadas

### Frontend

* React, HTML, CSS, JavaScript

### Backend

* Java con Spring Boot

### Base de Datos

* H2 (base de datos en memoria)

### Testing

* JUnit, Hamcrest

## Estructura del Proyecto

/src
├── main
│   ├── java/com/p6r # Código fuente del backend
│   ├── resources # Configuración y archivos estáticos
└── test # Tests unitarios y de integración
/frontend # Código fuente del frontend


## ⚙️ Instalación y Configuración

### Requisitos Previos

* JDK 17+
* Maven 3.6+
* Node.js y npm

### Pasos de Instalación

1.  `git clone <URL-DEL-REPOSITORIO>`
2.  `cd <NOMBRE-DEL-REPOSITORIO>`
3.  Configura los archivos `application.properties` y `.env`.
4.  `mvn clean install && mvn spring-boot:run`
5.  `cd frontend && npm install && npm start`
6.  Accede a `http://localhost:3000`.

### Cómo Contribuir

1.  Haz un fork.
2.  Crea una rama.
3.  Realiza cambios.
4.  Sube cambios.
5.  Abre un pull request.

### Licencia

Este proyecto es propiedad del equipo ACE-Code-Backend. Todos los derechos reservados. No está permitida la distribución sin previa autorización.
