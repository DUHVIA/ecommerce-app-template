package com.example.ecommerce_template.network.mapper

import com.example.ecommerce_template.data.address.Address
import com.example.ecommerce_template.network.dto.address.AddressDto
import com.example.ecommerce_template.network.dto.order.OrderDto
import com.example.ecommerce_template.network.dto.order.OrderItemDto
import com.example.ecommerce_template.data.order.Order
import com.example.ecommerce_template.data.order.OrderItem
import com.example.ecommerce_template.data.order.OrderStatus

fun OrderItemDto.toDomain(): OrderItem = OrderItem(
    id = id,
    productId = productId,
    productName = productName,
    quantity = quantity,
    unitPrice = unitPrice,
    subtotal = subtotal
)

fun AddressDto.toAddressDomain(): Address = Address(
    id = id,
    street = street,
    city = city,
    state = state,
    zipCode = zipCode,
    isDefault = isDefault
)

fun OrderDto.toDomain(): Order = Order(
    id = id,
    status = OrderStatus.fromApi(status),
    rawStatus = status,
    shippingAddress = shippingAddress.toAddressDomain(),
    subtotal = subtotal,
    taxes = taxes,
    shippingCost = shippingCost,
    total = total,
    items = items.map { it.toDomain() },
    createdAt = createdAt
)
