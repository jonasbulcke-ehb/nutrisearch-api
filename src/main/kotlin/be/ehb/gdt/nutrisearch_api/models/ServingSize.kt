package be.ehb.gdt.nutrisearch_api.models

class ServingSize(val grams: Double, val name: String) : SoftDeletable {
    private var _isDeleted: Boolean = false
    override val isDeleted: Boolean
        get() = _isDeleted

    constructor() : this(1.0, "Gram")

    override fun delete() {
        _isDeleted = true
    }
}