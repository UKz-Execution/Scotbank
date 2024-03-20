package uk.co.asepstrath.bank;

import io.jooby.helper.UniRestExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UnitTest {

    @Test
    public void testUniRest() {
        UniRestExtension rest = new UniRestExtension();

        Assertions.assertThrows(Exception.class, () -> rest.install(null));
    }
}
