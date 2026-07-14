package com.example.ecommerce_template.data.address

data class Address(
    val id: String,
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String,
    val isDefault: Boolean
) {
    val displayLabel: String get() = "$street, $city, $state $zipCode"
}
