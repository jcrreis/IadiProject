package com.example.iadiproject.model


import java.util.*
import javax.persistence.*


@Entity
data class ApplicationDAO(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,
        var submissionDate: Date,
        var status: Int,
        var decision: Boolean,
        var justification: String,
        @ManyToOne(cascade = arrayOf(CascadeType.ALL), mappedBy = "applications")
        var grantCall: GrantCallDAO,
        @ManyToOne(cascade = arrayOf(CascadeType.ALL), mappedBy = "applications")
        var student: StudentDAO,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "application")
        var reviews: MutableList<ReviewDAO>,
        var meanScores: Double
) {
    constructor() : this(0, Date(), 1, true, "", GrantCallDAO(), StudentDAO(), mutableListOf(),0.0) {

    }

   fun updateMeanScores(){
       this.meanScores = this.reviews.map { it.score }.toList().average()
    }
}

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class UserDAO(
        @Id
        @GeneratedValue(strategy = GenerationType.TABLE)
        open var id: Long,
        @Column(nullable = false)
        open var name: String,
        @Column(nullable = false)
        open var password: String,
        //@Column(nullable = false,unique=true)
        open var email: String,
        open var address: String,
        @ManyToOne(cascade = arrayOf(CascadeType.ALL), mappedBy = "students")
        open var institution: InstitutionDAO,
        //@ManyToMany(fetch = FetchType.EAGER)
        //@JoinTable(name = "users_roles", joinColumns = arrayOf(JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = JoinColumn(name = "role_id", referencedColumnName = "id")))
        open var roles: String
){
    constructor() : this(0, "", "", "", "", InstitutionDAO(), "")

    fun changePassword(password: String){
        this.password = password
    }
}


@Entity
data class AdminDAO(
        override var id: Long,
        override var name: String,
        override var password: String,
        override var email: String,
        override var address: String,
        override var institution: InstitutionDAO

): UserDAO(id,name,password,email,address,institution,"ROLE_ADMIN")
{
    constructor(): this(0,"","","","",InstitutionDAO())
}



@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class EntityDAO(
        @Id
        @GeneratedValue(strategy = GenerationType.TABLE)
        open var id: Long,
        @Column(nullable = false)
        open var name: String,
        @Column(nullable = false)
        open var contact: String
){
    constructor() : this(0, "", "")
}

@Entity
data class StudentDAO(


        override var id: Long,
        override var name: String,
        override var password: String,
        override var email: String,
        override var address: String,
        override var institution: InstitutionDAO,
        @Lob
        var cv: String,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "student")
        var applications: MutableList<ApplicationDAO>

) : UserDAO(id, name, password, email, address, institution,"ROLE_STUDENT") {
    constructor() : this(0, "", "", "", "", InstitutionDAO(),"", mutableListOf()) {

    }
}

@Entity
data class ReviewerDAO(

        override var id: Long,
        override var name: String,
        override var password: String,
        override var email: String,
        override var address: String,
        override var institution: InstitutionDAO,
        @OneToMany(cascade = arrayOf(CascadeType.ALL))
        var panelsChairs: MutableList<EvaluationPanelDAO>,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "reviewer")
        var reviews: MutableList<ReviewDAO>

): UserDAO(id, name, password, email, address, institution, "ROLE_REVIEWER"){
    @ManyToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "reviewers")
    var evaluationPanels: MutableList<EvaluationPanelDAO> = mutableListOf()
    constructor() : this(0, "", "", "", "", InstitutionDAO(), mutableListOf(),mutableListOf()) {

    }
}

@Entity
data class InstitutionDAO(

        override var id: Long,
        override var name: String,
        override var contact: String,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "institution",fetch = FetchType.EAGER)
        var users: MutableList<UserDAO>

): EntityDAO(id, name, contact){
    constructor() : this(0, "", "", mutableListOf()) {

    }
}

@Entity
data class SponsorDAO(

        override var id: Long,
        override var name: String,
        override var contact: String

): EntityDAO(id, name, contact) {
    @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "sponsor")
    var grantCalls: MutableList<GrantCallDAO> = mutableListOf()
    constructor() : this(0, "", "") {

    }
}

@Entity
data class GrantCallDAO(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,
        var title: String,
        var description: String,
        var requirements: String,
        var funding: Double,
        var openingDate: Date,
        var closingDate: Date,
        var dataItems: String,
        @ManyToOne(fetch = FetchType.LAZY)
        var sponsor: SponsorDAO,
        @OneToOne(cascade = arrayOf(CascadeType.ALL), mappedBy = "grantCall")
        var evaluationPanel: EvaluationPanelDAO,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "grantCall")
        var applications: MutableList<ApplicationDAO>
) {
    constructor() : this(0, "", "", "", 0.0, Date(), Date(), "", SponsorDAO(), EvaluationPanelDAO(), mutableListOf()) {

    }
}


@Entity
data class EvaluationPanelDAO(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,
        @ManyToOne(cascade = arrayOf(CascadeType.ALL), optional = true)
        var panelchair: ReviewerDAO?,
        var winnerId: Long,
        @OneToOne(cascade = arrayOf(CascadeType.ALL), optional = true)
        var grantCall: GrantCallDAO?


){
    @ManyToMany(cascade = arrayOf(CascadeType.ALL))
    var reviewers: MutableList<ReviewerDAO> = mutableListOf()
    constructor() : this(0, null, -1, null) {

    }
}

@Entity
data class ReviewDAO(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,
        @ManyToOne(cascade = arrayOf(CascadeType.ALL), mappedBy = "reviews")
        var application: ApplicationDAO,
        @ManyToOne(cascade = arrayOf(CascadeType.ALL), mappedBy = "reviews")
        var reviewer: ReviewerDAO,
        var score: Int,
        var observations: String
){

    constructor() : this(0, ApplicationDAO(), ReviewerDAO(), 0, ""){

    }
}


data class DataItems(val value: String, val name: String)