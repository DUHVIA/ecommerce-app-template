package com.example.ecommerce_template.data.profile

class ProfileRepository {

    private var currentProfile = Profile(
        id = "usr_001", // Mismo ID que devuelve el Login exitoso
        firstName = "Gabriel",
        lastName = "Soto",
        email = "gabriel.soto@email.com",
        phoneNumber = "987654321",
        shippingAddress = "Av. Venezuela s/n (Cercado)",
        city = "Arequipa",
        avatarUrl = null
    )

    fun getCurrentUserProfile(): Profile {
        return currentProfile
    }

    // Permite simular que editas tu dirección o teléfono en la UI
    fun updateCurrentUserProfile(updatedProfile: Profile) {
        currentProfile = updatedProfile
    }
}