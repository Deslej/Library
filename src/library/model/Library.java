package library.model;

import library.exception.PublicationArleadyExistsException;
import library.exception.UserArleadyExistsException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class Library implements Serializable {

    private Map<String,Publication> publications=new HashMap<>();
    private Map<String,LibraryUser> users=new HashMap<>();

    public Map<String, LibraryUser> getUsers() {
        return users;
    }

    public Map<String, Publication> getPublications() {
        return publications;
    }
    public Optional<Publication> findPublicationByTitle(String title){
        return  Optional.ofNullable(publications.get(title));
    }

    public void addPublication(Publication publication) {
    if(publications.containsKey(publication.getTitle()))
        throw new PublicationArleadyExistsException("Juz istnieje taka publickacja:"+publication.getTitle());
    else {
        publications.put(publication.getTitle(),publication);
    }

    }
    public void addUser(LibraryUser user){
        if(publications.containsKey(user.getPesel())){
            throw new UserArleadyExistsException("Użytkownik o takim peselu juz istniej:"+user.getPesel());
        }else {
            users.put(user.getPesel(),user);
        }
    }
    public boolean removePublication(Publication publication){
        if(publications.containsValue(publication)){
            publications.remove(publication.getTitle());
            return true;
        }else{
            return false;
        }

    }
}
