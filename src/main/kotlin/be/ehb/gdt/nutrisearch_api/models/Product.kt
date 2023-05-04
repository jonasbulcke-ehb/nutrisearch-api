package be.ehb.gdt.nutrisearch_api.models

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.validation.constraints.NotNull
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document("products")
class Product(
    @Indexed
    var name: String,
    @Indexed
    var categoryId: String,
    var isVerified: Boolean = false,
    val preparations: MutableList<Preparation> = mutableListOf(),
    val servingSizes: MutableList<ServingSize> = mutableListOf(),
    @JsonIgnore
    val isDeleted: Boolean = false,
    @Id
    val id: String = ObjectId.get().toHexString(),
) {
    @Indexed
    @NotNull
    lateinit var ownerId: String
}

