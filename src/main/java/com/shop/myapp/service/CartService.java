package com.shop.myapp.service;

import com.shop.myapp.dto.Cart;
import com.shop.myapp.repository.CartRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;

    public CartService(@Autowired SqlSession sqlSession) {
        this.cartRepository = sqlSession.getMapper(CartRepository.class);
    }

    public int insertCart(Cart cart) {
        return cartRepository.insertCart(cart);
    }

    public List<Cart> findCartDetailByMemberId(String memberId){
        return cartRepository.findByMemberId(memberId);
    }

    public int deleteByMemberId(String memberId){
        return cartRepository.deleteCartByMemberId(memberId);

    }

    public int deleteByCartId(String cartId){
        return cartRepository.deleteCartByCartId(cartId);
    }

    public List<Cart> findSelectCartByCartIds(List<String> cartCods){
        return cartRepository.findSelectCartByCartCodes(cartCods);
    }
    public Optional<Cart> findByCartId(String cartId){
        return cartRepository.findByCartId(cartId);

    }
    public int amountSetByCartId(String cartId,String mathSign){
        try{
        return cartRepository.amountSetByCartId(cartId,mathSign);
        } catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

}
