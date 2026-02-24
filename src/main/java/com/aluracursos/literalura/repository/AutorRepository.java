package com.aluracursos.literalura.repository;

import com.aluracursos.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByFullNameContainingIgnoreCase(String name);

    @Query("SELECT a FROM Autor a WHERE a.birthYear <= :year AND (a.deathYear IS NULL OR a.deathYear >= :year)")
    List<Autor> findAuthorsAliveInYear(@Param("year") Integer year);
}