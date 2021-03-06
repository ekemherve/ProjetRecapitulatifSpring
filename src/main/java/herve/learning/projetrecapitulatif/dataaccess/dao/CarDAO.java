package herve.learning.projetrecapitulatif.dataaccess.dao;

import herve.learning.projetrecapitulatif.dataaccess.entity.CarEntity;
import herve.learning.projetrecapitulatif.dataaccess.entity.UserEntity;
import herve.learning.projetrecapitulatif.dataaccess.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Optional;

@Component
public class CarDAO {

    private final CarRepository carRepository;

    @Autowired
    public CarDAO(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public CarEntity save(CarEntity carEntity){

        return carRepository.save(carEntity);
    }

    public Collection<CarEntity> findAll(){

        return carRepository.findAll();
    }

    public Optional<CarEntity> findById(Long id) {

        return carRepository.findById(id);
    }

    public Page<CarEntity> findUnSoldCars(UserEntity userEntity, Pageable pageable) {

        return carRepository.findByUserAndSoldIsFalse(userEntity, pageable);
    }

    public Page<CarEntity> findSoldCars(UserEntity userEntity, Pageable pageable) {

        return carRepository.findByUserAndSoldIsTrue(userEntity, pageable);
    }

    public Collection<CarEntity> findByUser(UserEntity userEntity){
        return  carRepository.findByUser(userEntity);
    }
}
