package herve.learning.projetrecapitulatif.dataaccess.util;

import herve.learning.projetrecapitulatif.dataaccess.entity.CarEntity;
import herve.learning.projetrecapitulatif.dataaccess.entity.UserEntity;
import herve.learning.projetrecapitulatif.model.Car;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static herve.learning.projetrecapitulatif.model.Constant.CAR_IS_NULL;

@Component
public class CarConverter {

    public Car toModel(CarEntity carEntity) {

        if(Objects.isNull(carEntity))
            throw new IllegalArgumentException(CAR_IS_NULL);

        Car car = new Car();

        if(Objects.nonNull(carEntity.getId()))
            car.setId(carEntity.getId());

        car.setBrand(carEntity.getBrand());
        car.setModel(carEntity.getModel());
        car.setSold(carEntity.getSold());
        car.setCreation(carEntity.getCreation());

        return car;
    }

    public CarEntity toEntity(Car car, UserEntity userEntity) {

        if(Objects.isNull(car))
            throw new IllegalArgumentException(CAR_IS_NULL);

        CarEntity carEntity = new CarEntity();

        if(Objects.nonNull(carEntity.getId()))
            car.setId(car.getId());

        carEntity.setBrand(car.getBrand());
        carEntity.setModel(car.getModel());
        carEntity.setSold(car.getSold());
        carEntity.setCreation(car.getCreation());

        if(Objects.nonNull(userEntity))
            carEntity.setUser(userEntity);

        return carEntity;
    }

}
