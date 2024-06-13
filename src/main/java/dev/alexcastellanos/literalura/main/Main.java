package dev.alexcastellanos.literalura.main;

import dev.alexcastellanos.literalura.models.Book;
import dev.alexcastellanos.literalura.models.BookSearchResult;
import dev.alexcastellanos.literalura.service.APIConsumption;
import dev.alexcastellanos.literalura.service.ConvertDataToObject;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Main {
    Scanner keyboardInput = new Scanner(System.in);
    private APIConsumption apiConsumption = new APIConsumption();
    private ConvertDataToObject converterStringToObject = new ConvertDataToObject();

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
            selectedChoice = keyboardInput.nextInt();
            keyboardInput.nextLine();
            System.out.println(selectedChoice);
            switch (selectedChoice){
                case 1:
                    System.out.println("Por favor introduce el libro buscado");
                    String searchedWord = keyboardInput.nextLine();
                    String uri = String.format("https://gutendex.com/books/?search=%s",searchedWord).replace(" ", "%20");
                    String searchResult = apiConsumption.getDataFromAPI(uri);
                    BookSearchResult searchResultObject = converterStringToObject.obtainData(searchResult, BookSearchResult.class);
                    if (searchResultObject.getCount()==0){
                        System.out.println("No se han encontrado resultados para tu busqueda");
                    } else {
                        List<Book> bookList = searchResultObject.getBookList();
                        System.out.println(bookList.get(0));
                    }
                    break;
                case 2:
                    System.out.println("Mostrar libros registrados");
                    break;
                case 3:
                    System.out.println("Mostar autores registrados");
                    break;
                case 4:
                    System.out.println("Mostrar autores vivos dado un año");
                    break;
                case 5:
                    System.out.println("Mostrar libros por idioma");
                    break;
                case 9:
                    System.out.println("Saliendo de la aplicacion...");
                    break;
                default:
                    System.out.println("Por favor ingresa una opcion valida...");
                    break;
            }
        }
    }
}
