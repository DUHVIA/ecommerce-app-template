package com.example.ecommerce_template.data.address

import com.example.ecommerce_template.core.ApiResult
import com.example.ecommerce_template.core.safeCall
import com.example.ecommerce_template.network.api.AddressApi
import com.example.ecommerce_template.network.dto.address.AddressCreateDto
import com.example.ecommerce_template.network.mapper.toDomain

class AddressRepository(
    private val addressApi: AddressApi
) {
    suspend fun list(): ApiResult<List<Address>> =
        safeCall { addressApi.list() }.map { list -> list.map { it.toDomain() } }

    suspend fun create(
        street: String,
        city: String,
        state: String,
        zipCode: String,
        isDefault: Boolean
    ): ApiResult<Address> {
        val result = safeCall {
            addressApi.create(
                AddressCreateDto(
                    street = street,
                    city = city,
                    state = state,
                    zipCode = zipCode,
                    isDefault = isDefault
                )
            )
        }
        return result.map { it.toDomain() }
    }

    suspend fun setDefault(addressId: String): ApiResult<Address> =
        safeCall { addressApi.setDefault(addressId) }.map { it.toDomain() }

    suspend fun delete(addressId: String): ApiResult<Unit> =
        safeCall {
            addressApi.delete(addressId)
            Unit
        }
}
