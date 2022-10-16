package cn.nagico.week4.controller

import cn.nagico.week4.util.annotation.Slf4j
import cn.nagico.week4.util.annotation.Slf4j.Companion.logger
import cn.nagico.week4.util.extension.checkCode
import cn.nagico.week4.util.response.ApiResponseType
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@Slf4j
@WebMvcTest(TestController::class)
internal class TestControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun apiTestSuccess() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/"))
            .andDo { logger.info(it.response.contentAsString) }
            .andExpect ( status().isOk )
            .checkCode()
            .andExpect ( jsonPath("$.data").value("") )

    }
}