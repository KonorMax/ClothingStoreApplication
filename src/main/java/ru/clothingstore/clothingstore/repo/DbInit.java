package ru.clothingstore.clothingstore.repo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.clothingstore.clothingstore.model.Item;
import ru.clothingstore.clothingstore.model.User;


@Service
public class DbInit implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;

    public DbInit(PasswordEncoder passwordEncoder, UserRepository userRepository, ItemRepository itemRepository, ShoppingCartRepository shoppingCartRepository, CartItemRepository cartItemRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemRepository = cartItemRepository;
    }




    @Override
    public void run(String... args) throws Exception {
        User admin = new User("admin", passwordEncoder.encode("admin"), "ADMIN, USER", "admin@topjava.com");
        User user = new User("testuser", passwordEncoder.encode("testuser"), "USER", "user@topjava.com");

        Item item = new Item("pants Adidas", 5000.0f, "cat: sportwear, subc: pants", "pants", "Adidas");
        Item item2 = new Item("pants Reebok", 4000.0f, "cat: sportwear, subc: pants", "pants", "Reebok");
        Item item3 = new Item("pants Puma", 3000.0f, "cat: sportwear, subc: pants", "pants", "Puma");
        Item item4 = new Item("pants Nike", 5000.0f, "cat: sportwear, subc: pants", "pants", "Nike");
        Item item5 = new Item("pants Spartak", 5000.0f, "cat: sportwear, subc: pants", "pants", "Spartak");
        Item item6 = new Item("sneakers Adidas", 5000.0f, "cat: shoes, subc: sneakers", "sneakers", "Adidas");
        Item item7 = new Item("sneakers Reebok", 5000.0f, "cat: shoes, subc: sneakers", "sneakers", "Reebok");
        Item item8 = new Item("sneakers Puma", 5000.0f, "cat: shoes, subc: sneakers", "sneakers", "Puma");
        Item item9 = new Item("sneakers Nike", 5000.0f, "cat: shoes, subc: sneakers", "sneakers", "Nike");
        Item item10 = new Item("sneakers Spartak", 5000.0f, "cat: shoes, subc: sneakers", "sneakers", "Spartak");

        userRepository.save(admin);
        userRepository.save(user);
        itemRepository.save(item);
        itemRepository.save(item2);
        itemRepository.save(item3);
        itemRepository.save(item4);
        itemRepository.save(item5);
        itemRepository.save(item6);
        itemRepository.save(item7);
        itemRepository.save(item8);
        itemRepository.save(item9);
        itemRepository.save(item10);

    }
}
