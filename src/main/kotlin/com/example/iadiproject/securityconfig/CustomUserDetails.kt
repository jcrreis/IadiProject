package com.example.iadiproject.securityconfig

import com.example.iadiproject.services.UserService
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


class CustomUserDetails(
        private val id: Long,
        private val aUsername:String,
        private val aPassword:String,
        private val authorities: MutableCollection<out GrantedAuthority>) : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = authorities

    override fun isEnabled(): Boolean = true

    override fun getUsername(): String = aUsername

    override fun isCredentialsNonExpired(): Boolean = true

    override fun getPassword(): String = aPassword

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    fun getId(): Long = id


}

@Service
class CustomUserDetailsService(
        val users: UserService
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {

        username?.let {
            val userDAO = users.findByName(it)
            if( userDAO !== null) {
                return CustomUserDetails(userDAO.id, userDAO.name, userDAO.password, mutableListOf(SimpleGrantedAuthority(userDAO.roles)))
            } else
                throw UsernameNotFoundException(username)
        }
        throw UsernameNotFoundException(username)
    }
}
