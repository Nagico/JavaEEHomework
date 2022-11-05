package cn.nagico.week7.util.response

import org.springframework.http.HttpStatus

/**
 * API响应类型
 *
 * @property code 响应码
 * @property message 响应消息
 * @property httpStatus HTTP状态码
 */
enum class ApiResponseType(var code: String, var message: String, var httpStatus: HttpStatus) {
    SUCCESS("00000", "success", HttpStatus.OK),
    USER_NOT_EXIST("A0001", "用户不存在", HttpStatus.BAD_REQUEST),
    PASSWORD_ERROR("A0002", "密码错误", HttpStatus.BAD_REQUEST),
    USER_HAS_EXISTED("A0010", "用户已存在", HttpStatus.BAD_REQUEST),
    PARAMETER_ERROR("A1000", "参数错误", HttpStatus.BAD_REQUEST),
    ID_NOT_EXIST("A1001", "ID不存在", HttpStatus.NOT_FOUND),
    UNKNOWN_ERROR("A9000", "未知错误", HttpStatus.INTERNAL_SERVER_ERROR),
    NOT_LOGIN("A3001","未登录", HttpStatus.UNAUTHORIZED),
    NO_PERMISSION("A3002","无权限", HttpStatus.FORBIDDEN),
}