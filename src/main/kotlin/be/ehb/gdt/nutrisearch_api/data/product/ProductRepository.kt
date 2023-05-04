package be.ehb.gdt.nutrisearch_api.data.product

import be.ehb.gdt.nutrisearch_api.models.Product

interface ProductRepository {
    fun findAllProducts(): List<Product>
    fun findProduct(id: String): Product?
    fun findProductsByCategory(categoryId: String) : List<Product>
    fun findProductsByName(name: String) : List<Product>
    fun insertProduct(product: Product): Product
    fun deleteProduct(id: String)
}