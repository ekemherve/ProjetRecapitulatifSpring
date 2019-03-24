package herve.learning.projetrecapitulatif.dataaccess.util;


import herve.learning.projetrecapitulatif.dataaccess.entity.RoleEntity;
import herve.learning.projetrecapitulatif.dataaccess.entity.UserEntity;
import herve.learning.projetrecapitulatif.model.Role;
import herve.learning.projetrecapitulatif.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

import static herve.learning.projetrecapitulatif.model.Constant.USER_IS_NULL;

@Component
public class UserConverter {

    private final RoleConverter roleConverter;

    private final PasswordEncoder encoder;

    @Autowired
    public UserConverter(RoleConverter roleConverter, PasswordEncoder encoder) {
        this.roleConverter = roleConverter;
        this.encoder = encoder;
    }

    public UserEntity toEntity(User user) {

        UserEntity userEntity = new UserEntity();

        if(Objects.isNull(user))
            throw new IllegalArgumentException(USER_IS_NULL);

        if(Objects.nonNull(user))
            userEntity.setId(user.getId());

        userEntity.setUsername(user.getUsername());
        userEntity.setAuthority(user.getAuthority());
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(encoder.encode(user.getPassword()));
        userEntity.setBirthday(user.getBirthday());
        userEntity.setEnabled(user.getEnabled());
        userEntity.setCredentialsNonExpired(user.getCredentialsNonExpired());
        userEntity.setAccountNonLocked(user.getNonLocked());
        userEntity.setAccountNonExpired(user.getNonExpired());

        return userEntity;
    }

    public UserEntity toEntityWithRoles(User user) {


        UserEntity userEntity = this.toEntity(user);

        if(Objects.nonNull(user.getRoles())) {
            Collection<RoleEntity> roleEntities = Objects.isNull(user.getRoles())
                    ? null
                    : user.getRoles()
                          .stream()
                          .map(roleConverter::toEntity).collect(Collectors.toSet());
            userEntity.setRoles(roleEntities);
        }
        return userEntity;
    }

    public User toModel(UserEntity userEntity) {

        User user = new User();

        if (Objects.isNull(userEntity))
            throw new IllegalArgumentException(USER_IS_NULL);

        if (Objects.nonNull(userEntity)) {
            user.setId(userEntity.getId());
        }

        user.setUsername(userEntity.getUsername());
        user.setAuthority(userEntity.getAuthority());
        user.setEmail(userEntity.getEmail());
        user.setPassword(userEntity.getPassword());
        user.setBirthday(userEntity.getBirthday());
        user.setEnabled(userEntity.isEnabled());
        user.setCredentialsNonExpired(userEntity.isCredentialsNonExpired());
        user.setNonLocked(userEntity.isAccountNonLocked());
        user.setNonExpired(userEntity.isAccountNonExpired());
        return user;
    }

    public User toModelWithRoles (UserEntity userEntity){


        User user = this.toModel(userEntity);

        if (Objects.nonNull(userEntity.getRoles())) {
            Collection<Role> roles = Objects.isNull(userEntity.getRoles())
                    ? null
                    : userEntity.getRoles()
                    .stream()
                    .map(roleConverter::toModel).collect(Collectors.toSet());
            user.setRoles(roles);
        }
        return user;
    }
}
