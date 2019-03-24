package herve.learning.projetrecapitulatif.service;

import herve.learning.projetrecapitulatif.exception.CustomException;
import herve.learning.projetrecapitulatif.model.User;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface UserService {
    User save(User user) throws CustomException;

    User findById(Long id);

    void delete(User user);

    Collection<User> findAll();

    User update(User user) throws CustomException;

    Collection<User> findAllWithPagination(Pageable pageable);
}
