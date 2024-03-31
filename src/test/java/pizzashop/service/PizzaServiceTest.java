package pizzashop.service;

import org.junit.jupiter.api.*;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class PizzaServiceTest {

    @Test
    @Order(1)
    @Timeout(value = 2)
    void addPayment_TC1_ECP() {
        //arrange
        int tableNumber = 3;
        PaymentType type = PaymentType.CASH;
        double amount = 2012.0;

        PaymentRepository paymentRepository = new PaymentRepository();
        MenuRepository menuRepository = new MenuRepository();
        PizzaService pizzaService = new PizzaService(menuRepository, paymentRepository);

        int noOfElements = pizzaService.getPayments().size();
        //act
        pizzaService.addPayment(tableNumber, type, amount);

        //assert
        assert pizzaService.getPayments().size() == noOfElements + 1;
    }

    @Test
    @Order(2)
    void addPayment_TC1_BVA() {
        //arrange
        int tableNumber = 1;
        PaymentType type = PaymentType.CASH;
        double amount = 0.1;

        PaymentRepository paymentRepository = new PaymentRepository();
        MenuRepository menuRepository = new MenuRepository();
        PizzaService pizzaService = new PizzaService(menuRepository, paymentRepository);

        int noOfElements = pizzaService.getPayments().size();
        //act
        pizzaService.addPayment(tableNumber, type, amount);

        //assert
        assert pizzaService.getPayments().size() == noOfElements + 1;
    }

    @Test
    @Order(3)
    void addPayment_TC5_BVA() {
        //arrange
        int tableNumber = 9;
        PaymentType type = PaymentType.CARD;
        double amount = 0.2;

        PaymentRepository paymentRepository = new PaymentRepository();
        MenuRepository menuRepository = new MenuRepository();
        PizzaService pizzaService = new PizzaService(menuRepository, paymentRepository);

        int noOfElements = pizzaService.getPayments().size();
        String expectedMessage = "Invalid table number";
        //act
        Exception exception = assertThrows(Exception.class, () -> pizzaService.addPayment(tableNumber, type, amount));
        //assert
        assertEquals(exception.getMessage(), expectedMessage);
        assertEquals(noOfElements, pizzaService.getPayments().size());
    }

    @Test
    @Order(5)
    @DisplayName("Test 2 BVA")
    @Timeout(1)
    void addPayment_TC2_BVA() {
        //arrange
        PaymentRepository mockPaymentRepository = new PaymentRepository();
        MenuRepository mockMenuRepository = new MenuRepository();
        int mockTableNumber = 8;
        PaymentType mockType = PaymentType.CASH;
        double mockAmount = 0.1;
        PizzaService mockPizzaService= new PizzaService(mockMenuRepository, mockPaymentRepository);
        int initialSize = mockPizzaService.getPayments().size();

        //act
        mockPizzaService.addPayment(mockTableNumber, mockType, mockAmount);

        //assert
        assertEquals(initialSize + 1, mockPizzaService.getPayments().size());
    }

    @Test
    @Order(2)
    @DisplayName("Test 4 ECP")
    void addPayment_TC4_ECP() {
        //arrange
        PaymentRepository mockPaymentRepository = new PaymentRepository();
        MenuRepository mockMenuRepository = new MenuRepository();
        int mockTableNumber = 0;
        PaymentType mockType = PaymentType.CARD;
        double mockAmount = 222.2;
        PizzaService mockPizzaService= new PizzaService(mockMenuRepository, mockPaymentRepository);
        String expectedMessage = "Error message - Invalid table number";

        //act
        Exception exception = assertThrows(Exception.class, () ->
                mockPizzaService.addPayment(mockTableNumber, mockType, mockAmount));

        //assert
        assertEquals(exception.getMessage(), expectedMessage);
    }

    @Test
    @Disabled
    void skip_method() {}

}