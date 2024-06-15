package dev.alexcastellanos.literalura.repository;

import dev.alexcastellanos.literalura.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    @Query(value = "SELECT * FROM books WHERE :language = ANY(languages)", nativeQuery = true)
    Optional<List<Book>> getBooksPerLenguage(String language);
}