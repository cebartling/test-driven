package com.pintailconsultingllc.mockito.examples.mockedconstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ShoppingService {

    private final Map<UUID, ShoppingCart> shoppingCartMap = new HashMap<>();

    public void addItemToShoppingCart(UUID shoppingCartKey, ShoppingCartItem shoppingCartItem) {
        ShoppingCart shoppingCart;
        if (!this.shoppingCartMap.containsKey(shoppingCartKey)) {
            shoppingCart = new ShoppingCart();
            shoppingCartMap.put(shoppingCartKey, shoppingCart);
        } else {
            shoppingCart = shoppingCartMap.get(shoppingCartKey);
        }
        shoppingCart.addItem(shoppingCartItem);
    }
}
