package Utils;


import org.junit.jupiter.api.Test;

import java.util.Base64;

import static Utils.EncryptUtils.atob;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EncryptUtilsTest {

    @Test
    void checkDecryptionReturnGoodValue() {
        String originalInput = "test input";
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());

        assertTrue(atob(encodedString).equals(originalInput));
    }

    @Test
    void checkDecryptionReturnBadValue() {
        String originalInput = "test input1";
        String badInput = "test input2";
        String encodedString = Base64.getEncoder().encodeToString(originalInput.getBytes());

        assertFalse(atob(encodedString).equals(badInput));
    }
}