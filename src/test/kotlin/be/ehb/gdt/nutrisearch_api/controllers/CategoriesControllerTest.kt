package be.ehb.gdt.nutrisearch_api.controllers

import be.ehb.gdt.nutrisearch_api.exceptions.CategoryNotFoundException
import be.ehb.gdt.nutrisearch_api.models.Category
import be.ehb.gdt.nutrisearch_api.models.Subcategory
import be.ehb.gdt.nutrisearch_api.services.CategoryService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.ResourceUtils

@WebMvcTest
@ContextConfiguration(classes = [CategoriesController::class])
@AutoConfigureMockMvc(addFilters = false)
class CategoriesControllerTest {
    private val id = "cat2"
    private val subcategoryId = "cat2sub2"
    private val name = "Graanproducten"
    private val subcategoryName = "Brood"
    private val subcategory = Subcategory(subcategoryName, subcategoryId)
    private val subcategories = mutableListOf(Subcategory("Cornflakes", "cat2sub1"), subcategory)
    private val category = Category(name, subcategories, id)

    @MockBean
    lateinit var categoryService: CategoryService

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `when GET all categories request is sent then status 200 is expected`() {
        val categories = listOf(
            Category(
                "Vleesproducten",
                mutableListOf(Subcategory("Vers Vlees", "cat1sub1")),
                "cat1"
            ),
            category
        )

        `when`(categoryService.getCategories()).thenReturn(categories)

        mockMvc.perform(get("/api/v1/categories"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(readJsonFromFile("categories/categories.json")))

        verify(categoryService).getCategories()
    }

    @Test
    fun `when GET request is sent then status 200 is expected`() {
        `when`(categoryService.getCategory("cat2")).thenReturn(category)

        mockMvc.perform(get("/api/v1/categories/{id}", id))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(readJsonFromFile("categories/category.json")))

        verify(categoryService).getCategory(id)
    }

    @Test
    fun `when GET request is sent and category does not exist then status 404 is expected`() {
        `when`(categoryService.getCategory(id)).thenThrow(CategoryNotFoundException::class.java)

        mockMvc.perform(get("/api/v1/categories/{id}", id))
            .andDo(print())
            .andExpect(status().isNotFound())

        verify(categoryService).getCategory(id)
    }

    @Test
    fun `when POST request is sent then status 200 is expected`() {
        val json = "{ 'id': 'id', 'name': '$name' }"
        `when`(categoryService.createCategory(name)).thenReturn(Category(name, id = "id"))

        mockMvc.perform(post("/api/v1/categories").queryParam("name", name))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(content().json(json))

        verify(categoryService).createCategory(name)
    }


    @Test
    fun `when POST request is invalid then status 400 is expected`() {
        mockMvc.perform(post("/api/v1/categories"))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect { assertEquals("Required parameter 'name' is not present.", it.response.errorMessage) }

        verifyNoInteractions(categoryService)
    }

    @Test
    fun `when category exists and valid PUT request is sent then status 204 expected`() {
        mockMvc.perform(put("/api/v1/categories/{id}", id).queryParam("name", name))
            .andDo(print())
            .andExpect(status().isNoContent())

        verify(categoryService).updateCategory(id, name)
    }

    @Test
    fun `when category does not exists and PUT request is sent then status 404 is expected`() {
        `when`(categoryService.updateCategory(id, name)).thenThrow(CategoryNotFoundException::class.java)

        mockMvc.perform(put("/api/v1/categories/{id}", id).queryParam("name", name))
            .andDo(print())
            .andExpect(status().isNotFound())

        verify(categoryService).updateCategory(id, name)
    }

    @Test
    fun `when category exists and invalid PUT request is sent then status 400 is expected`() {
        mockMvc.perform(put("/api/v1/categories/{id}", id))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect { assertEquals("Required parameter 'name' is not present.", it.response.errorMessage) }

        verifyNoInteractions(categoryService)
    }

    @Test
    fun `when category does exists and DELETE request is sent then status 204 is expected`() {
        mockMvc.perform(delete("/api/v1/categories/{id}", id))
            .andDo(print())
            .andExpect(status().isNoContent())

        verify(categoryService).deleteCategory(id)
    }

    @Test
    fun `when category does not exists and DELETE request is sent then status 404 is expected`() {
        `when`(categoryService.deleteCategory(id)).thenThrow(CategoryNotFoundException::class.java)

        mockMvc.perform(delete("/api/v1/categories/{id}", id))
            .andDo(print())
            .andExpect(status().isNotFound())

        verify(categoryService).deleteCategory(id)
    }

    @Test
    fun `when category exists and has subcategories and GET request is sent then expect status 200`() {
        `when`(categoryService.getSubcategories(id)).thenReturn(subcategories)

        mockMvc.perform(get("/api/v1/categories/{id}/subcategories", id))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(readJsonFromFile("categories/subcategories.json")))

        verify(categoryService).getSubcategories(id)
    }

    @Test
    fun `when category does not exist and GET request is sent then status 404 is expected`() {
        `when`(categoryService.getSubcategories(id)).thenThrow(CategoryNotFoundException::class.java)

        mockMvc.perform(get("/api/v1/categories/{id}/subcategories", id))
            .andDo(print())
            .andExpect(status().isNotFound())

        verify(categoryService).getSubcategories(id)
    }

    @Test
    fun `when category exists and has subcategories and GET request is sent then status 200 is expected`() {
        val json = "{'id': $subcategoryId, 'name': 'Brood'}"

        `when`(categoryService.getSubcategory(id, subcategoryId)).thenReturn(subcategory)

        mockMvc.perform(get("/api/v1/categories/{id}/subcategories/{subId}", id, subcategoryId))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(content().json(json))

        verify(categoryService).getSubcategory(id, subcategoryId)
    }

    @Test
    fun `when category exists and subcategory does not exists and GET request is sent then status 404 is expected`() {
        `when`(categoryService.getSubcategory(id, subcategoryId)).thenThrow(CategoryNotFoundException::class.java)

        mockMvc.perform(get("/api/v1/categories/{id}/subcategories/{subId}", id, subcategoryId))
            .andDo(print())
            .andExpect(status().isNotFound())

        verify(categoryService).getSubcategory(id, subcategoryId)
    }

    @Test
    fun `when category exists and POST request is sent then status 204 is expected`() {
        mockMvc.perform(post("/api/v1/categories/{id}/subcategories", id).queryParam("name", subcategoryName))
            .andDo(print())
            .andExpect(status().isNoContent())

        verify(categoryService).createSubcategory(id, subcategoryName)
    }

    @Test
    fun `when category does not exists and POST request is sent then status 404 is expected`() {
        `when`(categoryService.createSubcategory(id, subcategoryName)).thenThrow(CategoryNotFoundException::class.java)

        mockMvc.perform(post("/api/v1/categories/{id}/subcategories", id).queryParam("name", subcategoryName))
            .andDo(print())
            .andExpect(status().isNotFound())

        verify(categoryService).createSubcategory(id, subcategoryName)
    }

    @Test
    fun `when category and subcategory exists and PUT request is sent then status 204 is expected`() {
        mockMvc.perform(
            put("/api/v1/categories/{id}/subcategories/{subId}", id, subcategoryId)
                .queryParam("name", subcategoryName)
        )
            .andDo(print())
            .andExpect(status().isNoContent())

        verify(categoryService).updateSubcategory(id, subcategoryId, subcategoryName)
    }

    @Test
    fun `when category or subcategory does not exists and PUT request is sent then status 404 is expected`() {
        `when`(categoryService.updateSubcategory(id, subcategoryId, subcategoryName)).thenThrow(
            CategoryNotFoundException::class.java
        )

        mockMvc.perform(
            put("/api/v1/categories/{id}/subcategories/{subId}", id, subcategoryId).queryParam(
                "name", subcategoryName
            )
        )
            .andDo(print())
            .andExpect(status().isNotFound())

        verify(categoryService).updateSubcategory(id, subcategoryId, subcategoryName)
    }

    @Test
    fun `when category and subcategory exists and DELETE request is sent then status 204 is expected`() {
        mockMvc.perform(delete("/api/v1/categories/{id}/subcategories/{subId}", id, subcategoryId))
            .andDo(print())
            .andExpect(status().isNoContent())

        verify(categoryService).deleteSubcategory(id, subcategoryId)
    }

    @Test
    fun `when category or subcategory does not exist and DELETE request is sent then status 404 is expected`() {
        `when`(categoryService.deleteSubcategory(id, subcategoryId)).thenThrow(CategoryNotFoundException::class.java)

        mockMvc.perform(delete("/api/v1/categories/{id}/subcategories/{subId}", id, subcategoryId))
            .andDo(print())
            .andExpect(status().isNotFound())

        verify(categoryService).deleteSubcategory(id, subcategoryId)
    }

    private fun readJsonFromFile(fileName: String): String {
        return String(ResourceUtils.getFile("classpath:$fileName").readBytes())
    }
}