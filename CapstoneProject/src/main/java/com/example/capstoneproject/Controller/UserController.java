package com.example.capstoneproject.Controller;

import com.example.capstoneproject.API.ApiResponse;
import com.example.capstoneproject.Model.User;
import com.example.capstoneproject.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/get")
    public ResponseEntity<?> getUsers(){
        return ResponseEntity.status(200).body(userService.findAllUser());
    }

    @PostMapping("add")
    public ResponseEntity<?> addUser(@RequestBody User user){
        userService.addUser(user);
        return ResponseEntity.status(200).body(new ApiResponse("the User is added"));
    }


    @PutMapping("update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id,@RequestBody User user){
        userService.updateuser(id,user);
        return ResponseEntity.status(200).body(new ApiResponse("the User is updated"));
    }

    @DeleteMapping ("delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        userService.deleteUser(id);
        return ResponseEntity.status(200).body(new ApiResponse("the User is deleted"));
    }

    @PutMapping("/subscribe/{id}")
    public ResponseEntity<?> subscribeUser(@PathVariable Integer id){
        userService.subscribe(id);
        return ResponseEntity.status(200).body(new ApiResponse("the User is subscribed"));
    }


}
