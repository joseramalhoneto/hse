package de.hse.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.hse.model.Category;
import de.hse.model.Product;
import de.hse.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    private Product product1, product2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Before
    public void setUp() {
        product1  = new Product(100L, "Laptop", "Dell VCR893", 315, "EUR");
        product2  = new Product(200L, "Mobile Phone", "Samsung", 264.1, "GBP");
    }

    @Test
    public void saveCategory() throws Exception {
        when(productService.saveProduct(any()))
                .thenReturn(product1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/product/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(product1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value(100L))
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    public void findAllCategory() throws Exception {
        when(productService
                .findAllProduct())
                .thenReturn(List.of(product1, product2));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/product/find/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].productId").value(100L))
                .andExpect(jsonPath("$[1].productId").value(200L));
    }

    @Test
    public void findCategoryById() throws Exception {
        when(productService
                .findProductById(100L))
                .thenReturn(product1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/product/find/{id}", 100L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(100L))
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    public void updateCategory() throws Exception {
        when(productService.updateProduct(any()))
                .thenReturn(product1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/product/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(product1))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId").value(100L))
                .andExpect(jsonPath("$.name").value("Laptop"));
    }

    @Test
    public void deleteCategoryById() throws Exception {
        when(productService.saveProduct(any()))
                .thenReturn(product1);

        this.mockMvc.perform(delete("/product/delete/{id}", 100L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}