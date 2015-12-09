package de.mupitu.vokki.presentation.validators;

import de.mupitu.vokki.business.users.boundary.UserManager;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.apache.commons.validator.routines.EmailValidator;

@FacesValidator(value = "emailAddressValidator")
public class EmailAddressValidator implements Validator {

    @EJB
    UserManager userManager;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Fehlerhafte E-Mail-Adresse",
                            "E-Mail-Adresse darf nicht 'null' sein."));
        }

        if (!(value instanceof String)) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Fehlerhafte E-Mail-Adresse",
                            "E-Mail-Adresse muss ein String sein."));
        }

        final String emailAddress = (String) value;

        if (!EmailValidator.getInstance().isValid(emailAddress)) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Fehlerhafte E-Mail-Adresse",
                            String.format("'%s' ist keine gültige E-Mail-Adresse.", emailAddress)));
        }

        if (userManager.findByEmailAddress(emailAddress) != null) {
            throw new ValidatorException(
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Fehlerhafte E-Mail-Adresse",
                            String.format("E-Mail-Adresse '%s' ist bereits vergeben.", emailAddress)));
        }
    }

}
