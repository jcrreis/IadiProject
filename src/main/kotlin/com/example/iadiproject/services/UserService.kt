package com.example.iadiproject.services

import com.example.iadiproject.model.*
import com.example.iadiproject.securityconfig.CustomUserDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
@Transactional
class UserService(val users: UserRepository) : UserDetailsService{

    fun getAll(): MutableList<UserDAO> = users.findAll()

    fun findByName(name: String): UserDAO? = users.findUserDAOByName(name).orElse(null)

    override fun loadUserByUsername(username: String): UserDetails {
        val user: UserDAO = users.findUserDAOByName(username).orElseThrow(){
            NotFoundException("User with name $username not  found")
        }
        return CustomUserDetails(user.id,user.name, user.password, mutableListOf())
    }



    fun changePassword(name: String,password: String){
        val encryptedPass: String = BCryptPasswordEncoder().encode(password)
        users.findUserDAOByName(name).get().changePassword(encryptedPass)
    }

}




