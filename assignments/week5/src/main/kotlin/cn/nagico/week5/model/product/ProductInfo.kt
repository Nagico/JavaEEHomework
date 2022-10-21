package cn.nagico.week5.model.product

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "商品信息")
data class ProductInfo(
    @Schema(description = "商品id")
    var id: Long = 0,
    @Schema(description = "商品名称")
    var name: String = "",
    @Schema(description = "商品价格")
    var price: Double = 0.0,
    @Schema(description = "商品图片")
    var image: String = "",
) {
    constructor(product: Product) : this(product.id!!, product.name, product.price, product.image)
}
