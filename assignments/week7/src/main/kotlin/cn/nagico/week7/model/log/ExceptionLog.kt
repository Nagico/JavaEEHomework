package cn.nagico.week7.model.log

import com.baomidou.mybatisplus.annotation.TableName
import java.io.Serializable

/**
 * <p>
 * 
 * </p>
 *
 * @author nagico
 * @since 2022-11-04
 */
@TableName("exception_log")
open class ExceptionLog() : Serializable {
    var id: Long? = null

    var classType: String? = null

    var exceptionCode: String? = null

    var exceptionType: String? = null

    var exceptionMsg: String? = null

    var exceptionStack: String? = null

    override fun toString(): String {
        return "ExceptionLog{" +
        "id=" + id +
        ", exceptionType=" + exceptionType +
        ", exceptionMsg=" + exceptionMsg +
        ", exceptionStack=" + exceptionStack +
        "}"
    }

    constructor(vo: ExceptionLogDto) : this() {
        this.id = vo.id
        this.classType = vo.classType
        this.exceptionCode = vo.exceptionCode
        this.exceptionType = vo.exceptionType
        this.exceptionMsg = vo.exceptionMsg
        this.exceptionStack = vo.exceptionStack
    }
}
