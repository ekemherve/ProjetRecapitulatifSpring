package herve.learning.projetrecapitulatif.dataaccess.repository;

import herve.learning.projetrecapitulatif.dataaccess.entity.CarEntity;
import herve.learning.projetrecapitulatif.dataaccess.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Repository
@Transactional
public interface CarRepository extends JpaRepository<CarEntity, Long> {

    Page<CarEntity> findByUserAndSoldIsFalse(UserEntity user, Pageable pageable);
    Page<CarEntity> findByUserAndSoldIsTrue(UserEntity user, Pageable pageable);
    Collection<CarEntity> findByUser(UserEntity userEntity);
}
