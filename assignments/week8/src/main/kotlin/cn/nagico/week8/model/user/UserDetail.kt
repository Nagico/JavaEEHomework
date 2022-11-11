package cn.nagico.week8.model.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetail(
    private val user: User,
) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorities: MutableList<GrantedAuthority> = ArrayList()
        for (role in user.roles!!) {
            for (permission in role.permissions!!) {
                authorities.add(SimpleGrantedAuthority(permission))
            }
        }
        return authorities
    }

    override fun getPassword(): String {
        return user.password!!
    }

    override fun getUsername(): String {
        return user.username!!
    }

    fun getId(): Long {
        return user.id!!
    }

    fun getIdString(): String {
        return user.id!!.toString()
    }

    fun getRolesList(): List<Role> {
        return user.roles!!.toList()
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}