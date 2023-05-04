package be.ehb.gdt.nutrisearch_api.data.product

import be.ehb.gdt.nutrisearch_api.models.Product
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

@Repository
@SuppressWarnings("kotlin:S6518")
class ProductMongoRepository(private val mongoTemplate: MongoTemplate) : ProductRepository {
    private val query get() = Query(Criteria.where("isDeleted").`is`(false))

    override fun findAllProducts(): List<Product> {
        query.fields().exclude("preparations", "servingSizes")
        return mongoTemplate.find(query, Product::class.java)
    }

    override fun findProduct(id: String): Product? {
        query.addCriteria(Criteria.where("_id").`is`(id))
        return mongoTemplate.findOne(query, Product::class.java)
    }

    override fun findProductsByCategory(categoryId: String): List<Product> {
        query.addCriteria(Criteria.where("categoryId").`is`(categoryId))
        query.fields().exclude("preparations", "servingSizes")
        return mongoTemplate.find(query, Product::class.java)
    }

    override fun findProductsByName(name: String): List<Product> {
        query.addCriteria(Criteria.where("name").regex(name))
        query.fields().exclude("preparations", "servingSizes")
        return mongoTemplate.find(query, Product::class.java)
    }

    override fun insertProduct(product: Product) = mongoTemplate.insert(product)

    override fun deleteProduct(id: String) {
        query.addCriteria(Criteria.where("_id").`is`(id))
        val update = Update().set("isDeleted", true)
        mongoTemplate.updateFirst(query, update, Product::class.java)
    }
}