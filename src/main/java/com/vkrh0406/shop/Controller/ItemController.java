package com.vkrh0406.shop.Controller;


import com.vkrh0406.shop.domain.Cart;
import com.vkrh0406.shop.dto.ItemDto;
import com.vkrh0406.shop.interceptor.SessionConst;
import com.vkrh0406.shop.service.CategoryService;
import com.vkrh0406.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("item")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("all")
    public String itemAll(Model model, @SessionAttribute(name = SessionConst.SESSION_CART, required = false) Cart cart, HttpServletRequest request,
                          @PageableDefault(size = 8) Pageable pageable, ItemSearch itemSearch){


        Page<ItemDto> itemDtos = itemService.searchAllItem(itemSearch, pageable);

        model.addAttribute("homeItemDto", itemDtos);
        model.addAttribute("category", CategoryService.category);
        model.addAttribute("cartSize", (cart == null) ? 0 : cart.getSize());


        return "item/itemAll";

    }
}
