package library.io;

import library.model.*;

import java.util.Collection;

public class ConsolePrinter {
    public void printBooks(Collection<Publication> publications) {
        Long counter = publications.stream()
                .filter(publication -> publication instanceof Book)
                .map(Publication::toString)
                .peek(this::printLine)
                .count();
        if (counter == 0)
            printLine("Brak książek w bibliotece");
    }

    public void printMagazines(Collection<Publication> publications) {
        Long counter = publications.stream()
                .filter(p->p instanceof Magazine)
                .map(Publication::toString)
                .peek(this::printLine)
                .count();
        if (counter == 0)
            printLine("Brak magazynów w bibliotece");
    }
    public void printLibraryUsers(Collection<LibraryUser> users){
        Long counter=users.stream()
                .map(User::toString)
                .peek(this::printLine)
                .count();
        if(counter==0){
            printLine("Brak użytkownikow w systemie");
        }
    }

    public void printLine(String text) {
        System.out.println(text);
    }
}