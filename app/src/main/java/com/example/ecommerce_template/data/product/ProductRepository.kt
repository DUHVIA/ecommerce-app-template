package com.example.ecommerce_template.data.product

class ProductRepository {

    private val mockProducts = listOf(
        Product(
            id = 1,
            name = "Proteína Whey Gold Standard 2lb",
            description = "Aislado de proteína de suero de leche, 24g de proteína por servicio. Sabor Chocolate.",
            price = 149.90,
            category = "Suplementos",
            imageUrl = null,
            stock = 15
        ),
        Product(
            id = 2,
            name = "Creatina Monohidratada 300g",
            description = "Creatina micronizada pura para mejorar la fuerza y el rendimiento muscular. Sin sabor.",
            price = 89.00,
            category = "Suplementos",
            imageUrl = null,
            stock = 20
        ),
        Product(
            id = 3,
            name = "Cinturón de Cuero para Powerlifting",
            description = "Cinturón de soporte lumbar de alta resistencia, 10mm de grosor con hebilla de acero.",
            price = 120.00,
            category = "Accesorios",
            imageUrl = null,
            stock = 5
        ),
        Product(
            id = 4,
            name = "Mancuernas Hexagonales 10kg (Par)",
            description = "Mancuernas de caucho de alta calidad con mango cromado ergonómico y antideslizante.",
            price = 160.00,
            category = "Equipamiento",
            imageUrl = null,
            stock = 8
        ),
        Product(
            id = 5,
            name = "Shaker / Mezclador Pro 700ml",
            description = "Vaso mezclador con compartimento para pastillas y polvo. Libre de BPA.",
            price = 25.00,
            category = "Accesorios",
            imageUrl = null,
            stock = 50
        )
    )

    // 1. Obtener todo el catálogo para la pantalla principal (Home)
    fun getAllProducts(): List<Product> {
        return mockProducts
    }

    // 2. Obtener un producto específico para la pantalla de "Detalle del Producto"
    fun getProductById(id: Int): Product? {
        return mockProducts.find { it.id == id }
    }

    // 3. Opcional: Filtrar por categoría en la interfaz
    fun getProductsByCategory(category: String): List<Product> {
        return mockProducts.filter { it.category == category }
    }
}