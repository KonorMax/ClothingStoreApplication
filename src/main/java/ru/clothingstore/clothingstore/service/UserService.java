package ru.clothingstore.clothingstore.service;



import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.clothingstore.clothingstore.model.ShoppingCart;
import ru.clothingstore.clothingstore.model.User;
import ru.clothingstore.clothingstore.repo.UserRepository;


import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public User getUser(String username){
        return userRepository.findByUsername(username);
    }


    public User getUser(Long id){
        return userRepository.findUserById(id);
    }


    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public List<User> getUserWithAdvancedSearch(String search){
        return userRepository.findAllByUsernameContainingOrEmailContaining(search, search);
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public String registerUser(User user){
        User checkedUsernameByUsername = userRepository.findUserByUsername(user.getUsername());
        User checkedUsernameByEmail = userRepository.findByEmail(user.getEmail());


        if(checkedUsernameByUsername != null && checkedUsernameByEmail != null){
            return "notUniqueUsernameAndEmail";
        }

        if(checkedUsernameByUsername != null){
            return "notUniqueUsername";
        }

        if(checkedUsernameByEmail != null){
            return "notUniqueEmail";
        }

        user.setEnable(true);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        user.setRoles("USER");
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        user.setShoppingCart(shoppingCart);

        user.setEnable(true);

        userRepository.save(user);

        return "success";

    }


}
