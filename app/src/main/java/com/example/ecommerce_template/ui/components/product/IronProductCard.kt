package com.example.ecommerce_template.ui.components.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecommerce_template.R
import com.example.ecommerce_template.data.product.Product
@Composable
fun IronProductCard(
    modifier: Modifier = Modifier,
    item: Product,
    badgeText: String? = item.category,
    onAddToCart: () -> Unit,
    onClick: () -> Unit,
    badgeColor: Color = MaterialTheme.colorScheme.primary,
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color(0xFF0F0F0F), RoundedCornerShape(16.dp))
            ) {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = "Imagen de ${item.name}",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                if (badgeText != null) {
                    Text(
                        text = badgeText,
                        style = MaterialTheme.typography.labelSmall.copy(color = Color.Black),
                        modifier = Modifier
                            .padding(8.dp)
                            .background(badgeColor, RoundedCornerShape(2.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = item.category.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = item.name.uppercase(),
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$${item.price}",
                    style = MaterialTheme.typography.labelLarge
                )

                IconButton(
                    onClick = onAddToCart,
                    modifier = Modifier.size(36.dp),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color(0xFF333333),
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Add,
                        contentDescription = "Añadir",
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Composable
fun IronProductCardPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            IronProductCard(
                item = Product(
                    id = "5",
                    name = "Shaker / Mezclador Pro 600ml",
                    description = "Vaso mezclador con compartimento para pastillas y polvo. Libre de BPA.",
                    price = 25.00,
                    category = "Accesorios",
                    imageRes = R.drawable.prod_shaker,
                    stock = 50
                ),
                onAddToCart = { },
                onClick = { }
            )
        }
    }
}