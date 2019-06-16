package herve.learning.projetrecapitulatif.service.implementation;

import herve.learning.projetrecapitulatif.dataaccess.dao.RoleDAO;
import herve.learning.projetrecapitulatif.dataaccess.dao.UserDAO;
import herve.learning.projetrecapitulatif.dataaccess.entity.RoleEntity;
import herve.learning.projetrecapitulatif.dataaccess.entity.UserEntity;
import herve.learning.projetrecapitulatif.dataaccess.util.RoleConverter;
import herve.learning.projetrecapitulatif.dataaccess.util.UserConverter;
import herve.learning.projetrecapitulatif.exception.CustomException;
import herve.learning.projetrecapitulatif.model.Role;
import herve.learning.projetrecapitulatif.model.User;
import herve.learning.projetrecapitulatif.securite.authentication.AuthenticationFacadeService;
import herve.learning.projetrecapitulatif.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import static herve.learning.projetrecapitulatif.model.Constant.*;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final RoleDAO roleDAO;
    private final UserConverter userConverter;
    private final RoleConverter roleConverter;
    private final AuthenticationFacadeService authenticationFacadeService;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RoleDAO roleDAO, UserConverter userConverter, RoleConverter roleConverter,
                           AuthenticationFacadeService authenticationFacadeService) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.userConverter = userConverter;
        this.roleConverter = roleConverter;
        this.authenticationFacadeService = authenticationFacadeService;
    }

    @Override
    public User findById(Long id){

        UserEntity userEntity = userDAO.findOne(id);

        return Objects.isNull(userEntity) ? null : userConverter.toModelWithRoles(userEntity);
    }

    @Override
    public void delete(Long id) {

        UserEntity userEntity = userDAO.findOne(id);
        this.userDAO.delete(userEntity);
    }

    @Override
    public Collection<User> findAll(){

        return userDAO.findAll().stream()
                .map(userEntity -> userConverter.toModelWithRoles(userEntity))
                .collect(Collectors.toSet());
    }

    @Override
    public User save(User user) throws CustomException {

        setUserSecurityParameter(user);

        Role role = this.convertToRole(createRoleIfNotExist("ROLE_ADMIN"));

        Collection<UserEntity> userEntities = userDAO.findByUsernameOrEmail(user.getUsername(), user.getEmail());

        if(Objects.nonNull(userEntities) && userEntities.size() > 0)
            throw new CustomException(USER_ALREADY_EXISTS);

        user.addRole(role);
        UserEntity userEntity = userConverter.toEntityWithRoles(user);
        userEntity = userDAO.save(userEntity);

        return userConverter.toModelWithRoles(userEntity);
    }

    @Override
    public User update(User user) throws CustomException {

        UserEntity userToSave = this.getValidUserOrThrowException(user);

        userToSave = userDAO.save(userToSave);

        return userConverter.toModelWithRoles(userToSave);
    }

    @Override
    public User findByUsername(String username) throws CustomException {

        if(Objects.isNull(username))
            throw new IllegalArgumentException(USER_IS_NULL);
        UserEntity userEntity = userDAO.findByUsername(username);
        if(Objects.isNull(userEntity))
            throw new CustomException(USER_DOESNT_EXIST);

        return userConverter.toModelWithRoles(userEntity);
    }

    private RoleEntity createRoleIfNotExist(String authority){
        return roleDAO.createRoleIfNotExist(authority);
    }

    private Role convertToRole(RoleEntity roleEntity) {
        return roleConverter.toModel(roleEntity);
    }

    @Override
    public Collection<User> findAllWithPagination(Pageable pageable){

        Collection<UserEntity> userEntities = userDAO.findAllWithPagination(pageable);
        return userEntities.stream().map(userConverter::toModelWithRoles).collect(Collectors.toSet());
    }

    @Override
    public User setRole(String role) throws CustomException {

        String username = authenticationFacadeService.getAuthenticated();
        if(Objects.isNull(username))
                throw new CustomException(USER_DOESNT_EXIST);
        UserEntity userEntity = userDAO.findByUsername(username);
        userEntity.addRole(createRoleIfNotExist(role));
        userEntity = userDAO.save(userEntity);
        return userConverter.toModelWithRoles(userEntity);
    }

    private UserEntity getUserEntityConnected() {
        String username = authenticationFacadeService.getAuthentication().getName();
        return userDAO.findByUsername(username);
    }

    private UserEntity getValidUserOrThrowException(User userToBeUpdated) throws CustomException {

        UserEntity userAuthenticated = userDAO.findByUsername(authenticationFacadeService.getAuthenticated());

        if(Objects.isNull(userAuthenticated))
            throw new CustomException(USER_NOT_AUTHENTICATED);
        if(Objects.isNull(userToBeUpdated))
            throw new CustomException(USER_IS_NULL);

        Collection<UserEntity> usersFound = userDAO.findByUsernameOrEmailWithoutCurrentUser(userToBeUpdated.getUsername(),
                userToBeUpdated.getEmail(), userAuthenticated.getId());

        if(Objects.nonNull(usersFound) && usersFound.size()>0)
            throw new CustomException(USERNAME_OR_EMAIL_ALREADY_EXIST);

        // If the password is the same, we don't want to encrypt the password again, so we set userAuthenticated
        // fields other than the password and we return it to be saved
        if(Objects.equals(userAuthenticated.getPassword(),userToBeUpdated.getPassword())) {
            this.setUser(userAuthenticated, userToBeUpdated);
            return userAuthenticated;
        }

        // If the password change we need to encrypt it
        userToBeUpdated.setId(userAuthenticated.getId());
        this.setUserSecurityParameter(userToBeUpdated);
        userAuthenticated = userConverter.toEntityWithRoles(userToBeUpdated);

        return userAuthenticated;
    }

    private void setUserSecurityParameter(User user) {

        user.setEnabled(true);
        user.setNonExpired(true);
        user.setNonLocked(true);
        user.setCredentialsNonExpired(true);
    }

    private void setUser(UserEntity userFromDataBase, User newUser) {
        userFromDataBase.setUsername(newUser.getUsername());
        userFromDataBase.setEmail(newUser.getEmail());
        userFromDataBase.setBirthday(newUser.getBirthday());
    }
}
