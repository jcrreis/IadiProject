package com.example.iadiproject.services

import com.example.iadiproject.model.*
import com.example.iadiproject.securityconfig.CustomUserDetails

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

    fun changePassword(name: String,oldpassword: String,newpassword: String){

        val newEncryptedPass: String = BCryptPasswordEncoder().encode(newpassword)
        val user = users.findUserDAOByName(name).orElseThrow(){
            NotFoundException("User not found")
        }
        if(BCryptPasswordEncoder().matches(oldpassword, user.password)) {
            user.changePassword(newEncryptedPass)
            users.save(user)
        }
        else{
            throw BadRequestExcepetion("Old password is invalid")
        }
    }

}




