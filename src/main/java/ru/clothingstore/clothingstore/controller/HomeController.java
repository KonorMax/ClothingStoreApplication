package ru.clothingstore.clothingstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.clothingstore.clothingstore.service.ItemService;
import ru.clothingstore.clothingstore.service.SearchService;
import ru.clothingstore.clothingstore.service.UserService;


@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class HomeController {
    private final UserService userService;
    private final ItemService itemService;
    private final SearchService searchService;

    @GetMapping("")
    public String getHome(Model model){
        model.addAttribute("items", itemService.getAllActiveItems());
        return "home";
    }

    @RequestMapping("sort")
    public String indexWithQuery(@RequestParam("query") String query,
                                 @RequestParam("field") String field,
                                 Model model){
        if(query.equals("")){
            switch (field){
                case "name":
                    model.addAttribute("items", searchService.sortAllByName());
                    break;
                case "category":
                    model.addAttribute("items",searchService.sortAllByCategory());
                    break;
                case "brand":
                    model.addAttribute("items", searchService.sortAllByBrand());
                    break;
                case "recent":
                    model.addAttribute("items", searchService.sortAllByIdDesc());
                    break;
                case "priceAsc":
                    model.addAttribute("items", searchService.sortAllByPriceAsc());
                    break;
                case "priceDesc":
                    model.addAttribute("items", searchService.sortAllByPriceDesc());
                    break;
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
                case "priceAsc":
                    model.addAttribute("items", searchService.getSearchedItemsOrderByPriceAsc(query));
                    break;
                case "priceDesc":
                    model.addAttribute("items", searchService.getSearchedItemsOrderByPriceDesc(query));
                    break;
                case "recent":
                    model.addAttribute("items", searchService.getSearchedItemsOrderByIdDesc(query));
            }
        }
        model.addAttribute("field", field);
        model.addAttribute("query", query);

        return "home";
    }

    @GetMapping("search")
    public String navSearch(@RequestParam("query") String query, Model model){
        model.addAttribute("items", searchService.getSearchedItems(query));
        model.addAttribute("query", query);
        return "home";
    }
}
