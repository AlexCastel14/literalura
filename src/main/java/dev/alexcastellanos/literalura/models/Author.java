package dev.alexcastellanos.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "authors")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String name;
    @JsonAlias ("birth_year") private Integer birthyear;
    @JsonAlias ("death_year") private Integer deathyear;
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "authors",cascade = CascadeType.PERSIST)
    private List<Book> books;


// Getters and Setters
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Integer getBirthyear() {
        return birthyear;
    }

    public void setBirthyear(Integer birthyear) {
        this.birthyear = birthyear;
    }

    public Integer getDeathyear() {
        return deathyear;
    }

    public void setDeath_year(Integer death_year) {
        this.deathyear = deathyear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
