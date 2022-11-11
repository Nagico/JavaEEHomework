package cn.nagico.week8.service.impl

import cn.nagico.week8.dao.UserRepository
import cn.nagico.week8.model.user.User
import cn.nagico.week8.model.user.UserDetail
import cn.nagico.week8.service.UserService
import cn.nagico.week8.util.exception.ApiException
import cn.nagico.week8.util.response.ApiResponseType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class UserServiceImpl : UserService {
    @Autowired
    lateinit var userRepository: UserRepository

    @Cacheable(cacheNames = ["user"], key = "#id")
    override fun getUserById(id: Long): User {
        return userRepository.findByIdOrNull(id) ?: throw ApiException(ApiResponseType.ID_NOT_EXIST)
    }

    override fun getUserByName(username: String): User {
        return userRepository.findByUsername(username) ?: throw ApiException(ApiResponseType.ID_NOT_EXIST)
    }

    @CacheEvict(cacheNames = ["user"], key = "#id")
    override fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }

    @CacheEvict(cacheNames = ["user"], key = "#id")
    override fun updateUser(id: Long, user: User): User {
        if (id != user.id || userRepository.findById(id).isEmpty) {
            throw ApiException(ApiResponseType.ID_NOT_EXIST)
        }
        return userRepository.saveAndFlush(user)
    }

    override fun findAll(): List<User> {
        return userRepository.findAll()
    }

    override fun addUser(user: User): User {
        return userRepository.save(user)
    }

    override fun getUserDetailByName(username: String): UserDetail? {
        return try {
            val user = getUserByName(username)
            UserDetail(user)
        } catch (e: ApiException) {
            null
        }
    }

    override fun getUserDetailById(id: Long): UserDetail? {
        return try {
            val user = getUserById(id)
            UserDetail(user)
        } catch (e: ApiException) {
            null
        }
    }
}