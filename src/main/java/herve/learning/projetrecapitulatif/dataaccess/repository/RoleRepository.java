package herve.learning.projetrecapitulatif.dataaccess.repository;

import herve.learning.projetrecapitulatif.dataaccess.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    RoleEntity findByAuthority(String authority);
}
