import com.example.iadiproject.model.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.bind.Bindable.listOf
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
@Service
class SetupDataLoader(
        @Autowired
        val userRepository: UserRepository,
        @Autowired
        val roleRepository: RoleRepository,
        @Autowired
        val privilegeRepository: PrivilegeRepository,
        @Autowired
        val passwordEncoder: PasswordEncoder

): ApplicationListener<ContextRefreshedEvent> {
    internal var alreadySetup = false


    @Transactional
    override fun onApplicationEvent(event:ContextRefreshedEvent) {
        if (alreadySetup)
            return
        val readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE")
        val writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE")
        val adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege)
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges)
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege))
        val adminRole = roleRepository.findRoleByName("ROLE_ADMIN").orElse(null)
        val user = StudentDAO()
        user.name = "Test"
        user.address = "Rua xxx"
        user.email = "ajaj@gmail.com"
        user.password = "123"
        user.institution = InstitutionDAO(0,"","", mutableListOf())
        user.roles = Arrays.asList(adminRole)
        userRepository.save(user)
        alreadySetup = true
    }
    @Transactional
    internal fun createPrivilegeIfNotFound(name:String):Privilege {
        var privilege = privilegeRepository.findPrivilegeByName(name).orElse(null)
        if (privilege == null)
        {
            privilege = Privilege(0,name, mutableListOf())
            privilegeRepository.save(privilege)
        }
        return privilege
    }
    @Transactional
    internal fun createRoleIfNotFound(
            name:String, privileges:MutableList<Privilege>):Role {
        var role: Role = roleRepository.findRoleByName(name).orElse(null)

        if (role == null)
        {
            role = Role(0,name, mutableListOf(), privileges)
            roleRepository.save(role)
        }
        return role
    }
}