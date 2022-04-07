package de.hse.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.hse.model.Category;
import de.hse.service.CategoryService;
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
@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    private Category category1, category2;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Before
    public void setUp() {
        category1  = new Category(100L, "Home");
        category2  = new Category(200L, "Devices");
    }

    @Test
    public void saveCategory() throws Exception {
        when(categoryService.saveCategory(any()))
                .thenReturn(category1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/category/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(category1))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.categoryId").value(100L))
                .andExpect(jsonPath("$.name").value("Home"));
    }

    @Test
    public void findAllCategory() throws Exception {
        when(categoryService
                .findAllCategory())
                .thenReturn(List.of(category1, category2));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/category/find/all"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].categoryId").value(100L))
                .andExpect(jsonPath("$[1].categoryId").value(200L));
    }

    @Test
    public void findCategoryById() throws Exception {
        when(categoryService
                .findCategoryById(100L))
                .thenReturn(category1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/category/find/{id}", 100L))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId").value(100L))
                .andExpect(jsonPath("$.name").value("Home"));
    }

    @Test
    public void updateCategory() throws Exception {
        when(categoryService.updateCategory(any()))
                .thenReturn(category1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/category/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(new ObjectMapper().writeValueAsString(category1))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId").value(100L))
                .andExpect(jsonPath("$.name").value("Home"));
    }

    @Test
    public void deleteCategoryById() throws Exception {
        when(categoryService.saveCategory(any()))
                .thenReturn(category1);

        this.mockMvc.perform(delete("/category/delete/{id}", 100L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}