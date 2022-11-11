package cn.nagico.week8.security

import cn.nagico.week8.dao.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

/**
 * 创建一个实现UserDetailsService的Bean，这个Bean会给AuthenticationProvider
 */
@Service
class DbUserDetailService : UserDetailsService {
    @Autowired
    var userRepository: UserRepository? = null

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository?.findByUsername(username)
            ?: throw UsernameNotFoundException("User $username is not found")

        val roles = Array(user.roles?.size ?: 0) { "" }
        user.roles?.forEachIndexed { index, role -> roles[index] = role.name!! }

        return User.builder()
            .username(user.username)
            .password(BCryptPasswordEncoder().encode(user.password))
            .roles(*roles).build()
    }
}