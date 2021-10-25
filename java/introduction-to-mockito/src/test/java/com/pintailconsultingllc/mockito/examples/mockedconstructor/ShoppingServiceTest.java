package com.pintailconsultingllc.mockito.examples.mockedconstructor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.Map;
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
            void VerifyAddItemInvocationTest() {
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
                    final ShoppingCart shoppingCartMock = mocked.constructed().get(0);

                    // Now verify interactions as normal with a mock object.
                    verify(shoppingCartMock).addItem(shoppingCartItem);
                }
            }
        }

        @Nested
        @DisplayName("when shopping cart previously exists")
        class WhenShoppingCartIsCachedTests {

            @Mock
            ShoppingCart shoppingCartMock;

            @BeforeEach
            public void doBeforeEachTest() throws Exception {
                // Using reflection, get access the shopping cart map and add an entry to it, basically pre-caching
                // the shopping cart.
                final Field shoppingCartMapField = ShoppingService.class.getDeclaredField("shoppingCartMap");
                shoppingCartMapField.setAccessible(true);
                final Map<UUID, ShoppingCart> shoppingCartMap = (Map<UUID, ShoppingCart>)
                        shoppingCartMapField.get(shoppingService);
                shoppingCartMap.put(shoppingCartKey, shoppingCartMock);
                // Now exercise the SUT.
                shoppingService.addItemToShoppingCart(shoppingCartKey, shoppingCartItem);
            }

            @Test
            @DisplayName("verify ShoppingCart.addItem is called on existing shopping cart")
            void VerifyAddItemInvocationTest() {
                // Now verify interactions as normal with a mock object.
                verify(shoppingCartMock).addItem(shoppingCartItem);
            }
        }
    }
}