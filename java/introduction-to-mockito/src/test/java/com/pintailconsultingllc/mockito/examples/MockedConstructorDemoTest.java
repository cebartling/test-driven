package com.pintailconsultingllc.mockito.examples;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("Mockito support for mocking constructors")
class MockedConstructorDemoTest {

    @Test
    @DisplayName("demonstration")
    void DemonstrationTest() {
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
        final MockedConstruction.MockInitializer<MockedConstructorDemoDependency> mockInitializer = (mock, context) -> {
            // Stubbing the object instance that was intercepted via the constructor.
            // Stub normally like any other Mockito mock.
            doNothing().when(mock).addItem(shoppingCartItem);
        };
        try (MockedConstruction<MockedConstructorDemoDependency> mocked = mockConstruction(MockedConstructorDemoDependency.class, mockInitializer)) {
            // Make sure wherever the mocked constructor is called in within the try block. That is the only scope
            // that will do the constructor interception.
            MockedConstructorDemo sut = new MockedConstructorDemo();
            sut.addItemToShoppingCart(shoppingCartItem);

            // Use the MockedConstruction instance to get a reference to the mocked MockedConstructorDemoDependency instance.
            final MockedConstructorDemoDependency mockedConstructorDemoDependency = mocked.constructed().get(0);

            // Now verify interactions as normal with a mock object.
            verify(mockedConstructorDemoDependency).addItem(shoppingCartItem);
        }
    }

}