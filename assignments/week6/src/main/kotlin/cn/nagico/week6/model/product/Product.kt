package cn.nagico.week6.model.product

import com.baomidou.mybatisplus.annotation.FieldFill
import com.baomidou.mybatisplus.annotation.TableField
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
open class Product(
    var id: Long? = null,
    var name: String? = null,
    var description: String? = null,
    var image: String? = null,
    var price: Double? = null,
    @TableField(fill = FieldFill.INSERT)
    var createTime: LocalDateTime? = null,
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
