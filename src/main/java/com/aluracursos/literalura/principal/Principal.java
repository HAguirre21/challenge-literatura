package com.aluracursos.literalura.principal;

import com.aluracursos.literalura.model.*;
import com.aluracursos.literalura.repository.AutorRepository;
import com.aluracursos.literalura.repository.LibroRepository;
import com.aluracursos.literalura.service.ExternalApiService;
import com.aluracursos.literalura.service.DataMapperService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class Principal {

    private static final String API_BASE_URL = "https://gutendex.com/books/?search=";
    private final Scanner userInputScanner = new Scanner(System.in);

    private final ExternalApiService apiConsumer;
    private final DataMapperService dataMapper;
    private final LibroRepository bookCatalog;
    private final AutorRepository authorRegistry;

    public Principal(ExternalApiService apiConsumer, DataMapperService dataMapper,
                     LibroRepository bookCatalog, AutorRepository authorRegistry) {
        this.apiConsumer = apiConsumer;
        this.dataMapper = dataMapper;
        this.bookCatalog = bookCatalog;
        this.authorRegistry = authorRegistry;
    }

    public void runUserInterface() {
        int userChoice = -1;
        while (userChoice != 0) {
            String menuDisplay = """
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
                    Elige una opción:""";
            System.out.println(menuDisplay);

            try {
                userChoice = Integer.parseInt(userInputScanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠ Opción inválida.");
                continue;
            }

            switch (userChoice) {
                case 1 -> executeBookSearch();
                case 2 -> displayRegisteredBooks();
                case 3 -> displayRegisteredAuthors();
                case 4 -> displayAuthorsAliveInSpecifiedYear();
                case 5 -> displayBooksBySelectedLanguage();
                case 6 -> showTop10MostDownloadedBooks();
                case 0 -> System.out.println("👋 ¡Hasta luego!");
                default -> System.out.println("⚠ Opción no válida.");
            }
        }
    }

    private void executeBookSearch() {
        System.out.println("Escribe el título del libro:");
        String searchTitle = userInputScanner.nextLine();

        Optional<Libro> existingBook = bookCatalog.findByTitleContainingIgnoreCase(searchTitle);
        if (existingBook.isPresent()) {
            System.out.println("📚 Libro encontrado en la base de datos:");
            System.out.println(existingBook.get());
            return;
        }

        String rawJsonResponse = apiConsumer.fetchDataFromApi(API_BASE_URL + searchTitle.replace(" ", "+"));
        DatosRespuestaAPI apiResponse = dataMapper.mapJsonToClass(rawJsonResponse, DatosRespuestaAPI.class);

        if (apiResponse.resultDataList().isEmpty()) {
            System.out.println("❌ Libro no encontrado.");
            return;
        }

        DatosLibro bookData = apiResponse.resultDataList().get(0);
        Libro newBook = new Libro(bookData);

        if (bookData.authors() != null && !bookData.authors().isEmpty()) {
            DatosAutor authorData = bookData.authors().get(0);
            Autor assignedAuthor = authorRegistry.findByFullNameContainingIgnoreCase(authorData.fullName())
                    .orElseGet(() -> authorRegistry.save(new Autor(authorData)));
            newBook.setAuthor(assignedAuthor);
        }

        bookCatalog.save(newBook);
        System.out.println("✅ Libro guardado:\n" + newBook);
    }

    private void displayRegisteredBooks() {
        List<Libro> persistedBooks = bookCatalog.findAll();
        if (persistedBooks.isEmpty()) {
            System.out.println("📭 No hay libros registrados aún.");
            return;
        }
        persistedBooks.forEach(System.out::println);
    }

    private void displayRegisteredAuthors() {
        List<Autor> persistedAuthors = authorRegistry.findAll();
        if (persistedAuthors.isEmpty()) {
            System.out.println("📭 No hay autores registrados aún.");
            return;
        }
        persistedAuthors.forEach(System.out::println);
    }

    private void displayAuthorsAliveInSpecifiedYear() {
        System.out.println("Ingresa el año:");
        try {
            int targetYear = Integer.parseInt(userInputScanner.nextLine());
            List<Autor> matchingAuthors = authorRegistry.findAuthorsAliveInYear(targetYear);
            if (matchingAuthors.isEmpty()) {
                System.out.println("❌ No se encontraron autores vivos en " + targetYear);
            } else {
                matchingAuthors.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠ Año inválido.");
        }
    }

    private void displayBooksBySelectedLanguage() {
        System.out.println("""
                Idiomas disponibles:
                  es - Español
                  en - Inglés
                  fr - Francés
                  pt - Portugués
                Ingresa el código del idioma:""");
        String languageCode = userInputScanner.nextLine().trim().toLowerCase();
        List<Libro> booksInLanguage = bookCatalog.findByLanguage(languageCode);
        if (booksInLanguage.isEmpty()) {
            System.out.println("❌ No hay libros registrados en ese idioma.");
        } else {
            booksInLanguage.forEach(System.out::println);
        }
    }

    private void showTop10MostDownloadedBooks() {
        List<Libro> top10BooksList = bookCatalog.findTop10ByOrderByDownloadCountDesc(
                PageRequest.of(0, 10));
        System.out.println("🏆 TOP 10 LIBROS MÁS DESCARGADOS:");
        top10BooksList.forEach(System.out::println);
    }
}