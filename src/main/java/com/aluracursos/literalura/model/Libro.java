package com.aluracursos.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor author;

    private String language;
    private Integer downloadCount;

    public Libro() {}

    public Libro(DatosLibro data) {
        this.title = data.title();
        this.language = data.languages() != null && !data.languages().isEmpty()
                ? data.languages().get(0) : "Desconocido";
        this.downloadCount = data.downloadCount();
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Autor getAuthor() { return author; }
    public void setAuthor(Autor author) { this.author = author; }
    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
    public Integer getDownloadCount() { return downloadCount; }
    public void setDownloadCount(Integer downloadCount) { this.downloadCount = downloadCount; }

    @Override
    public String toString() {
        return """
                ----- LIBRO -----
                Título: %s
                Autor: %s
                Idioma: %s
                Número de descargas: %d
                -----------------
                """.formatted(title,
                author != null ? author.getFullName() : "Desconocido",
                language, downloadCount);
    }
}