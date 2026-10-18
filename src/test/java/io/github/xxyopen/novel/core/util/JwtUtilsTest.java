package io.github.xxyopen.novel.core.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class JwtUtilsTest {

    private static final String SECRET = "test-secret-with-at-least-32-bytes-1234";

    @Test
    void signsAndParsesTokenWithConfiguredSecret() {
        JwtUtils jwtUtils = new JwtUtils(SECRET);
        String token = jwtUtils.generateToken(1001L, "front");

        assertEquals(1001L, jwtUtils.parseToken(token, "front"));
    }

    @Test
    void rejectsMissingSecret() {
        assertThrows(IllegalStateException.class, () -> new JwtUtils(" "));
    }

    @Test
    void rejectsWeakSecret() {
        assertThrows(IllegalStateException.class, () -> new JwtUtils("too-short"));
    }
}
