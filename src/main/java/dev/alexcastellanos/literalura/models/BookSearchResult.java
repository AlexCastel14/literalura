package dev.alexcastellanos.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookSearchResult {
    @JsonAlias("count") Integer count;
    @JsonAlias("results") List<Book> bookList;

    public Integer getCount() {
        return count;
    }

    public List<Book> getBookList() {
        return bookList;
    }
}
