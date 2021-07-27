package com.vkrh0406.shop.Controller;

import com.vkrh0406.shop.FileStore;
import com.vkrh0406.shop.domain.Cart;
import com.vkrh0406.shop.domain.Item;
import com.vkrh0406.shop.domain.Member;
import com.vkrh0406.shop.dto.ItemDto;
import com.vkrh0406.shop.interceptor.SessionConst;
import com.vkrh0406.shop.resolver.Login;
import com.vkrh0406.shop.resolver.SessionCart;
import com.vkrh0406.shop.service.CategoryService;
import com.vkrh0406.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("")
public class HomeController {
    private final CategoryService categoryService;
    private final ItemService itemService;
    private final FileStore fileStore;


    @GetMapping("")
    public String shopHome(Model model, @SessionCart Cart cart, @Login Member member, HttpServletRequest request) {

        return "redirect:/item/all";


//        if (member != null) {
//            model.addAttribute("username", member.getUsername());
//        }
//
//
//        List<Item> homeItem = itemService.getHomeItem();
//        List<ItemDto> homeItemDto = homeItem.stream()
//                .map(o -> new ItemDto(o.getId(), o.getName(), o.getPrice(),
//                        o.getDiscountPrice(), o.getRatingStar(), o.getUploadFile().getStoreFileName()))
//                .collect(Collectors.toList());
//
//
//        model.addAttribute("homeItemDto", homeItemDto);
//        model.addAttribute("category", CategoryService.category);
//        model.addAttribute("cartSize", (cart == null) ? 0 : cart.getSize());



    }

//    @GetMapping("/")
//    public String home() {
//        return "redirect:/shop";
//    }

}
