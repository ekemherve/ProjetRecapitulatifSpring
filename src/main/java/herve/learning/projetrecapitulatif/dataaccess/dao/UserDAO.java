package herve.learning.projetrecapitulatif.dataaccess.dao;

import herve.learning.projetrecapitulatif.dataaccess.entity.UserEntity;
import herve.learning.projetrecapitulatif.dataaccess.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class UserDAO {

    private final UserRepository userRepository;

    @Autowired
    public UserDAO(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity save(UserEntity userEntity){
        return userRepository.save(userEntity);
    }

    public void delete(UserEntity userEntity){

        this.userRepository.delete(userEntity);
    }

    public UserEntity findOne(Long id) {

        Optional<UserEntity> userEntity = userRepository.findById(id);

        return userEntity.orElse(null);
    }

    public UserEntity findByEmail(String email) {

        return userRepository.findByEmail(email);
    }

    public UserEntity findByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    public UserEntity findByUsernameOrEmail(String username, String email) {

        return userRepository.findByUsernameOrEmail(username, email);
    }

    public Collection<UserEntity> findAll() {

        return userRepository.findAll();
    }

    public Collection<UserEntity> findAllWithPagination(Pageable pageable){

        Page<UserEntity> pages = userRepository.findAll(pageable);
        return pages.getContent();
    }
}
