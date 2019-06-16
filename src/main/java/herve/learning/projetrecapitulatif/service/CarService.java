package herve.learning.projetrecapitulatif.service;

import herve.learning.projetrecapitulatif.exception.CustomException;
import herve.learning.projetrecapitulatif.model.Car;
import herve.learning.projetrecapitulatif.model.User;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface CarService {

    Car save(Car car) throws CustomException;
    Car findById(Long id) throws CustomException;
    Car update(Car car) throws CustomException;
    Collection<Car> findAll();

    Collection<Car> findUnSoldCar(Pageable pageable);
    Collection<Integer> findSoldAndUnSoldCarsSize(String username) throws CustomException;
    Collection<Car> findSoldCars(Pageable pageable);
}
