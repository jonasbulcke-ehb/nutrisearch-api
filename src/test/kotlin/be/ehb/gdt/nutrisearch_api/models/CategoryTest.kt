package be.ehb.gdt.nutrisearch_api.models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestFactory

class CategoryTest {
    private val category = Category(
        "Groenten & Fruit",
        mutableListOf(
            Subcategory("Groenten"),
            Subcategory("Fruit"),
            Subcategory("Noten")
        ),
        "myVeryUniqueObjectId"
    )

    @Test
    fun `test equality`() {
        val copy = Category(
            "Groenten & Fruit",
            mutableListOf(
                Subcategory("Groenten"),
                Subcategory("Fruit"),
                Subcategory("Noten")
            ),
            "myVeryUniqueObjectId"
        )
        val other = Category("other", mutableListOf(), "myVeryUniqueObjectId")

        assertEquals(copy, copy)
        assertEquals(other, other)
        assertEquals(category, category)
        assertEquals(category, copy)
        assertEquals(category, other)
        assertEquals(other, copy)
    }

    @TestFactory
    fun `test inequality`(): List<DynamicTest> {
        return listOf(Category("Groenten & Fruit"), Category("Random"), null, Subcategory("Noten"))
            .map {
                DynamicTest.dynamicTest("Test if arg is not equal to ${category.name}")
                { assertNotEquals(category, it) }
            }
    }
}