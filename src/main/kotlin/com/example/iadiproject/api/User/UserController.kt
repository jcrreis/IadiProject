package com.example.iadiproject.api.User

import com.example.iadiproject.api.ChangePasswordDTO
import com.example.iadiproject.api.SimpleInstitutionDTO
import com.example.iadiproject.api.UserDTO
import com.example.iadiproject.services.UserService
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RestController


@RestController
class UserController(val users: UserService): UserAPI{

    override fun getAll(): List<UserDTO> = users.getAll().map{
         UserDTO(it.id, it.name, it.email, it.address)
    }


    override fun getLoggedUser(): UserDTO?{
        val auth: Authentication = SecurityContextHolder.getContext().authentication
        return users.findByName(auth.name)?.let {
            UserDTO(it.id,it.name,it.email,it.address)
        }
    }

    override fun changePassword(passwords: ChangePasswordDTO){
        val auth: Authentication = SecurityContextHolder.getContext().authentication
        users.changePassword(auth.name,passwords.oldpassword,passwords.newpassword)
    }

}