package com.vkrh0406.shop.Controller;

import com.vkrh0406.shop.domain.Item;
import com.vkrh0406.shop.dto.ItemDto;
import com.vkrh0406.shop.service.CategoryService;
import com.vkrh0406.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("shop")
public class ShopController {
    private final CategoryService categoryService;
    private final ItemService itemService;

    @GetMapping("home")
    public String shopHome(Model model){

        List<Item> homeItem = itemService.getHomeItem();
        List<ItemDto> homeItemDto = homeItem.stream()
                .map(o -> new ItemDto(o.getId(), o.getName(), o.getPrice(),
                        o.getDiscountPrice(), o.getRatingStar(), o.getUploadFile().getStoreFileName()))
                .collect(Collectors.toList());

        model.addAttribute("homeItemDto", homeItemDto);

        return "home";

    }
}
