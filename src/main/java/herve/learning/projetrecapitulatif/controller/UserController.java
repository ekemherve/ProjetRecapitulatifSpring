package herve.learning.projetrecapitulatif.controller;

import herve.learning.projetrecapitulatif.exception.CustomException;
import herve.learning.projetrecapitulatif.model.User;
import herve.learning.projetrecapitulatif.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/user")
@CrossOrigin(origins = "*")
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity save(@RequestBody User user) {

        try {
            user = userService.save(user);
        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }

        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<Collection<User>> findAll() {

        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){

        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    @GetMapping("/allpages")
    public ResponseEntity<Collection<User>> getClientPage(@RequestParam("page") int page , @RequestParam("size")int size){

        Pageable pageable = PageRequest.of(page, size);
        Collection<User> pageUser = userService.findAllWithPagination(pageable);
        return new ResponseEntity<>(pageUser, HttpStatus.OK);
    }

    @GetMapping(value = "/size")
    public ResponseEntity<Integer> getNumberOfUserInDatabase() {

        int taille = userService.findAll().size();
        return new ResponseEntity<>(taille, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody User user){

        this.userService.delete(user);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity update(@RequestBody User user){


        try {
            user = this.userService.update(user);
        } catch (CustomException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        return ResponseEntity.ok(user);
    }

}
