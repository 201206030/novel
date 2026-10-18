package io.github.xxyopen.novel.core.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Requires an explicitly configured JWT signing secret in production.
 */
@Component
@Profile("prod")
@Slf4j
public class JwtSecretValidator implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) {
        String jwtSecret = System.getenv("JWT_SECRET");
        if (jwtSecret == null || jwtSecret.isBlank()) {
            log.error("JWT_SECRET environment variable is not configured. "
                + "Please configure it before starting the application. "
                + "Linux/macOS: export JWT_SECRET=\"$(openssl rand -base64 64 | tr -d '\\n')\"");
            throw new IllegalStateException("Missing required environment variable: JWT_SECRET");
        }
    }
}
