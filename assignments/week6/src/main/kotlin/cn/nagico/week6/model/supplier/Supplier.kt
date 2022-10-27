package cn.nagico.week6.model.supplier

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
open class Supplier(
    var id: Long? = null,
    var name: String? = null,
    var description: String? = null,
    @TableField(fill = FieldFill.INSERT)
    var createTime: LocalDateTime? = null,
    @TableField(fill = FieldFill.INSERT_UPDATE)
    var updateTime: LocalDateTime? = null
) : Serializable {

    override fun toString(): String {
        return "Supplier{" +
        "id=" + id +
        ", createTime=" + createTime +
        ", description=" + description +
        ", name=" + name +
        ", updateTime=" + updateTime +
        "}"
    }
}
