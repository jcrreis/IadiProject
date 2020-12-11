package com.example.iadiproject.securityconfig


import com.example.iadiproject.api.AddUserDTO
import com.example.iadiproject.api.UserSignInDTO
import com.example.iadiproject.services.UnauthorizedException
import com.example.iadiproject.services.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwt
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.impl.DefaultClaims
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean
import java.lang.Exception
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import kotlin.collections.HashMap
import kotlin.collections.LinkedHashMap

object JWTSecret {
    private const val passphrase = "este é um grande segredo que tem que ser mantido escondido"
    val KEY: String = Base64.getEncoder().encodeToString(passphrase.toByteArray())
    const val SUBJECT = "JSON Web Token for CIAI 2019/20"
    const val VALIDITY = 1000 * 60 * 10 // 10 minutes in miliseconds
}

private fun addResponseToken(authentication: Authentication, response: HttpServletResponse) {

    val claims = HashMap<String, Any?>()
    claims["username"] = authentication.name
    claims["authorities"] = authentication.authorities

    val token = Jwts
            .builder()
            .setClaims(claims)
            .setSubject(JWTSecret.SUBJECT)
            .setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + JWTSecret.VALIDITY))
            .signWith(SignatureAlgorithm.HS256, JWTSecret.KEY)
            .compact()

    response.addHeader("Authorization", "Bearer $token")
}

class UserPasswordAuthenticationFilterToJWT (
        defaultFilterProcessesUrl: String?,
        private val anAuthenticationManager: AuthenticationManager
) : AbstractAuthenticationProcessingFilter(defaultFilterProcessesUrl) {

    override fun attemptAuthentication(request: HttpServletRequest?,
                                       response: HttpServletResponse?): Authentication? {
        //getting user from request body
        val user = ObjectMapper().readValue(request!!.inputStream, UserSignInDTO::class.java)

        // perform the "normal" authentication
        val auth = anAuthenticationManager.authenticate(UsernamePasswordAuthenticationToken(user.name, user.password))

        return if (auth.isAuthenticated) {
            // Proceed with an authenticated user
            SecurityContextHolder.getContext().authentication = auth
            auth
        } else
            null
    }

    override fun successfulAuthentication(request: HttpServletRequest,
                                          response: HttpServletResponse,
                                          filterChain: FilterChain?,
                                          auth: Authentication) {

        // When returning from the Filter loop, add the token to the response
        addResponseToken(auth, response)
    }
}

class UserAuthToken(private var login:String, private var authorities: MutableCollection<out GrantedAuthority>?) : Authentication {

    override fun getAuthorities() = authorities

    override fun setAuthenticated(isAuthenticated: Boolean) {}

    override fun getName() = login

    override fun getCredentials() = null

    override fun getPrincipal() = this

    override fun isAuthenticated() = true

    override fun getDetails() = login
}

class JWTAuthenticationFilter: GenericFilterBean() {

    // To try it out, go to https://jwt.io to generate custom tokens, in this case we only need a name...

    override fun doFilter(request: ServletRequest?,
                          response: ServletResponse?,
                          chain: FilterChain?) {

        val authHeader = (request as HttpServletRequest).getHeader("Authorization")

        if( authHeader != null && authHeader.startsWith("Bearer ") ) {//.body
            val token = authHeader.substring(7) // Skip 7 characters for "Bearer "
            var claims: Claims
            try {
                claims = Jwts.parser().setSigningKey(JWTSecret.KEY).parseClaimsJws(token).body
            } catch (e: Exception) {
                throw UnauthorizedException("Invalid JWT")
            }


            // should check for token validity here (e.g. expiration date, session in db, etc.)
            val exp = (claims["exp"] as Int).toLong()
            if ( exp < System.currentTimeMillis()/1000) // in seconds

                (response as HttpServletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED) // RFC 6750 3.1

            else {
                val authoritiesArray: ArrayList<*> = claims["authorities"] as ArrayList<*>
                val authoritiesHashMap = authoritiesArray[0] as LinkedHashMap<*,*>
                val authorities = mutableListOf<GrantedAuthority>()
                authorities.add(SimpleGrantedAuthority(authoritiesHashMap["authority"] as String))
                val authentication = UserAuthToken(claims["username"] as String,authorities)
                // Can go to the database to get the actual user information (e.g. authorities)

                SecurityContextHolder.getContext().authentication = authentication

                // Renew token with extended time here. (before doFilter)
                addResponseToken(authentication, response as HttpServletResponse)

                chain!!.doFilter(request, response)
            }
        } else {
            chain!!.doFilter(request, response)
        }
    }
}

class UserPasswordSignUpFilterToJWT (
        defaultFilterProcessesUrl: String?,
        private val users: UserService
) : AbstractAuthenticationProcessingFilter(defaultFilterProcessesUrl) {

    override fun attemptAuthentication(request: HttpServletRequest?,
                                       response: HttpServletResponse?): Authentication? {
        //getting user from request body
        val user = ObjectMapper().readValue(request!!.inputStream, AddUserDTO::class.java)
        users.verifyIfValuesAreUnique(user.name,user.email)
        var authority: String
        authority = when(user.type) {
            "Student" -> "ROLE_STUDENT"
            "Reviewer" -> "ROLE_REVIEWER"
            else -> { // if not reviewer nor student is a sponsor then
                "ROLE_SPONSOR"
            }
        }
        val authorities = mutableListOf<GrantedAuthority>()
        authorities.add(SimpleGrantedAuthority(authority))
        return users
                .addUser(user)
                .let {
                    val auth = UserAuthToken(user.name,authorities)
                    SecurityContextHolder.getContext().authentication = auth
                    auth
                }
    }

    override fun successfulAuthentication(request: HttpServletRequest,
                                          response: HttpServletResponse,
                                          filterChain: FilterChain?,
                                          auth: Authentication) {

        addResponseToken(auth, response)
    }
}
