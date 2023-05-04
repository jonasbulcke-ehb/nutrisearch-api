package be.ehb.gdt.nutrisearch_api.controllers

import be.ehb.gdt.nutrisearch_api.data.product.ProductRepository
import be.ehb.gdt.nutrisearch_api.models.Product
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/products")
class ProductsController(private val productRepository: ProductRepository) {

    @GetMapping
    fun getProducts() = ResponseEntity.ok(productRepository.findAllProducts())

    @GetMapping("{id}")
    fun getProduct(@PathVariable id: String) = ResponseEntity.ok(productRepository.findProduct(id))

    @PostMapping
    fun postProduct(@RequestBody product: Product): ResponseEntity<Product> {
        return ResponseEntity(productRepository.insertProduct(product), HttpStatus.CREATED)
    }

    @DeleteMapping("{id}")
    fun deleteProduct(@PathVariable id: String): ResponseEntity<Unit> {
        productRepository.deleteProduct(id)
        return ResponseEntity.noContent().build()
    }
}