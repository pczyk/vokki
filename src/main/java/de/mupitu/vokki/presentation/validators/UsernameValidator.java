package de.mupitu.vokki.presentation.validators;

import de.mupitu.vokki.business.users.boundary.UserManager;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator(value = "usernameValidator")
public class UsernameValidator implements Validator {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 16;

    @EJB
    UserManager userManager;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehlerhafter Benutzername", "Benutzername darf nicht 'null' sein."));
        }

        if (!(value instanceof String)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehlerhafter Benutzername", "Benutzername muss ein String sein."));
        }

        final String username = (String) value;

        if (username.length() < MIN_LENGTH) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehlerhafter Benutzername",
                    String.format("Benutzername muss aus mindestens %d Zeichen bestehen.", MIN_LENGTH)));
        }

        if (username.length() > MAX_LENGTH) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehlerhafter Benutzername",
                    String.format("Benutzername darf aus höchstens %d Zeichen bestehen.", MAX_LENGTH)));
        }

        if (userManager.findByUsername(username) != null) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Fehlerhafter Benutzername",
                    String.format("Benutzername '%s' ist bereits vergeben.", username)));
        }
    }

}
