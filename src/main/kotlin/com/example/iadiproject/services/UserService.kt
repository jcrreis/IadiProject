package com.example.iadiproject.services

import com.example.iadiproject.api.AddUserDTO
import com.example.iadiproject.model.*
import com.example.iadiproject.securityconfig.CustomUserDetails

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.transaction.Transactional


@Service
@Transactional
class UserService(val users: UserRepository,
                  val institutions: InstitutionRepository) : UserDetailsService
{

    fun getAll(): List<UserDAO> = users.findAll()

    fun findByName(name: String): UserDAO? = users.findUserDAOByName(name).orElse(null)

    override fun loadUserByUsername(username: String): UserDetails {
        val user: UserDAO = users.findUserDAOByName(username).orElseThrow(){
            NotFoundException("User with name $username not  found")
        }
        return CustomUserDetails(user.id,user.name, user.password, mutableListOf())
    }

    //fun getAllRegularUsers(): List<UserDAO> = regularUsers.findAll()

    fun changePassword(name: String, oldpassword: String, newpassword: String){

        val newEncryptedPass: String = BCryptPasswordEncoder().encode(newpassword)
        val user = users.findUserDAOByName(name).orElseThrow(){
            ForbiddenException("User not logged in")
        }
        if(BCryptPasswordEncoder().matches(oldpassword, user.password)) {
            user.changePassword(newEncryptedPass)
            users.save(user)
        }
        else{
            throw BadRequestException("Old password is invalid")
        }
    }

    fun verifyIfValuesAreUnique(name: String, email: String){

        if(users.findUserDAOByName(name).isPresent){
            throw BadRequestException("This username already exists")
        }
        if(users.findUserDAOByEmail(email).isPresent){
            throw BadRequestException("This email already exists")
        }

    }

    fun addUser(user: AddUserDTO) : Optional<UserDAO> {
        val aUser = users.findById(user.id)
        return if ( aUser.isPresent )
            Optional.empty()
        else {
            user.password = BCryptPasswordEncoder().encode(user.password)
            addUserToCollection(user)
        }
    }

    fun addUserToCollection(user: AddUserDTO):  Optional<UserDAO>{
        when (user.type) {
            "Student" -> {
                val institution = institutions.findById(user.institutionId).orElseThrow(){
                    NotFoundException("Institution with id ${user.institutionId} doesn't exist")
                }
                val student = StudentDAO(user.id, user.name,user.password,user.email,user.address,
                        institution,mutableListOf(),null)
                return Optional.of(users.save(student))
            }
            "Reviewer" -> {
                val institution = institutions.findById(user.institutionId).orElseThrow(){
                    NotFoundException("Institution with id ${user.institutionId} doesn't exist")
                }
                val reviewer = ReviewerDAO(user.id,user.name,user.password,user.email,user.address,
                        institution, mutableListOf(), mutableListOf())
                return Optional.of(users.save(reviewer))

            }
            "Sponsor" ->{
                val sponsor = SponsorDAO(user.id,user.name,user.password,user.email,user.address,user.contact)
                users.save(sponsor)
                return Optional.of(users.save(sponsor))
            }
            else -> {
                throw BadRequestException("Type not match any user entity.")
            }
        }
    }

}




