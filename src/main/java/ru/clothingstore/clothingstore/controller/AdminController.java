package ru.clothingstore.clothingstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.clothingstore.clothingstore.model.Item;
import ru.clothingstore.clothingstore.model.ItemImage;
import ru.clothingstore.clothingstore.model.User;
import ru.clothingstore.clothingstore.service.*;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

@Controller
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
    private final ItemService itemService;
    private final ItemImageService itemImageService;
    private final SearchService searchService;
    private final UserService userService;
    private final ShoppingCartService shoppingCartService;

    @GetMapping("adminhome")
    public String adminHome(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        return "/admin/adminhome";
    }

    //?file=...&id=111
    @Transactional
    @PostMapping("upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   @RequestParam("id") Long id,
                                   RedirectAttributes redirectAttributes) {

        Item currentItem = itemService.getItemById(id);

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message",
                    "Data is empty! Please upload your image!");
            return "redirect:../admin/adminView/" + id;
        }

        String UPLOADED_FOLDER = "static/images/itemImages";

        Path destPath = Paths.get(UPLOADED_FOLDER, file.getOriginalFilename());

        try {
            file.transferTo(destPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        String originalPathStr = "/images/itemImages" + destPath.getFileName().toString();

        byte[] encodedPath = Base64.getEncoder().encode(originalPathStr.getBytes());

        ItemImage image = new ItemImage(currentItem, encodedPath);
        redirectAttributes.addFlashAttribute("message",
                "You  successfully loaded your image " +
                        file.getOriginalFilename() +
                        " for " + currentItem.getName());
        itemImageService.saveItemImage(image);
        currentItem.getImages().add(image);
        itemService.saveItem(currentItem);
        return "redirect:../admin/adminView/" + id;
    }

    @Transactional
    @RequestMapping("deleteImage")
    public String deleteImage(@RequestParam("imagePath") String imagePath, @RequestParam("itemId") Long itemId) {
        Item item = itemService.getItemById(itemId);

        for (ItemImage itemImage : item.getImages()) {
            if (itemImage.getDecodedImgPath().equals(imagePath)) {
                itemImageService.deleteItemImage(itemImage.getId());
                item.getImages().remove(itemImage);
                break;
            }
        }

        itemService.saveItem(item);
        return "redirect:../admin/adminView" + itemId;

    }


    @GetMapping("adminView/{id}")
    public String adminView(@PathVariable("id") Long id, Model model) {
        model.addAttribute("item", itemService.getItemById(id));
        return "admin/adminView";
    }


    @GetMapping("activation/{id}")
    public String activeItem(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Item currentItem = itemService.getItemById(id);
        if (currentItem.isActive()) {
            currentItem.setActive(false);
            redirectAttributes.addFlashAttribute("activation",
                    currentItem.getName() + " is switched to an inactive status");
        } else {
            currentItem.setActive(true);
            redirectAttributes.addFlashAttribute("activation",
                    currentItem.getName() + " is switched to an active status");
        }
        itemService.saveItem(currentItem);
        return "redirect:.../adminhome";
    }

    //.../sort?query=null&field=
    @RequestMapping("sort")
    public String getAdminSortedItems(@RequestParam("query") String query,
                                      @RequestParam("field") String field,
                                      Model model) {
        if(query.equals(null)){
            switch (field){
                case "name":
                    model.addAttribute("items", searchService.sortAllByName());
                    break;
                case "category":
                    model.addAttribute("items", searchService.sortAllByCategory());
                    break;
                case "brand":
                    model.addAttribute("items", searchService.sortAllByBrand());
                    break;
                case "recent":
                    model.addAttribute("items", searchService.sortAllById());
                    break;
                case "priceAsc":
                    model.addAttribute("items", searchService.sortAllByPriceAsc());
                    break;
                case "priceDesc":
                    model.addAttribute("items", searchService.sortAllByPriceDesc());
            }
        } else {
            switch (field){
                case "name":
                    model.addAttribute("items", searchService.getSearchedItemsOrderByName(query));
                    break;
                case "category":
                    model.addAttribute("items", searchService.getSearchedItemsOrderByCategory(query));
                    break;
                case "brand":
                    model.addAttribute("items", searchService.getSearchedItemsOrderByBrand(query));
                    break;
                case "recent":
                    model.addAttribute("items", searchService.getSearchedItemsOrderByIdDesc(query));
                    break;
                case "priceAsc":
                    model.addAttribute("items", searchService.getSearchedItemsOrderByPriceAsc(query));
                    break;
                case "priceDesc":
                    model.addAttribute("items", searchService.getSearchedItemsOrderByPriceDesc(query));
            }
        }
        model.addAttribute("field", field);
        model.addAttribute("query", query);

        return "admin/adminhome";

    }

    @GetMapping("searchItems")
    public String navSearch(@RequestParam("query") String query, Model model){
        model.addAttribute("items", searchService.getSearchedItems(query));
        model.addAttribute("query", query);
        return "admin/adminhome";
    }

    @GetMapping("searchUsers")
    public String searchUsers(@RequestParam("userSearch")String userSearch, Model model){
        model.addAttribute("users", userService.getUserWithAdvancedSearch(userSearch));
        model.addAttribute("userSearch", userSearch);
        return "admin/adminhome";
    }

    @GetMapping("users")
    public String getAllUsers(Model model){
        model.addAttribute("users", userService.getAllUsers());
        return "admin/adminhome";
    }

    @GetMapping("adminUser/{id}")
    public String seeUserDetails(@PathVariable("id") Long id, Model model){
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        model.addAttribute("orders",
                shoppingCartService.getCompletedShoppingCarts(user.getUsername(), true));
        return "admin/adminView";
    }

    @GetMapping("enable/{id}")
    public String enable(@PathVariable("id")Long id, Model model){
        User user = userService.getUser(id);
        if (user.isEnable()){
            user.setEnable(false);
            model.addAttribute("activation", user.getUsername() + "'s account is disable");
        } else {
            user.setEnable(true);
            model.addAttribute("activation", user.getUsername() + "'s account is enable");
        }

        model.addAttribute("users", userService.getAllUsers());
        userService.saveUser(user);
        return "admin/adminhome";
    }

    @GetMapping("newItem")
    public String newItem(Model model){
        model.addAttribute("item", new Item());
        return "admin/newItem";

    }

    @PostMapping(value = "newItem")
    public String saveNewItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes){
        itemService.saveItem(item);
        redirectAttributes.addFlashAttribute("activation", item.getName() + " added succesfully");
        return "redirect:../adminhome";
    }




}

