package cn.nagico.week7.model.product

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
import io.swagger.v3.oas.annotations.media.Schema
import java.io.Serializable
import java.time.LocalDateTime

/**
 * <p>
 * 
 * </p>
 *
 * @author nagico
 * @since 2022-10-27
 */
@Schema(description = "商品")
open class Product(
    @Schema(description = "id")
    var id: Long? = null,
    @Schema(description = "名称")
    var name: String? = null,
    @Schema(description = "描述")
    var description: String? = null,
    @Schema(description = "图片")
    var image: String? = null,
    @Schema(description = "价格")
    var price: Double? = null,
    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    var createTime: LocalDateTime? = null,
    @Schema(description = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    var updateTime: LocalDateTime? = null,
) : Serializable {

    constructor(product: Product) : this(product.id, product.name, product.description, product.image, product.price, product.createTime, product.updateTime)

    override fun toString(): String {
        return "Product{" +
        "id=" + id +
        ", createTime=" + createTime +
        ", description=" + description +
        ", image=" + image +
        ", name=" + name +
        ", price=" + price +
        ", updateTime=" + updateTime +
        "}"
    }
}
