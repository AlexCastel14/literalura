package dev.alexcastellanos.literalura.repository;

import dev.alexcastellanos.literalura.models.Author;
import dev.alexcastellanos.literalura.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    Optional<List<Author>> findByBirthyearLessThanAndDeathyearGreaterThan(Integer year, Integer year1);
}
