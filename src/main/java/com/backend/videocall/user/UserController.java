package com.backend.videocall.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/api/v1/users")
//@RequiredArgsConstructor
@CrossOrigin(origins="*")
@Slf4j
public class UserController {
    @Autowired
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public void register(@RequestBody User user){
        service.register(user);
    }

    @PostMapping("/login")
    public  User login(@RequestBody User user){
        return service.login((user));
    }

    @PostMapping("/logout")
    public  void logout(String email){
        service.logout(email);
         }


     @GetMapping("/getAll")
    public List<User> findAll() {
        return  service.findAll();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handle(Exception e){
        e.printStackTrace();
        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

}
