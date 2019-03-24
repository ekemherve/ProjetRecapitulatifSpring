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
    public void delete(User user) {

        this.userDAO.delete(userConverter.toEntityWithRoles(user));
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

        Role role = this.createRoleIfNotExist("ROLE_USER");

        UserEntity userEntity = userDAO.findByUsernameOrEmail(user.getUsername(), user.getEmail());

        if(Objects.nonNull(userEntity))
            throw new CustomException(USER_ALREADY_EXISTS);

        user.addRole(role);
        userEntity = userConverter.toEntityWithRoles(user);
        userEntity = userDAO.save(userEntity);

        return userConverter.toModelWithRoles(userEntity);
    }

    @Override
    public User update(User user) throws CustomException {

        UserEntity userToSave = this.getValidUserOrThrowException(user);

        userToSave = userDAO.save(userToSave);

        return userConverter.toModelWithRoles(userToSave);
    }

    private Role createRoleIfNotExist(String authority){

        RoleEntity roleEntity = roleDAO.createRoleIfNotExist(authority);

        return roleConverter.toModel(roleEntity);
    }

    @Override
    public Collection<User> findAllWithPagination(Pageable pageable){

        Collection<UserEntity> userEntities = userDAO.findAllWithPagination(pageable);
        return userEntities.stream().map(userConverter::toModelWithRoles).collect(Collectors.toSet());
    }

    private UserEntity getUserEntityConnected() {
        String username = authenticationFacadeService.getAuthentication().getName();
        return userDAO.findByUsername(username);
    }

    private UserEntity getValidUserOrThrowException(User userToBeSaved) throws CustomException {

        UserEntity userAuthenticated = this.getUserEntityConnected();

        if(Objects.isNull(userAuthenticated)){
            throw new CustomException(USER_NOT_AUTHENTICATED);
        }

        if(Objects.isNull(userToBeSaved)){
            throw new IllegalArgumentException(USER_DOESNT_EXIST);
        }

        UserEntity userFound = userDAO.findByUsernameOrEmail(userToBeSaved.getUsername(), userToBeSaved.getEmail());

        if(Objects.nonNull(userFound) && !Objects.equals(userFound, userAuthenticated)){

            throw new CustomException(USERNAME_OR_EMAIL_ALREADY_EXIST);
        }

        userToBeSaved.setId(userAuthenticated.getId());
        userAuthenticated = userConverter.toEntityWithRoles(userToBeSaved);

        return userAuthenticated;
    }

    private void setUserSecurityParameter(User user){

        user.setEnabled(true);
        user.setNonExpired(true);
        user.setNonLocked(true);
        user.setCredentialsNonExpired(true);
    }

}