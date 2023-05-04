package be.ehb.gdt.nutrisearch_api.models

class Preparation(
    val id: String,
    val name: String,
    val nutrients: Map<Nutrient, Int>
): SoftDeletable {
    private var _isDeleted: Boolean = false
    override val isDeleted: Boolean
        get() = _isDeleted

    override fun delete() {
        _isDeleted = true
    }
}