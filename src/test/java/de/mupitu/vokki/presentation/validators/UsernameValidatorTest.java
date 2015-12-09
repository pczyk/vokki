package de.mupitu.vokki.presentation.validators;

import de.mupitu.vokki.business.users.boundary.UserManager;
import de.mupitu.vokki.business.users.entity.User;
import javax.faces.validator.ValidatorException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UsernameValidatorTest {

    @Mock
    private UserManager userManager;

    @InjectMocks
    private UsernameValidator validator;

    @Test(expected = ValidatorException.class)
    public void validateValueNull() {
        validator.validate(null, null, null);
    }

    @Test(expected = ValidatorException.class)
    public void validateValueEmpty() {
        validator.validate(null, null, "");
    }

    @Test(expected = ValidatorException.class)
    public void validateUsernameTooShort() {
        validator.validate(null, null, "a");
    }

    @Test(expected = ValidatorException.class)
    public void validateExistingUsername() {
        final String username = "heisenberg";
        when(userManager.findByUsername(username)).thenReturn(new User());

        validator.validate(null, null, username);
    }

    @Test
    public void validateUsernameCorrectAndNotExisting() {
        final String username = "heisenberg";
        when(userManager.findByUsername(username)).thenReturn(null);

        validator.validate(null, null, username);
    }
}
