package com.example.ecommerce_template.core

import com.example.ecommerce_template.network.dto.error.ErrorResponseDto
import com.example.ecommerce_template.network.dto.error.HttpValidationErrorDto
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import java.io.IOException

private val errorJson = Json { ignoreUnknownKeys = true; explicitNulls = false }

suspend fun <T> safeCall(block: suspend () -> T): ApiResult<T> {
    return try {
        ApiResult.Success(block())
    } catch (e: HttpException) {
        ApiResult.Error(
            message = e.parseErrorMessage() ?: "Error ${e.code()} en el servidor",
            httpStatus = e.code()
        )
    } catch (e: IOException) {
        ApiResult.Error(message = e.message ?: "Sin conexión a internet")
    } catch (e: Throwable) {
        ApiResult.Error(message = e.message ?: "Error inesperado")
    }
}

private fun HttpException.parseErrorMessage(): String? {
    val raw = response()?.errorBody()?.string()?.takeIf { it.isNotBlank() } ?: return null
    return runCatching { errorJson.decodeFromString(ErrorResponseDto.serializer(), raw) }
        .map { it.detail }
        .getOrNull()
        ?: runCatching { errorJson.decodeFromString(HttpValidationErrorDto.serializer(), raw) }
            .map { it.detail.joinToString(separator = "\n") { d -> d.msg } }
            .getOrNull()
}
