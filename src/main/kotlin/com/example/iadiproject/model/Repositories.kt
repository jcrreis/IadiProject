package com.example.iadiproject.model

import com.example.iadiproject.services.*
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

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
    fun findReviewerDAOByAddress(address: String)
}

interface GrantCallRepository : JpaRepository<GrantCallDAO, Long>{
    fun findGrantCallDAOBySponsorId(id: Long)
}

interface EvaluationPanelRepository : JpaRepository<EvaluationPanelDAO, Long>{
    fun findEvaluationPanelDAOByGrantCallId(id: Long): EvaluationPanelDAO
}

interface ReviewRepository :  JpaRepository<ReviewDAO, Long>{

}
