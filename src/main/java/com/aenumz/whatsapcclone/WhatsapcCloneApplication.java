package com.aenumz.whatsapcclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.OAuthScope;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@SpringBootApplication
@EnableJpaAuditing
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.OAUTH2,
    scheme = "bearer",
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER,
    flows = @OAuthFlows(
        password = @OAuthFlow(
            tokenUrl = "http://localhost:9090/realms/whatsapp-clone/protocol/openid-connect/token",
            authorizationUrl = "http://localhost:9090/realms/whatsapp-clone/protocol/openid-connect/auth",
            refreshUrl = "http://localhost:9090/realms/whatsapp-clone/protocol/openid-connect/token",
            scopes = {
                @OAuthScope(name = "openid", description = "OpenID"),
                @OAuthScope(name = "profile", description = "Profile"),
                @OAuthScope(name = "email", description = "Email"),
                @OAuthScope(name = "address", description = "Address"),
                @OAuthScope(name = "phone", description = "Phone"),
                @OAuthScope(name = "offline_access", description = "Offline Access")
            }
        )
    )  
)
public class WhatsapcCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhatsapcCloneApplication.class, args);
    }

}
