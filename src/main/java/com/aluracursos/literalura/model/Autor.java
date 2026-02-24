package com.aluracursos.literalura.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String fullName;
    private Integer birthYear;
    private Integer deathYear;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> books = new ArrayList<>();

    public Autor() {}

    public Autor(DatosAutor data) {
        this.fullName = data.fullName();
        this.birthYear = data.birthYear();
        this.deathYear = data.deathYear();
    }

    public Long getId() { return id; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public Integer getBirthYear() { return birthYear; }
    public void setBirthYear(Integer birthYear) { this.birthYear = birthYear; }
    public Integer getDeathYear() { return deathYear; }
    public void setDeathYear(Integer deathYear) { this.deathYear = deathYear; }
    public List<Libro> getBooks() { return books; }
    public void setBooks(List<Libro> books) { this.books = books; }

    @Override
    public String toString() {
        return """
                --- Autor ---
                Nombre: %s
                Año de nacimiento: %s
                Año de fallecimiento: %s
                Libros: %s
                """.formatted(fullName, birthYear, deathYear,
                books.stream().map(Libro::getTitle).toList());
    }
}