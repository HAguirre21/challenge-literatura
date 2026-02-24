package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Libro;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    Optional<Libro> findByTitleContainingIgnoreCase(String bookTitle);

    List<Libro> findByLanguage(String langCode);

    @Query("SELECT l FROM Libro l ORDER BY l.downloadCount DESC")
    List<Libro> findTop10ByOrderByDownloadCountDesc(Pageable pageable);
}