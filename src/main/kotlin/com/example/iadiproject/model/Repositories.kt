package com.example.iadiproject.model

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ApplicationRepository : JpaRepository<ApplicationDAO, Long> {
    fun findApplicationDAOByGrantCallId(id: Long): List<ApplicationDAO>
    fun findApplicationDAOByStudentId(id: Long): List<ApplicationDAO>

}
interface InstitutionRepository : JpaRepository<InstitutionDAO, Long> {

}
interface StudentRepository: JpaRepository<StudentDAO, Long>{

}
interface SponsorRepository : JpaRepository<SponsorDAO, Long> {

}

interface ReviewerRepository: JpaRepository<ReviewerDAO, Long> {
}

interface GrantCallRepository : JpaRepository<GrantCallDAO, Long>{
    fun findGrantCallDAOBySponsorId(id: Long): List<GrantCallDAO>
}

interface EvaluationPanelRepository : JpaRepository<EvaluationPanelDAO, Long>{
    fun findEvaluationPanelDAOByGrantCallId(id: Long): EvaluationPanelDAO
}

interface ReviewRepository :  JpaRepository<ReviewDAO, Long>{

}

interface UserRepository : JpaRepository<UserDAO, Long>{
    fun findUserDAOByName(name: String): Optional<UserDAO>
}



interface AdminRepository: JpaRepository<AdminDAO, Long>{

}
