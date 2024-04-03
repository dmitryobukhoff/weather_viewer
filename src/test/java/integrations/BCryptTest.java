package integrations;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.junit.Test;
import ru.dmitryobukhoff.utils.BCryptUtil;

import static org.junit.jupiter.api.Assertions.*;


public class BCryptTest {
    @Test
    public void rightPasswordAccepted(){
        String s2 = BCryptUtil.hash("qwerty");
        assertTrue(BCryptUtil.verified("qwerty", s2));
    }

    @Test
    public void wrongAnswerNotAccepted(){
        String s2 = BCryptUtil.hash("Qwerty");
        assertFalse(BCryptUtil.verified("qwerty", s2));
    }
}
