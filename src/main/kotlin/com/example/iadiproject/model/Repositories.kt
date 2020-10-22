package com.example.iadiproject.model

import com.example.iadiproject.services.*
import org.springframework.data.repository.CrudRepository

interface ApplicationRepository : CrudRepository<ApplicationDAO, Long> {

}
interface InstitutionRepository : CrudRepository<InstitutionDAO, Long> {

}
interface StudentRepository : CrudRepository<StudentDAO, Long> {

}
interface SponsorRepository : CrudRepository<SponsorDAO, Long> {

}

interface ReviewerRepository : CrudRepository<ReviewerDAO, Long>{

}

interface GrantCallRepository : CrudRepository<GrantCallDAO, Long>{

}

interface EvaluationPanelRepository : CrudRepository<EvaluationPanelDAO, Long>{

}