package be.ehb.gdt.nutrisearch_api.services.impl

import be.ehb.gdt.nutrisearch_api.data.category.CategoryRepository
import be.ehb.gdt.nutrisearch_api.exceptions.CategoryNotFoundException
import be.ehb.gdt.nutrisearch_api.models.Category
import be.ehb.gdt.nutrisearch_api.models.Subcategory
import be.ehb.gdt.nutrisearch_api.services.CategoryService
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(private val repo: CategoryRepository) : CategoryService {
    override fun getCategories(): List<Category> = repo.findAllCategories()
    override fun getCategory(id: String) = repo.findCategory(id) ?: throw CategoryNotFoundException(id)

    override fun createCategory(name: String): Category {
        return repo.insertCategory(name)
    }

    override fun updateCategory(id: String, name: String) {
        if (!repo.existsCategoryById(id)) {
            throw CategoryNotFoundException(id)
        }
        repo.updateCategory(id, name)
    }

    override fun deleteCategory(id: String) {
        if (!repo.existsCategoryById(id)) {
            throw CategoryNotFoundException(id)
        }
        repo.deleteCategory(id)
    }

    override fun getSubcategories(parentId: String): List<Subcategory> {
        if (!repo.existsCategoryById(parentId)) {
            throw CategoryNotFoundException(parentId)
        }
        return repo.findAllSubcategories(parentId)
    }

    override fun getSubcategory(parentId: String, id: String) =
        repo.findSubcategory(parentId, id) ?: throw CategoryNotFoundException("$parentId/$id")

    override fun createSubcategory(parentId: String, name: String) {
        if (!repo.existsCategoryById(parentId)) {
            throw CategoryNotFoundException(parentId)
        }
        repo.insertSubcategory(parentId, name)
    }

    override fun updateSubcategory(parentId: String, id: String, name: String) {
        if (!repo.existsSubcategoryById(parentId, id)) {
            throw CategoryNotFoundException("$parentId/$id")
        }
        repo.updateSubcategory(parentId, id, name)
    }

    override fun deleteSubcategory(parentId: String, id: String) {
        if(!repo.existsSubcategoryById(parentId, id)) {
            throw CategoryNotFoundException("$parentId/$id")
        }
        repo.deleteSubcategory(parentId, id)
    }
}