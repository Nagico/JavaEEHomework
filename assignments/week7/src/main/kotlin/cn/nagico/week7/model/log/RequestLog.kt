package cn.nagico.week7.model.log

import com.baomidou.mybatisplus.annotation.TableName
import java.io.Serializable
import java.time.LocalDateTime

/**
 * <p>
 * 
 * </p>
 *
 * @author nagico
 * @since 2022-11-04
 */
@TableName("request_log")
open class RequestLog : Serializable {

    var id: Long? = null

    var controllerMethod: String? = null

    var path: String? = null

    var method: String? = null

    var ip: String? = null

    var header: String? = null

    var success: Byte? = null

    var time: Double? = null

    var createTime: LocalDateTime? = null

    override fun toString(): String {
        return "RequestLog{" +
        "id=" + id +
        ", path=" + path +
        ", method=" + method +
        ", ip=" + ip +
        ", header=" + header +
        ", success=" + success +
        ", time=" + time +
        ", createTime=" + createTime +
        "}"
    }
}
