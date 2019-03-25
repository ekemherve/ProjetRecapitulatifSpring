package herve.learning.projetrecapitulatif.controller;

import herve.learning.projetrecapitulatif.exception.CustomException;
import herve.learning.projetrecapitulatif.model.Car;
import herve.learning.projetrecapitulatif.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/api/car")
@CrossOrigin(value = "*")
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody Car car) {

        try {
            car = carService.save(car);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.ok(car);
    }

    @PutMapping
    public ResponseEntity update(@RequestBody Car car) {

        try {
            car = carService.update(car);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.ok(car);
    }


    @GetMapping()
    public ResponseEntity<Collection<Car>> findAll() {

        return ResponseEntity.ok(carService.findAll());
    }
}
