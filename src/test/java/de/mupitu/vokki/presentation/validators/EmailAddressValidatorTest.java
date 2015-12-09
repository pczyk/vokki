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
public class EmailAddressValidatorTest {

    @Mock
    private UserManager userManager;

    @InjectMocks
    private EmailAddressValidator validator;

    @Test(expected = ValidatorException.class)
    public void validateValueNull() {
        validator.validate(null, null, null);
    }

    @Test(expected = ValidatorException.class)
    public void validateValueNoString() {
        validator.validate(null, null, new Object());
    }

    @Test(expected = ValidatorException.class)
    public void validateInvalidAddress1() {
        validator.validate(null, null, "localhost");
    }
    
    @Test(expected = ValidatorException.class)
    public void validateInvalidAddress2() {
        validator.validate(null, null, "local.host");
    }
    
    @Test(expected = ValidatorException.class)
    public void validateInvalidAddress3() {
        validator.validate(null, null, "heisenberg@example.c");
    }

    @Test(expected = ValidatorException.class)
    public void validateExistingAddress() {
        final String address = "heisenberg@example.com";
        when(userManager.findByEmailAddress(address)).thenReturn(new User());

        validator.validate(null, null, address);
    }

    @Test
    public void validateUsernameCorrectAndNotExisting() {
        final String address = "heisenberg@example.com";
        when(userManager.findByEmailAddress(address)).thenReturn(null);

        validator.validate(null, null, address);
    }

    @Test
    public void validateUsernameCorrectAndNotExistingDots() {
        final String address = "heisen.berg@example.co.uk";
        when(userManager.findByEmailAddress(address)).thenReturn(null);

        validator.validate(null, null, address);
    }
}
