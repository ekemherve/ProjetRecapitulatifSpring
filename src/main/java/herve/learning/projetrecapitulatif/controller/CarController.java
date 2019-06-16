package herve.learning.projetrecapitulatif.controller;

import herve.learning.projetrecapitulatif.exception.CustomException;
import herve.learning.projetrecapitulatif.model.Car;
import herve.learning.projetrecapitulatif.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

     @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity save(@RequestBody Car car) {

        try {
            car = carService.save(car);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        return ResponseEntity.ok(car);
    }

    @PreAuthorize("hasRole('ROLE_AMDIN')")
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

    @GetMapping(value = "/count")
    public ResponseEntity countCarsIndatabase() {
        int taille = carService.findAll().size();
        return ResponseEntity.ok(taille);
    }

    @GetMapping(value = "/countsize/{username}")
    public ResponseEntity countCars(@PathVariable String username) {
        Collection<Integer> taille ;
        try {
            taille = carService.findSoldAndUnSoldCarsSize(username);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return ResponseEntity.ok(taille);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/unsold")
    public ResponseEntity findUnSoldCars(@RequestParam int page, @RequestParam int size){

        return ResponseEntity.ok(carService.findUnSoldCar(PageRequest.of(page, size)));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(value = "/sold")
    public ResponseEntity findSoldCars(@RequestParam int page, @RequestParam int size){

        return ResponseEntity.ok(carService.findSoldCars(PageRequest.of(page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        try {
            return  ResponseEntity.ok(carService.findById(id));
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
