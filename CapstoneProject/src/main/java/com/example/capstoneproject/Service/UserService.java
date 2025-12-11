package com.example.capstoneproject.Service;

import com.example.capstoneproject.API.ApiException;
import com.example.capstoneproject.Model.User;
import com.example.capstoneproject.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAllUser(){
        return userRepository.findAll();
    }

    public void addUser(User user){
        user.setSubscription(false);
        userRepository.save(user);
    }

    public void updateuser(Integer id , User user){
        User oldUser=userRepository.findUserById(id);

        if(oldUser==null){
            throw new ApiException("User is not found");
        }
        oldUser.setName(user.getName());
        oldUser.setPassword(user.getPassword());
        oldUser.setEmail(user.getEmail());
        oldUser.setPhoneNumber(user.getPhoneNumber());
        userRepository.save(oldUser);
    }

    public void deleteUser(Integer id){
        User user=userRepository.findUserById(id);
        if(user==null){
            throw new ApiException("User is not found");
        }
        userRepository.delete(user);
    }

    public void subscribe(Integer id){
        User u = userRepository.findUserById(id);
        if(u==null){
            throw new ApiException("User is not found");
        }
        if (u.getSubscription()){
            throw new ApiException("User is already subscribed");
        }
        u.setSubscription(true);
        userRepository.save(u);
    }
}
