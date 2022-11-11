package cn.nagico.week8.model.product

import cn.nagico.week8.model.supplier.Supplier
import io.swagger.v3.oas.annotations.media.Schema
import org.apache.commons.lang3.mutable.Mutable
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.Date
import javax.persistence.Entity
import javax.persistence.EntityListeners
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToMany

@Entity
@EntityListeners(AuditingEntityListener::class)
@Schema(description = "商品")
class Product(
    @Id
    @GeneratedValue
    @Schema(description = "商品id")
    var id: Long? = null,

    @Schema(description = "商品名称")
    var name: String,

    @Schema(description = "商品价格")
    var price: Double,

    @Schema(description = "商品描述")
    var description: String,

    @Schema(description = "商品图片")
    var image: String,

    @ManyToMany(targetEntity = Supplier::class, mappedBy = "products")
    @Schema(description = "商品供应商")
    var suppliers: MutableList<Supplier>,

    @CreatedDate
    @Schema(description = "创建时间", accessMode = Schema.AccessMode.READ_ONLY)
    var createTime: LocalDateTime?,

    @LastModifiedDate
    @Schema(description = "更新时间", accessMode = Schema.AccessMode.READ_ONLY)
    var updateTime: LocalDateTime?,
) {
    override fun toString(): String {
        return "Product(id=$id, name='$name')"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Product) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }
}