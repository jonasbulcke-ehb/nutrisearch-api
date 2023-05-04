package be.ehb.gdt.nutrisearch_api.models

interface SoftDeletable {
    val isDeleted: Boolean
    fun delete()
}