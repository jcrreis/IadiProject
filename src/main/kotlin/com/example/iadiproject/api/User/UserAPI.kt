package com.example.iadiproject.api.User



import com.example.iadiproject.api.AddSponsorDTO
import com.example.iadiproject.api.UserDTO
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus

import org.springframework.web.bind.annotation.*

@Api(value="users", description = "'User' management operations")
@RequestMapping("/users")
interface UserAPI {


    @ApiOperation("Get the list of all users")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved list of users"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN")
    ])
    @GetMapping("")
    fun getAll(): List<UserDTO>


    @ApiOperation("Get current user")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "Successfully retrieved the logged user"),
        ApiResponse(code = 401, message = "UNAUTHORIZED"),
        ApiResponse(code = 403, message = "FORBIDDEN")
    ])
    @GetMapping("/current")
    fun getLoggedUser(): UserDTO?

    @PutMapping("/changepassword")
    fun changePassword(password: String){
    }

   // @PostMapping("")
    //@ResponseStatus(HttpStatus.CREATED)
   // fun signUp(@RequestBody sponsor: AddSponsorDTO)

   // @PostMapping("")
   // @ResponseStatus(HttpStatus.CREATED)
   // fun login(@RequestBody sponsor: AddSponsorDTO)

}