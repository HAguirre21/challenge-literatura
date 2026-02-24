# 📚 LiterAlura - Catálogo de Libros

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-green?style=for-the-badge&logo=springboot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=for-the-badge&logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-4.0-red?style=for-the-badge&logo=apachemaven)

## 📖 Descripción

**LiterAlura** es un catálogo de libros interactivo desarrollado en Java con Spring Boot. Permite buscar libros a través de la API de [Gutendex](https://gutendex.com/), guardarlos en una base de datos PostgreSQL y consultar información sobre libros y autores desde la consola.

Este proyecto fue desarrollado como parte del **Challenge Literalura** de Alura Latam.

---

## ✨ Funcionalidades

El sistema ofrece las siguientes opciones de interacción via consola:

| # | Opción | Descripción |
|---|--------|-------------|
| 1 | Buscar libro por título | Busca en la API de Gutendex y guarda en la base de datos |
| 2 | Listar libros registrados | Muestra todos los libros guardados |
| 3 | Listar autores registrados | Muestra todos los autores guardados |
| 4 | Autores vivos en cierto año | Filtra autores que vivían en un año específico |
| 5 | Libros por idioma | Filtra libros según su idioma |
| 6 | Top 10 libros más descargados | Muestra el ranking de libros más populares |

---

## 🛠️ Tecnologías utilizadas

- **Java 17**
- **Spring Boot 3.2.5**
- **Spring Data JPA**
- **PostgreSQL 16**
- **Jackson Databind**
- **API Gutendex** (https://gutendex.com/)
- **Maven**

---

## 📁 Estructura del proyecto

```
literalura/
├── src/
│   └── main/
│       ├── java/com/aluracursos/literalura/
│       │   ├── model/
│       │   │   ├── Autor.java
│       │   │   ├── Libro.java
│       │   │   ├── DatosAutor.java
│       │   │   ├── DatosLibro.java
│       │   │   └── DatosRespuestaAPI.java
│       │   ├── principal/
│       │   │   └── Principal.java
│       │   ├── repository/
│       │   │   ├── AutorRepository.java
│       │   │   └── LibroRepository.java
│       │   ├── service/
│       │   │   ├── ConsumoAPI.java
│       │   │   └── ConvierteDatos.java
│       │   └── LiteraluraApplication.java
│       └── resources/
│           └── application.properties
└── pom.xml
```

---

## ⚙️ Configuración y uso

### Requisitos previos

- Java 17 o superior
- PostgreSQL 16
- Maven

### 1. Clonar el repositorio

```bash
git clone https://github.com/TU_USUARIO/literalura.git
cd literalura
```

### 2. Crear la base de datos

Abre pgAdmin o psql y ejecuta:

```sql
CREATE DATABASE literalura;
```

### 3. Configurar `application.properties`

Crea el archivo `src/main/resources/application.properties` basándote en el archivo de ejemplo:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### 4. Ejecutar el proyecto

```bash
mvn spring-boot:run
```

O desde VS Code abre `LiteraluraApplication.java` y haz clic en **▶ Run**.

---

## 🖥️ Ejemplo de uso

```
╔══════════════════════════════════╗
║        LITERALURA - MENÚ         ║
╠══════════════════════════════════╣
║ 1 - Buscar libro por título      ║
║ 2 - Listar libros registrados    ║
║ 3 - Listar autores registrados   ║
║ 4 - Autores vivos en cierto año  ║
║ 5 - Libros por idioma            ║
║ 6 - Top 10 libros más descargados║
║ 0 - Salir                        ║
╚══════════════════════════════════╝
Elige una opción:
```

---

## 🌐 API utilizada

Este proyecto consume la API pública de **Gutendex**, basada en el catálogo de Project Gutenberg.

- **URL base:** `https://gutendex.com/books/`
- **Documentación:** https://gutendex.com/
- No requiere autenticación ni API key

---

## 👤 Autor

Desarrollado por **Nicolas Navarro** como parte del Challenge Literalura de Alura Latam.
