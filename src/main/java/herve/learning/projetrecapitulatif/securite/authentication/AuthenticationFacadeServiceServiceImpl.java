package herve.learning.projetrecapitulatif.securite.authentication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationFacadeServiceServiceImpl implements AuthenticationFacadeService {
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Override
    public String getAuthenticated() {
        return getAuthentication().getName();
    }

}
