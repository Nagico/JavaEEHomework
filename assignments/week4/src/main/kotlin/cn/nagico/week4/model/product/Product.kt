package cn.nagico.week4.model.product

import io.swagger.v3.oas.annotations.media.Schema
import java.util.Date

@Schema(description = "商品")
data class Product(
    @Schema(description = "商品id")
    var id: Int? = null,
    @Schema(description = "商品名称")
    var name: String = "",
    @Schema(description = "商品价格")
    var price: Double = 0.0,
    @Schema(description = "商品描述")
    var description: String = "",
    @Schema(description = "商品图片")
    var image: String = "",
    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    var createTime: Date?,
    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    var updateTime: Date?,
)