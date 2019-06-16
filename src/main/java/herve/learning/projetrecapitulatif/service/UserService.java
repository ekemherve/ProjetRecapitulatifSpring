package herve.learning.projetrecapitulatif.service;

import herve.learning.projetrecapitulatif.exception.CustomException;
import herve.learning.projetrecapitulatif.model.User;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface UserService {
    User save(User user) throws CustomException;

    User findById(Long id);

    void delete(Long id);

    Collection<User> findAll();

    User update(User user) throws CustomException;

    Collection<User> findAllWithPagination(Pageable pageable);

    User setRole(String role) throws CustomException;

    User findByUsername(String username) throws CustomException;
}
