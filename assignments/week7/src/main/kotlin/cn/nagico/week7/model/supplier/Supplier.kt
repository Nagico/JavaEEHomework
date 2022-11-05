package cn.nagico.week7.model.supplier

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
@Schema(description = "供应商")
open class Supplier(
    @Schema(description = "id")
    var id: Long? = null,
    @Schema(description = "名称")
    var name: String? = null,
    @Schema(description = "描述")
    var description: String? = null,
    @Schema(description = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    var createTime: LocalDateTime? = null,
    @Schema(description = "更新时间")
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
