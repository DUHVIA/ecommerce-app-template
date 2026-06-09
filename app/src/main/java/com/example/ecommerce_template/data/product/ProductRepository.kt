package com.example.ecommerce_template.data.product

import com.example.ecommerce_template.R

object ProductRepository {

    private val mockProducts = listOf(
        Product(
            id = 1,
            name = "Proteína Whey Gold Standard 2lb",
            description = "Aislado de proteína de suero de leche, 24g de proteína por servicio. Sabor Chocolate.",
            price = 149.90,
            category = "Suplementos",
            imageRes = R.drawable.prod_proteina,
            stock = 15
        ),
        Product(
            id = 2,
            name = "Creatina Monohidratada 300g",
            description = "Creatina micronizada pura para mejorar la fuerza y el rendimiento muscular. Sin sabor.",
            price = 89.00,
            category = "Suplementos",
            imageRes = R.drawable.prod_creatina,
            stock = 20
        ),
        Product(
            id = 3,
            name = "Cinturón de Cuero para Powerlifting",
            description = "Cinturón de soporte lumbar de alta resistencia, 10mm de grosor con hebilla de acero.",
            price = 120.00,
            category = "Accesorios",
            imageRes = R.drawable.prod_cinturon,
            stock = 5
        ),
        Product(
            id = 4,
            name = "Mancuernas Hexagonales 10kg (Par)",
            description = "Mancuernas de caucho de alta calidad con mango cromado ergonómico y antideslizante.",
            price = 160.00,
            category = "Equipamiento",
            imageRes = R.drawable.prod_mancuerna,
            stock = 8
        ),
        Product(
            id = 5,
            name = "Shaker / Mezclador Pro 600ml",
            description = "Vaso mezclador con compartimento para pastillas y polvo. Libre de BPA.",
            price = 25.00,
            category = "Accesorios",
            imageRes = R.drawable.prod_shaker,
            stock = 50
        )
    )

    fun getAllProducts(): List<Product> = mockProducts

    fun getProductById(id: Int): Product? = mockProducts.find { it.id == id }

    fun getProductsByCategory(category: String): List<Product> = mockProducts.filter { it.category == category }
}