package herve.learning.projetrecapitulatif.securite.authentication;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacadeService {

    Authentication getAuthentication();

    String getAuthenticated();
}
