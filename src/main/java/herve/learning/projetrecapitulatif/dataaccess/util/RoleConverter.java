package herve.learning.projetrecapitulatif.dataaccess.util;

import herve.learning.projetrecapitulatif.dataaccess.entity.RoleEntity;
import herve.learning.projetrecapitulatif.model.Role;
import org.springframework.stereotype.Component;

import java.util.Objects;
import static herve.learning.projetrecapitulatif.model.Constant.Role_IS_NULL;

@Component
public class RoleConverter {

    public Role toModel(RoleEntity roleEntity) {

        Role role =  new Role();

        if(Objects.isNull(roleEntity))
            throw new IllegalArgumentException(Role_IS_NULL);

        if(Objects.nonNull(roleEntity.getId()))
            role.setId(roleEntity.getId());

        role.setAuthority(roleEntity.getAuthority());

        return role;
    }

    public RoleEntity toEntity(Role role){

        RoleEntity roleEntity = new RoleEntity();

        if(Objects.isNull(role))
            throw new IllegalArgumentException(Role_IS_NULL);

        if(Objects.nonNull(role.getId()))
            roleEntity.setId(role.getId());

        roleEntity.setAuthority(role.getAuthority());

        return roleEntity;
    }
}
