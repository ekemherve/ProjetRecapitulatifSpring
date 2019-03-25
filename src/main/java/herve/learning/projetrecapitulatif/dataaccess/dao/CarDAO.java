package herve.learning.projetrecapitulatif.dataaccess.dao;

import herve.learning.projetrecapitulatif.dataaccess.entity.CarEntity;
import herve.learning.projetrecapitulatif.dataaccess.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}
