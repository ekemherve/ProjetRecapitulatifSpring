package herve.learning.projetrecapitulatif.dataaccess.dao;

import herve.learning.projetrecapitulatif.dataaccess.entity.RoleEntity;
import herve.learning.projetrecapitulatif.dataaccess.repository.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RoleDAO  {

    private final RoleRepository roleRepository;

    public RoleDAO(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleEntity save(RoleEntity roleEntity){

        return  roleRepository.save(roleEntity);
    }

    public RoleEntity findByAuthority(String authority){

        return roleRepository.findByAuthority(authority);
    }

    public RoleEntity createRoleIfNotExist(String authority){

        RoleEntity roleEntity = roleRepository.findByAuthority(authority);

        if(Objects.isNull(roleEntity)) {
            RoleEntity role = new RoleEntity();
            role.setAuthority(authority);
            roleEntity = roleRepository.save(role);
        }

        return roleEntity;
    }


}
