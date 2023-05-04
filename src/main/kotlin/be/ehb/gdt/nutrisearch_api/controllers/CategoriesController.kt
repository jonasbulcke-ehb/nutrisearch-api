package be.ehb.gdt.nutrisearch_api.controllers

import be.ehb.gdt.nutrisearch_api.models.Category
import be.ehb.gdt.nutrisearch_api.services.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/categories")
class CategoriesController(private val service: CategoryService) {

    @GetMapping
    fun getCategories() = ResponseEntity.ok(service.getCategories())

    @GetMapping("/{id}")
    fun getCategory(@PathVariable id: String): ResponseEntity<Category> = ResponseEntity.ok(service.getCategory(id))

    @PostMapping
    fun postCategory(@RequestParam name: String): ResponseEntity<Category> =
        ResponseEntity(service.createCategory(name), HttpStatus.CREATED)

    @PutMapping("/{id}")
    fun putCategory(@PathVariable id: String, @RequestParam name: String): ResponseEntity<Unit> {
        service.updateCategory(id, name)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    fun deleteMapping(@PathVariable id: String): ResponseEntity<Unit> {
        service.deleteCategory(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}/subcategories")
    fun getSubcategories(@PathVariable id: String) = ResponseEntity.ok(service.getSubcategories(id))

    @GetMapping("/{parentId}/subcategories/{id}")
    fun getSubcategory(@PathVariable parentId: String, @PathVariable id: String) =
        ResponseEntity.ok(service.getSubcategory(parentId, id))

    @PostMapping("/{id}/subcategories")
    fun postSubcategory(@PathVariable id: String, @RequestParam name: String): ResponseEntity<Unit> {
        service.createSubcategory(id, name)
        return ResponseEntity.noContent().build()
    }

    @PutMapping("/{parentId}/subcategories/{id}")
    fun putSubcategory(
        @PathVariable parentId: String,
        @PathVariable id: String,
        @RequestParam name: String
    ): ResponseEntity<Unit> {
        service.updateSubcategory(parentId, id, name)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{parentId}/subcategories/{id}")
    fun deleteSubcategory(@PathVariable parentId: String, @PathVariable id: String): ResponseEntity<Unit> {
        service.deleteSubcategory(parentId, id)
        return ResponseEntity.noContent().build()
    }
}

