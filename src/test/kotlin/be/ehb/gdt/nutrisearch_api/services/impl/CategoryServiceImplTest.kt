package be.ehb.gdt.nutrisearch_api.services.impl

import be.ehb.gdt.nutrisearch_api.data.category.CategoryRepository
import be.ehb.gdt.nutrisearch_api.exceptions.CategoryNotFoundException
import be.ehb.gdt.nutrisearch_api.models.Category
import be.ehb.gdt.nutrisearch_api.models.Subcategory
import be.ehb.gdt.nutrisearch_api.services.CategoryService
import org.bson.types.ObjectId
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.fail
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class CategoryServiceImplTest {
    @Mock
    private lateinit var repo: CategoryRepository

    private lateinit var service: CategoryService

    @BeforeEach
    fun setUp() {
        service = CategoryServiceImpl(repo)
    }

    @Test
    fun `when the categories exist, then the list should be returned`() {
        `when`(repo.findAllCategories())
            .thenReturn(
                listOf(
                    Category(
                        "Groenten & Fruit",
                        mutableListOf(Subcategory("Groenten"), Subcategory("Fruit"), Subcategory("Noten"))
                    ),
                    Category("Droogwaren")
                )
            )
        val subcategories = service.getCategories()
        assertEquals(2, subcategories.size)
        verify(repo).findAllCategories()
    }

    @Test
    fun `when the category exists, it should be returned`() {
        val id = ObjectId.get().toHexString()
        `when`(repo.findCategory(id)).thenReturn(Category("Groenten & Fruit", mutableListOf(), id))

        val category = service.getCategory(id)
        assertEquals(Category("Groenten & Fruit", mutableListOf(), id), category)
        verify(repo).findCategory(id)
    }

    @Test
    fun `when category does not exist, then throw exception`() {
        val id = ObjectId.get().toHexString()
        `when`(repo.findCategory(id)).thenReturn(null)

        val e = assertThrows(CategoryNotFoundException::class.java) { service.getCategory(id) }
        assertEquals("Category with identifier $id could not be found", e.message)
    }
}