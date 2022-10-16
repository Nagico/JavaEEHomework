package cn.nagico.week4.util.extension

import cn.nagico.week4.util.response.ApiResponseType
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

fun ResultActions.checkCode(excepted: ApiResponseType = ApiResponseType.SUCCESS): ResultActions {
    return andExpect ( jsonPath("$.code").value(excepted.code) )
}