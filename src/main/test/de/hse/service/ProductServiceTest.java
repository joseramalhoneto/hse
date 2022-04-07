package de.hse.service;

import de.hse.exception.ProductException;
import de.hse.model.Product;
import de.hse.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ProductServiceTest {

    private Product product1, product2;

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    void setUp() {
        product1  = new Product(100L, "Laptop", "Dell VCR893", 315, "EUR");
        product2  = new Product(200L, "Mobile Phone", "Samsung", 264.1, "GBP");
    }

    @Test
    void saveProduct() {
        when(productRepository.save(product1)).thenReturn(product1);
        Product result = productService.saveProduct(product1);

        assertThat(result).isEqualTo(product1);
        verify(productRepository, times(1)).save(product1);
    }

    @Test
    void findAllProduct() {
        List<Product> list = new ArrayList<>();
        list.add(product1);
        list.add(product2);

        when(productRepository.findAll()).thenReturn(list);
        List<Product> result = productService.findAllProduct();

        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void findProductById() {
        product1  = new Product(100L, "Laptop", "Dell VCR893", 315, "EUR");
        when(productRepository.findById(product1.getProductId())).thenReturn(Optional.of(product1));
        Product contractReturned = productService.findProductById(product1.getProductId());

        assertThat(contractReturned).isEqualTo(product1);
        assertEquals(100L, contractReturned.getProductId());
        assertEquals(315, contractReturned.getPrice());
        assertEquals("Laptop", contractReturned.getName());
        verify(productRepository, times(1)).findById(contractReturned.getProductId());
    }

    @Test
    void updateProduct() {
        product1  = new Product(100L, "Laptop", "Dell VCR893", 315, "EUR");
        product1.setName("Laptop");
        product1.setDescription("Dell SDR12");

        when(productRepository.existsById(any())).thenReturn(true);
        when(productRepository.save(product1)).thenReturn(product1);

        Exception exception = assertThrows(ProductException.class, () -> productService.updateProduct(product1));
        String expectedMessage = "The product already exists. Try another number.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}