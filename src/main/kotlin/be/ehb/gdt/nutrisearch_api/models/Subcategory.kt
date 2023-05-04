package be.ehb.gdt.nutrisearch_api.models

import jakarta.validation.constraints.NotBlank
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id

class Subcategory(
    @NotBlank(message = "Subcategory name needs to be provided")
    var name: String,
    @Id
    val id: String = ObjectId.get().toHexString()
)

