package be.ehb.gdt.nutrisearch_api.models

import jakarta.validation.constraints.NotBlank
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("categories")
class Category(
    @NotBlank(message = "Category name needs to be provided")
    var name: String,
    val subcategories: MutableList<Subcategory> = mutableListOf(),
    @Id
    val id: String = ObjectId.get().toHexString()


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Category

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}