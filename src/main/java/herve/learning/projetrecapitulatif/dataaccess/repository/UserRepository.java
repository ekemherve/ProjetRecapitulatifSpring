package herve.learning.projetrecapitulatif.dataaccess.repository;

import herve.learning.projetrecapitulatif.dataaccess.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    UserEntity findByUsername(String username);

    /**
     * It's a name query using Native SQL which return all the users given the username and the email whose id is not equals to the given id parameter
     * @param username
     * @param email
     * @param id
     * @return : {@link Collection<UserEntity>}  => collection of UserEntity
     */
    @Query(value = "select u.* from projetrecapitulatif.users u where  " +
            " u.id<>?3 and (u.username=?1 or u.email=?2)", nativeQuery = true)
    Collection<UserEntity> findByUsernameOrEmailWithoutCurrentUser(String username, String email, Long id);

    // This Query doesn't work
    Collection<UserEntity> findByUsernameOrEmailAndIdNotContains(String username, String email, Long id);

    Collection<UserEntity> findByUsernameOrEmail(String username, String email);
}
