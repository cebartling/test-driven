package com.pintailconsultingllc.mockito.examples.mockedconstructor;

import com.pintailconsultingllc.mockito.examples.mockedconstructor.ShoppingCart;
import com.pintailconsultingllc.mockito.examples.mockedconstructor.ShoppingCartItem;
import com.pintailconsultingllc.mockito.examples.mockedconstructor.ShoppingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;

/**
 * This test suite demonstrates using Mockito's MockedConstruction and mockConstruction facilities for intercepting
 * constructor invocations and supplying mock instances of these classes. Super helpful when you can't use
 * dependency injection by want to provide a mock object and don't have a "seam" to provide it within the
 * system under test.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Mockito support for mocking constructors")
class ShoppingServiceTest {

    @InjectMocks
    ShoppingService shoppingService;

    @Nested
    @DisplayName("addItemToShoppingCart")
    class AddItemToShoppingCartTests {
        final UUID shoppingCartKey = UUID.randomUUID();
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();

        @Nested
        @DisplayName("when shopping cart does not previously exist")
        class WhenShoppingCartIsNotCachedTests {

            @Test
            @DisplayName("verify ShoppingCart.addItem is called on new shopping cart")
            void DemonstrationTest() {
                final MockedConstruction.MockInitializer<ShoppingCart> mockInitializer = (mock, context) -> {
                    // Stubbing the object instance that was intercepted via the constructor.
                    // Stub normally like any other Mockito mock.
                    doNothing().when(mock).addItem(shoppingCartItem);
                };
                try (MockedConstruction<ShoppingCart> mocked = mockConstruction(ShoppingCart.class, mockInitializer)) {
                    // Make sure wherever the mocked constructor is called is within the try block.
                    // That is the only scope that will be included in the constructor interception.
                    // The next line exercises the SUT, the ShoppingService.
                    shoppingService.addItemToShoppingCart(shoppingCartKey, shoppingCartItem);

                    // Use the MockedConstruction instance to get a reference to the mocked ShoppingCart instance.
                    final ShoppingCart mockedShoppingCart = mocked.constructed().get(0);

                    // Now verify interactions as normal with a mock object.
                    verify(mockedShoppingCart).addItem(shoppingCartItem);
                }
            }
        }
    }
}