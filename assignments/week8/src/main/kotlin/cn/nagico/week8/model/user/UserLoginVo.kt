package cn.nagico.week8.model.user

data class UserLoginVo(
    val id: Long,
    val username: String,
    val roles: List<Role>,
    val token: String
)
