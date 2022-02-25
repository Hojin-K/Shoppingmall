package com.shop.myapp.controller;

import com.shop.myapp.dto.Cart;
import com.shop.myapp.dto.Member;
import com.shop.myapp.interceptor.Auth;
import com.shop.myapp.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/cart")
@Auth(role = Auth.Role.USER)
public class CartController {

    private final CartService cartService;
    private final HttpSession session;

    public CartController(CartService cartService, HttpSession session) {
        this.cartService = cartService;
        this.session = session;
    }

    @PostMapping("/add")
    public String insertCart(@ModelAttribute Cart cartItem) {
        Member member = (Member) session.getAttribute("member");
        System.out.println(cartItem.getOptionCode());
        cartItem.setMemberId(member.getMemberId());
        cartItem.setAmount(1);
        int result = cartService.insertCart(cartItem);
        if (result == 0) {
            throw new IllegalStateException("카트 저장 실패");
        }

        return "redirect:/cart/myCart";
    }

    @GetMapping("/myCart")
    public String myCart(Model model) {
        Member member = (Member) session.getAttribute("member");
        List<Cart> carts = cartService.findCartDetailByMemberId(member.getMemberId());
        model.addAttribute("carts", carts);
        return "/cart/myCart";
    }

    @PostMapping("/{cartCode}/delete")
    @ResponseBody
    public ResponseEntity<Object> deleteCart(@PathVariable String cartCode) {
        Member member = (Member) session.getAttribute("member");
        Optional<Cart> cartOptional = cartService.findByCartId(cartCode);
        Cart cart = cartOptional.orElseThrow(() -> new IllegalStateException("cart 정보 없음"));
        if (cart.getMemberId().equals(member.getMemberId())) {
            int result = cartService.deleteByCartId(cartCode);
            if (result > 0) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(402).build();
            }
        }
        throw new IllegalStateException("사용자 정보 불일치");
    }

    @PostMapping("/{cartCode}/setAmount")
    @ResponseBody
    public ResponseEntity<Object> setCartAmount(@PathVariable String cartCode, @RequestParam String mathSign) {
        Member member = (Member) session.getAttribute("member");
        Optional<Cart> cartOptional = cartService.findByCartId(cartCode);
        Cart cart = cartOptional.orElseThrow(() -> new IllegalStateException("cart 정보 없음"));
        // 갯수가 0일경우, 처리 로직 추가 필요.
        if (cart.getMemberId().equals(member.getMemberId())) {
            int result = cartService.amountSetByCartId(cartCode, mathSign);
            if (result == 0) {
                return ResponseEntity.status(402).build();
            }
            return ResponseEntity.ok().build();
        }
        throw new IllegalStateException("사용자 정보 불일치");
    }
}
