import org.junit.Test;
import ru.dmitryobukhoff.validators.EntitiesValidator;

import static org.junit.jupiter.api.Assertions.*;

public class EntitiesValidatorTest {

    private EntitiesValidator entitiesValidator = new EntitiesValidator();

    @Test
    public void loginContentSign(){
        //Проверка, что логин 'lo@gin' не проходит валидацию
        String login = "lo@ginrt";
        assertFalse(entitiesValidator.isLoginValid(login));
    }

    @Test
    public void loginContentWithManySigns(){
        String login = "lo,gi#nrt";
        assertFalse(entitiesValidator.isLoginValid(login));
    }

    @Test
    public void loginHasFewLetters(){
        String login = "log";
        assertFalse(entitiesValidator.isLoginValid(login));
    }

    @Test
    public void loginHasManyLetters(){
        String login = "manylettersinthismessagewithbiglength";
        assertFalse(entitiesValidator.isLoginValid(login));
    }

    @Test
    public void loginIsValid(){
        String login = "dmitryobukhoff";
        assertTrue(entitiesValidator.isLoginValid(login));
    }
}
