package be.ehb.gdt.nutrisearch_api.data.category

import be.ehb.gdt.nutrisearch_api.models.Category
import be.ehb.gdt.nutrisearch_api.models.Subcategory

interface CategoryRepository {
    fun findAllCategories(): List<Category>
    fun findCategory(id: String): Category?
    fun insertCategory(name: String): Category
    fun updateCategory(id: String, name: String)
    fun deleteCategory(id: String)
    fun findAllSubcategories(parentId: String): List<Subcategory>
    fun findSubcategory(parentId: String, id: String): Subcategory?
    fun insertSubcategory(parentId: String, name: String): Subcategory
    fun updateSubcategory(parentId: String, id: String, name: String)
    fun deleteSubcategory(parentId: String, id: String)
    fun existsCategoryById(id: String): Boolean
    fun existsSubcategoryById(parentId: String, id: String): Boolean
}