package de.hse.service;

import de.hse.exception.CategoryException;
import de.hse.model.Category;
import de.hse.repository.CategoryRepository;
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
class CategoryServiceTest {

    private Category category1, category2;

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    void setUp() {
        category1  = new Category(100L, "Home");
        category2  = new Category(200L, "Devices");
    }

    @Test
    void saveCategory() {
        when(categoryRepository.save(category1)).thenReturn(category1);
        Category result = categoryService.saveCategory(category1);

        assertThat(result).isEqualTo(category1);
        verify(categoryRepository, times(1)).save(category1);
    }

    @Test
    void findAllCategory() {
        List<Category> list = new ArrayList<>();
        list.add(category1);
        list.add(category2);

        when(categoryRepository.findAll()).thenReturn(list);
        List<Category> result = categoryService.findAllCategory();

        assertEquals(2, result.size());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void findCategoryById() {
        category1  = new Category(100L, "Home");
        when(categoryRepository.findById(category1.getCategoryId())).thenReturn(Optional.of(category1));
        Category categoryReturned = categoryService.findCategoryById(category1.getCategoryId());

        assertThat(categoryReturned).isEqualTo(category1);
        assertEquals(100L, categoryReturned.getCategoryId());
        assertEquals("Home", categoryReturned.getName());
        verify(categoryRepository, times(1)).findById(categoryReturned.getCategoryId());
    }

    @Test
    void updateCategory() {
        category1  = new Category(100L, "Home");
        category1.setName("Laptop");

        when(categoryRepository.existsById(any())).thenReturn(true);
        when(categoryRepository.save(category1)).thenReturn(category1);

        Exception exception = assertThrows(CategoryException.class, () -> categoryService.updateCategory(category1));
        String expectedMessage = "The category already exists. Try another name.";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}