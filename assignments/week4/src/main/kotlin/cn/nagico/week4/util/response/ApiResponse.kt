package cn.nagico.week4.util.response

/**
 * API响应类
 *
 * @param T 响应数据类型
 * @property code 响应码
 * @property message 响应消息
 * @property data 响应内容
 */
data class ApiResponse<T>(
    var code: String,
    var message: String,
    var data: T?
) {
    companion object {
        fun <T> success(message: String, data: T): ApiResponse<T> {
            return ApiResponse(ApiResponseType.SUCCESS.code, message, data)
        }

        fun <T> success(data: T): ApiResponse<T> {
            return ApiResponse(ApiResponseType.SUCCESS.code, ApiResponseType.SUCCESS.message, data)
        }

        fun <T> fail(code: String, message: String): ApiResponse<T> {
            return ApiResponse(code, message, null)
        }

        fun <T> fail(type: ApiResponseType): ApiResponse<T> {
            return ApiResponse(type.code, type.message, null)
        }
    }
}