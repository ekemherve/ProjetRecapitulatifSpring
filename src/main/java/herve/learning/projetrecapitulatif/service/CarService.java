package herve.learning.projetrecapitulatif.service;

import herve.learning.projetrecapitulatif.exception.CustomException;
import herve.learning.projetrecapitulatif.model.Car;

import java.util.Collection;

public interface CarService {

    Car save(Car car) throws CustomException;
    Collection<Car> findAll();
    Car update(Car car) throws CustomException;
}
