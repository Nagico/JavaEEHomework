package cn.nagico.week4.util.exception

import cn.nagico.week4.util.annotation.Slf4j
import cn.nagico.week4.util.annotation.Slf4j.Companion.logger
import cn.nagico.week4.util.response.ApiResponse
import cn.nagico.week4.util.response.ApiResponseType
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestControllerAdvice

@Slf4j
@RestControllerAdvice
class ApiExceptionHandler {
    /**
     * 处理自定义异常
     *
     * @param e 自定义异常
     * @return 标准API响应
     */
    @ResponseBody
    @ExceptionHandler(ApiException::class)
    fun handleApiException(e: ApiException): ApiResponse<String> {
        logger.error(e.message)
        return ApiResponse.fail(e.code, e.message, e.httpStatus)
    }

    /**
     * 处理未知异常
     *
     * @param e 未知异常
     * @return 标准API响应
     */
    @ResponseBody
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ApiResponse<String> {
        logger.error(e.message)
        if (logger.isInfoEnabled) {
            e.printStackTrace()
        }
        return ApiResponse(ApiResponseType.UNKNOWN_ERROR.code, ApiResponseType.UNKNOWN_ERROR.message, e.message, ApiResponseType.UNKNOWN_ERROR.httpStatus)
    }
}