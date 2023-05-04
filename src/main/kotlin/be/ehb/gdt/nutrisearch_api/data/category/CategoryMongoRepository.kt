package be.ehb.gdt.nutrisearch_api.data.category

import be.ehb.gdt.nutrisearch_api.models.Category
import be.ehb.gdt.nutrisearch_api.models.Subcategory
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Repository

@Repository
class CategoryMongoRepository(val mongoTemplate: MongoTemplate) : CategoryRepository {
    override fun findAllCategories(): List<Category> = mongoTemplate.findAll(Category::class.java)

    override fun findCategory(id: String) = mongoTemplate.findById(id, Category::class.java)

    override fun insertCategory(name: String) = mongoTemplate.insert(Category(name))

    override fun updateCategory(id: String, name: String) {
        val query = Query(Criteria.where("_id").`is`(id))
        val update = Update().set("name", name)
        mongoTemplate.updateFirst(query, update, Category::class.java)
    }

    override fun deleteCategory(id: String) {
        val query = Query(Criteria.where("_id").`is`(id))
        mongoTemplate.remove(query)
    }

    override fun findAllSubcategories(parentId: String) =
        mongoTemplate.findById(parentId, Category::class.java)!!.subcategories

    override fun findSubcategory(parentId: String, id: String): Subcategory? {
        return mongoTemplate.findById(parentId, Category::class.java)!!.subcategories.find { it.id == id}
    }

    override fun insertSubcategory(parentId: String, name: String): Subcategory {
        val query = Query(Criteria.where("_id").`is`(parentId))
        val subcategory = Subcategory(name)
        val update = Update().push("subcategories", subcategory)
        mongoTemplate.updateFirst(query, update, Category::class.java)
        return subcategory
    }

    override fun updateSubcategory(parentId: String, id: String, name: String) {
        val query = Query(Criteria.where("_id").`is`(parentId).and("subcategories._id").`is`(id))
        val update = Update().set("subcategories.$.name", name)
        mongoTemplate.updateFirst(query, update, Category::class.java)
    }

    override fun deleteSubcategory(parentId: String, id: String) {
        val query = Query(Criteria.where("_id").`is`(parentId).and("subcategories._id").`is`(id))
        mongoTemplate.remove(query, Category::class.java)
    }

    override fun existsCategoryById(id: String): Boolean {
        val query = Query(Criteria.where("_id").`is`(id))
        return mongoTemplate.exists(query, Category::class.java)
    }

    override fun existsSubcategoryById(parentId: String, id: String): Boolean {
        val query = Query(Criteria.where("_id").`is`(parentId).and("subcategories._id").`is`(id))
        return mongoTemplate.exists(query, Category::class.java)
    }


}