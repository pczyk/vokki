package de.mupitu.vokki.presentation;

import de.mupitu.vokki.business.words.entity.Language;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 * Contains common functionality shared between various JSF beans
 *
 * @author Martin Filipczyk
 */
public abstract class BaseController implements Serializable {

    private static final String FLAG_BASE_PATH = "images/";
    
    /**
     * Sends a HTTP Bad Request (400) to the client and completes the response
     *
     * @see
     * <a href="https://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html#sec10.4.1">HTTP
     * Status Code 400</a>
     *
     * @param message message to be sent among the HTTP status
     */
    protected void sendBadRequestResponse(final String message) {
        final FacesContext context = FacesContext.getCurrentInstance();

        try {
            context.getExternalContext().responseSendError(HttpServletResponse.SC_BAD_REQUEST, message);
            context.responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(BaseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getFlagPathForLanguage(final Language language) {
        return FLAG_BASE_PATH + language.getFlagPath();
    }
}
