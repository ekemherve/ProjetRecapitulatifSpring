package herve.learning.projetrecapitulatif.dataaccess.repository;

import herve.learning.projetrecapitulatif.dataaccess.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);

    UserEntity findByUsernameOrEmail(String username, String email);
}