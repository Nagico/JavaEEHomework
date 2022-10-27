package cn.nagico.week5.model.supplier

import cn.nagico.week5.model.product.Product
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.util.*
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
@Schema(description = "供应商")
class Supplier (
    @Id
    @GeneratedValue
    @Schema(description = "供应商ID", accessMode = Schema.AccessMode.READ_ONLY)
    var id: Long? = null,

    @Schema(description = "供应商名称")
    var name: String,

    @Schema(description = "供应商地址")
    var description: String,

    @ManyToMany(targetEntity = Product::class, fetch = FetchType.EAGER, cascade = [CascadeType.REFRESH])
    @Schema(description = "供应商提供的商品")
    var products: MutableList<Product>,

    @CreatedDate
    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    var createTime: Date?,

    @LastModifiedDate
    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    var updateTime: Date?,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Supplier) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}