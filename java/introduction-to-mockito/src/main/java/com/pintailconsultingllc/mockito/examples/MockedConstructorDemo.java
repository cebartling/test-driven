package com.pintailconsultingllc.mockito.examples;

public class MockedConstructorDemo {

    private final MockedConstructorDemoDependency dependency;

    public MockedConstructorDemo() {
        // Creating our own dependency instance instead of using dependency injection.
        this.dependency = new MockedConstructorDemoDependency();
    }

    public void addItemToShoppingCart(ShoppingCartItem shoppingCartItem) {
        this.dependency.addItem(shoppingCartItem);
    }
}
