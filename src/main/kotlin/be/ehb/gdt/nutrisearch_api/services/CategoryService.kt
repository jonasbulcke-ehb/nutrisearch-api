package be.ehb.gdt.nutrisearch_api.services

import be.ehb.gdt.nutrisearch_api.models.Category
import be.ehb.gdt.nutrisearch_api.models.Subcategory

interface CategoryService {
    fun getCategories(): List<Category>
    fun getCategory(id: String): Category
    fun createCategory(name: String): Category
    fun updateCategory(id: String, name: String)
    fun deleteCategory(id: String)

//    fun getSubcategories(id: String): List<Subcategory>?
//    fun getSubcategory(parentId: String, id: String): Subcategory
    fun createSubcategory(parentId: String, name: String)
    fun updateSubcategory(parentId: String, id: String, name: String)
    fun deleteSubcategory(parentId: String, id: String)
    fun getSubcategories(parentId: String): List<Subcategory>
    fun getSubcategory(parentId: String, id: String): Subcategory
}