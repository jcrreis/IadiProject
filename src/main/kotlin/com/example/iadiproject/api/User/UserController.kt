package com.example.iadiproject.api.User

import com.example.iadiproject.api.ChangePasswordDTO
import com.example.iadiproject.api.CurrentUserDTO
import com.example.iadiproject.api.UserDTO
import com.example.iadiproject.model.ReviewerDAO
import com.example.iadiproject.model.SponsorDAO
import com.example.iadiproject.model.StudentDAO
import com.example.iadiproject.model.UserDAO
import com.example.iadiproject.services.UnauthorizedException
import com.example.iadiproject.services.UserService
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RestController


@RestController
class UserController(val users: UserService): UserAPI
{

    override fun getAll(): List<UserDTO> = users.getAll().map{
         UserDTO(it.id, it.name, it.email, it.address)
    }


    override fun getLoggedUser(): CurrentUserDTO?{
        val auth: Authentication = SecurityContextHolder.getContext().authentication
        if(auth.name == "admin"){
            /*If admin return because it doesn't belong to users collection*/
           return CurrentUserDTO(0,"admin","","","Admin")
        }
        val user: UserDAO = users.findByName(auth.name) ?: throw UnauthorizedException()
        var type = ""
        when (user) {
            is StudentDAO -> {type = "Student"}
            is ReviewerDAO -> { type = "Reviewer" }
            is SponsorDAO ->{ type = "Sponsor" }
        }

        return user.let {
            CurrentUserDTO(it.id,it.name,it.email,it.address,type)
        }
    }

    override fun changePassword(passwords: ChangePasswordDTO){
        val auth: Authentication = SecurityContextHolder.getContext().authentication
        users.changePassword(auth.name,passwords.oldpassword,passwords.newpassword)
    }

}