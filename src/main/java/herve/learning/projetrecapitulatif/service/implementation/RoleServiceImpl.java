package herve.learning.projetrecapitulatif.service.implementation;

import herve.learning.projetrecapitulatif.dataaccess.dao.RoleDAO;
import herve.learning.projetrecapitulatif.dataaccess.entity.RoleEntity;
import herve.learning.projetrecapitulatif.dataaccess.util.RoleConverter;
import herve.learning.projetrecapitulatif.model.Role;
import herve.learning.projetrecapitulatif.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static herve.learning.projetrecapitulatif.model.Constant.*;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleDAO roleDAO;
    private final RoleConverter roleConverter;

    public RoleServiceImpl(RoleDAO roleDAO, RoleConverter roleConverter) {
        this.roleDAO = roleDAO;
        this.roleConverter = roleConverter;
    }

    @Override
    public Role save(Role role) throws Exception {

        RoleEntity roleEntity = roleDAO.findByAuthority(role.getAuthority());

        if(Objects.nonNull(roleEntity))
            throw new Exception(ROLE_ALREADY_EXISTS);

        roleEntity = roleConverter.toEntity(role);
        roleEntity = roleDAO.save(roleEntity);
        return roleConverter.toModel(roleEntity);
    }
}
