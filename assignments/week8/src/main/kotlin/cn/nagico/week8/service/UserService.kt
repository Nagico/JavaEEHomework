package cn.nagico.week8.service

import cn.nagico.week8.dao.UserRepository
import cn.nagico.week8.model.user.User
import cn.nagico.week8.model.user.UserDetail
import cn.nagico.week8.util.exception.ApiException
import cn.nagico.week8.util.response.ApiResponseType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
interface UserService {
    fun getUserById(id: Long): User

    fun getUserByName(username: String): User

    fun deleteUser(id: Long)

    fun updateUser(id: Long, user: User): User

    fun findAll(): List<User>

    fun addUser(user: User): User

    fun getUserDetailByName(username: String): UserDetail?

    fun getUserDetailById(id: Long): UserDetail?
}