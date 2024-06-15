package dev.alexcastellanos.literalura.main;

import dev.alexcastellanos.literalura.models.Author;
import dev.alexcastellanos.literalura.models.Book;
import dev.alexcastellanos.literalura.models.BookSearchResult;
import dev.alexcastellanos.literalura.repository.AuthorRepository;
import dev.alexcastellanos.literalura.repository.BookRepository;
import dev.alexcastellanos.literalura.service.APIConsumption;
import dev.alexcastellanos.literalura.service.ConvertDataToObject;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

public class Main {
    Scanner keyboardInput = new Scanner(System.in);
    private APIConsumption apiConsumption = new APIConsumption();
    private ConvertDataToObject converterStringToObject = new ConvertDataToObject();
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Main(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void showOptionMenu(){
        Integer selectedChoice = 0;

        while (selectedChoice != 9){
            System.out.println("""
                    
                    
                    Bienvenido a LiterAlura, por favor selecciona una opcion:
                    1 - Buscar un libro por nombre e insertarlo en la base de datos
                    2 - Mostrar libros registrados
                    3 - Mostar autores registrados
                    4 - Mostrar autores vivos dado un año
                    5 - Mostrar libros por idioma
                    
                    9 - Salir 
                    """);
            try{
                selectedChoice = keyboardInput.nextInt();
                keyboardInput.nextLine();
            } catch (InputMismatchException e){
                System.out.println("Por favor ingresa una opcion valida!!\n\n");
                keyboardInput.nextLine();
                continue;
            }

            switch (selectedChoice){
                case 1:
                    getBookFromAPI();
                    break;
                case 2:
                    showStoredBooks();
                    break;
                case 3:
                    showStoredAuthors();
                    break;
                case 4:
                    System.out.println("Mostrar autores vivos dado un año");
                    try {
                        showAuthorsGivenAYear();
                    } catch (InputMismatchException e){
                        System.out.println("Por favor ingresa un año valido!!\n\n");
                        keyboardInput.nextLine();
                        continue;
                    }
                    break;
                case 5:
                    System.out.println("Mostrar libros por idioma");
                    showBooksPerLenguage();
                    break;
                case 9:
                    System.out.println("Saliendo de la aplicacion...");
                    break;
                default:
                    System.out.println("Por favor ingresa una opcion valida...");
                    break;
            }

            // Wait to see application answers
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e){
                System.out.println("Solo tenias que esperar!!!");
            }
        }
    }


    public void getBookFromAPI(){
        System.out.println("Por favor introduce el libro buscado");
        String searchedWord = keyboardInput.nextLine();

        String uri = String.format("https://gutendex.com/books/?search=%s",searchedWord).replace(" ", "%20");
        String searchResult = apiConsumption.getDataFromAPI(uri);
        BookSearchResult searchResultObject = converterStringToObject.obtainData(searchResult, BookSearchResult.class);

        if (searchResultObject.getCount()==0){
            System.out.println("No se han encontrado resultados para tu busqueda");
        } else {
            List<Book> bookList = searchResultObject.getBookList();
            Book firstBook = bookList.get(0);
            System.out.println(firstBook.getTitle());
            try{
                Author firstBookAuthor = firstBook.getAuthors().get(0);
                authorRepository.save(firstBookAuthor);
                System.out.println("Se ha guardado el author: " + firstBookAuthor.getName());
                bookRepository.save(firstBook);
                System.out.println("Se ha guardado el libro: " + firstBook.getTitle());
            } catch (DataIntegrityViolationException e){
                System.out.println("El libro buscado ya se encuentra en la base de datos...");
            }
        }
    }

    public void showStoredBooks(){
        System.out.println("Mostrar libros registrados");
        List<Book> books = bookRepository.findAll();
        books.forEach(System.out::println);
    }

    public void showStoredAuthors(){
        System.out.println("Mostrar libros registrados");
        List<Author> authors = authorRepository.findAll();
        authors.forEach(System.out::println);
    }

    public void showAuthorsGivenAYear(){
        System.out.println("Por favor introduce el año deseado...");
        try {
            Integer year = keyboardInput.nextInt();
            keyboardInput.nextLine();

            Optional<List<Author>> authorsOpt = authorRepository.findByBirthyearLessThanAndDeathyearGreaterThan(year, year);

            if (authorsOpt.isPresent()){
                List<Author> authors = authorsOpt.get();
                if (authors.size() != 0){
                    authors.forEach(System.out::println);
                } else {
                    System.out.println("No se han encontrado autores en este año.");
                }
            } else {
                System.out.println("No se han encontrado autores en este año.");
            }
        } catch (InputMismatchException e){
            throw new InputMismatchException();
        }
    }

    private void showBooksPerLenguage() {
        try {
            String language = keyboardInput.nextLine();

            Optional<List<Book>> booksFoundOpt = bookRepository.getBooksPerLenguage(language);

            if (booksFoundOpt.isPresent()){
                List<Book> booksFound = booksFoundOpt.get();
                System.out.println("Se encontraron: " + booksFound.size());
                if (booksFound.size() != 0){
                    booksFound.forEach(System.out::println);
                } else {
                    System.out.println("No se han encontrado libros de este idioma.");
                }
            } else {
                System.out.println("No se han encontrado libros de este idioma1.");
            }
        } catch (InputMismatchException e){
            throw new InputMismatchException();
        }
    }
}
