package de.mupitu.vokki.business.words.boundary;

import de.mupitu.vokki.business.words.entity.Word;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;

public class WordResource {

    private long id;
    private WordManager manager;

    public WordResource(long id, WordManager manager) {
        this.id = id;
        this.manager = manager;
    }
    
    @DELETE
    public void delete() {
        manager.remove(id);
    }
    
    @PUT
    public Word update(final Word word) {
        word.setId(id);
        return manager.save(word);
    }
    
    @GET
    public Word find() {
        return manager.findById(id);
    }
}
