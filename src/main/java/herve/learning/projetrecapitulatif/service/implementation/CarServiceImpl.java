package herve.learning.projetrecapitulatif.service.implementation;

import herve.learning.projetrecapitulatif.dataaccess.dao.CarDAO;
import herve.learning.projetrecapitulatif.dataaccess.dao.UserDAO;
import herve.learning.projetrecapitulatif.dataaccess.entity.CarEntity;
import herve.learning.projetrecapitulatif.dataaccess.entity.UserEntity;
import herve.learning.projetrecapitulatif.dataaccess.util.CarConverter;
import herve.learning.projetrecapitulatif.exception.CustomException;
import herve.learning.projetrecapitulatif.model.Car;
import herve.learning.projetrecapitulatif.securite.authentication.AuthenticationFacadeService;
import herve.learning.projetrecapitulatif.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static herve.learning.projetrecapitulatif.model.Constant.*;

@Service
public class CarServiceImpl implements CarService {

    private final CarDAO carDAO;

    private final UserDAO userDAO;

    private final CarConverter carConverter;

    private final AuthenticationFacadeService authenticationFacadeService;

    @Autowired
    public CarServiceImpl(CarDAO carDAO, UserDAO userDAO, CarConverter carConverter, AuthenticationFacadeService authenticationFacadeService) {
        this.carDAO = carDAO;
        this.userDAO = userDAO;
        this.carConverter = carConverter;
        this.authenticationFacadeService = authenticationFacadeService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public Car save(Car car) throws CustomException {

        if(Objects.isNull(car))
            throw new IllegalArgumentException(CAR_IS_NULL);

        String username = authenticationFacadeService.getAuthenticated();

        if(Objects.isNull(username))
            throw new CustomException(USER_DOESNT_HAVE_PERMISSION);

        if(car.getSold() == null)
            car.setSold(false);
        CarEntity carEntity = carConverter.toEntity(car);
        carEntity = carDAO.save(carEntity);
        return  carConverter.toModel(carEntity);
    }

    @Override
    public Car update(Car car) throws CustomException {

        if(Objects.isNull(car))
            throw new IllegalArgumentException(CAR_IS_NULL);

        CarEntity carEntity = carDAO.findById(car.getId()).orElse(null);

        if(Objects.isNull(carEntity))
            throw new CustomException(CAR_DO_NOT_EXISTS);

        if(Objects.isNull(authenticationFacadeService.getAuthenticated()))
            throw new CustomException(USER_DOESNT_EXIST);

        if(car.getSold() == null)
            car.setSold(false);

        carEntity = carConverter.toEntity(car);
        carEntity = carDAO.save(carEntity);
        return carConverter.toModel(carEntity);
    }

    @Override
    public Collection<Car> findUnSoldCar(Pageable pageable) {

        UserEntity userEntity = userDAO.findByUsername(authenticationFacadeService.getAuthenticated());

        if(Objects.isNull(userEntity))
            throw new IllegalArgumentException(USER_DOESNT_EXIST);

        Page<CarEntity> page = carDAO.findUnSoldCars(userEntity, pageable);

        Collection<CarEntity> cars = page.getContent();

        return cars.stream().map(carConverter::toModel).collect(Collectors.toSet());
    }

    @Override
    public Collection<Integer> findSoldAndUnSoldCarsSize(String username) throws CustomException {

        if(!Objects.equals(authenticationFacadeService.getAuthenticated(), username))
            throw new CustomException(USER_NOT_AUTHENTICATED);

        UserEntity userEntity = userDAO.findByUsername(username);

        Collection<CarEntity> cars = carDAO.findByUser(userEntity);

        int nbSold = 0, nbUnSold = 0;
        for(CarEntity car : cars){

            if(car.getSold())
                ++nbSold;
            else ++nbUnSold;
        }
        Collection<Integer> size = new ArrayList<>();
        size.add(nbSold);
        size.add(nbUnSold);
        return size;
    }

    @Override
    public Collection<Car> findSoldCars(Pageable pageable) {

        UserEntity userEntity = userDAO.findByUsername(authenticationFacadeService.getAuthenticated());

        if(Objects.isNull(userEntity))
            throw new IllegalArgumentException(USER_DOESNT_EXIST);

        Page<CarEntity> page = carDAO.findSoldCars(userEntity, pageable);

        Collection<CarEntity> cars = page.getContent();

        return cars.stream().map(carConverter::toModel).collect(Collectors.toSet());
    }

    @Override
    public Car findById(Long id) throws CustomException {

        Optional<CarEntity> optional = carDAO.findById(id);
        if(!optional.isPresent())
            throw new CustomException(CAR_DO_NOT_EXISTS);
        CarEntity carEntity = optional.get();
        return carConverter.toModel(carEntity);
    }

    @Override
    public Collection<Car> findAll() {
        return carDAO.findAll().stream()
                .map(carConverter::toModel)
                .collect(Collectors.toSet());
    }

}
