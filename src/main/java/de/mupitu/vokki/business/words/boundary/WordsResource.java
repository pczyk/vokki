package de.mupitu.vokki.business.words.boundary;

import de.mupitu.vokki.business.words.entity.Word;
import java.net.URI;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Stateless
@Path("words")
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class WordsResource {

    @Inject
    private WordManager manager;
    
    @Path("{@id}")
    public WordResource find(@PathParam("{@id}") final long id) {
        return new WordResource(id, manager);
    }
    
    @GET
    public List<Word> findAll() {
        return manager.findAll();
    }
    
    @PUT
    public Response save(final Word word, @Context final UriInfo uriInfo) {
        final Word entity = manager.save(word);
        final long id = entity.getId();
        final URI uri = uriInfo.getAbsolutePathBuilder().path("/" + id).build();
        return Response.created(uri).build();
    }
}
