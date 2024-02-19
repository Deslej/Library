package library.io.file;

import library.exception.DataExportException;
import library.exception.DataImportException;
import library.exception.InvalidDataException;
import library.model.*;

import java.io.*;
import java.util.Collection;

public class CsvFileManager implements FileManager{
    private static final String FILE_NAME="Library.csv";
    private static final String USERS_FILE_NAME="Library_users.csv";
    @Override
    public Library importData() {
        Library library=new Library();
        importPublications(library);
        importUsers(library);
        return library;
    }

    private void importUsers(Library library) {
        try (BufferedReader bufferedReader=new BufferedReader(new FileReader(USERS_FILE_NAME))){
            bufferedReader.lines()
                    .map(this::createUserFromString)
                    .forEach(library::addUser);
        } catch (FileNotFoundException e) {
            throw new DataImportException("Brak pliku: "+USERS_FILE_NAME);
        } catch (IOException e) {
            throw new DataImportException("Bład odczytu pliku: "+USERS_FILE_NAME);
        }
    }

    private LibraryUser createUserFromString(String line) {
        String[] split = line.split(";");
        String name=split[0];
        String surname=split[1];
        String pesel=split[2];
        return new LibraryUser(name,surname,pesel);
    }

    private void importPublications(Library library) {
        try (BufferedReader bufferedReader=new BufferedReader(new FileReader(FILE_NAME))){
                bufferedReader.lines()
                        .map(this::createObjectFromString)
                        .forEach(library::addPublication);
        } catch (FileNotFoundException e) {
            throw new DataImportException("Brak pliku: "+FILE_NAME);
        } catch (IOException e) {
            throw new DataImportException("Bład odczytu pliku: "+FILE_NAME);
        }
    }


    private Publication createObjectFromString(String line) {
        String[] split = line.split(";");
        String type = split[0];
        if(Book.TYPE.equals(type)){
                return createBook(split);
        } else if (Magazine.TYPE.equals(type)) {
                return createMagzine(split);
        }
        throw new InvalidDataException("Nieznany typ publikacji:"+type);
    }

    private Magazine createMagzine(String[] split) {
        String wydawnictwo=split[1];
        int rok=Integer.valueOf(split[2]);
        int miesiac=Integer.valueOf(split[3]);
        int dzien=Integer.valueOf(split[4]);
        String tytul=split[5];
        String jezyk=split[6];
        return new Magazine(tytul,wydawnictwo,jezyk,rok,miesiac,dzien);
    }

    private Book createBook(String[] split) {
        String wydawnictwo=split[2];
        int rok=Integer.valueOf(split[3]);
        String autor=split[4];
        String isbn=split[6];
        String tytul=split[1];
        int strony=Integer.valueOf(split[5]);
        return new Book(tytul,autor,rok,strony,wydawnictwo,isbn);
    }

    @Override
    public void exportData(Library library) {
        exportPublications(library);
        exportUsers(library);
    }
    private void exportPublications(Library library) {
        Collection<Publication> publications = library.getPublications().values();
        exportToCsv(publications, FILE_NAME);
    }

    private void exportUsers(Library library) {
        Collection<LibraryUser> users = library.getUsers().values();
        exportToCsv(users, USERS_FILE_NAME);
    }
    private <T extends CsvConvertible> void exportToCsv(Collection<T> collection, String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (T element : collection) {
                bufferedWriter.write(element.toCsv());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new DataExportException("Błąd zapisu danych do pliku " + fileName);
        }
    }
}
