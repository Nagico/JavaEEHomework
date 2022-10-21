package cn.nagico.week5.util.extension

import cn.nagico.week5.util.response.ApiResponseType
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath

fun ResultActions.checkCode(excepted: ApiResponseType = ApiResponseType.SUCCESS): ResultActions {
    return andExpect ( jsonPath("$.code").value(excepted.code) )
}