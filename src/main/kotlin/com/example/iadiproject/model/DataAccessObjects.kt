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
        @ManyToOne(cascade = arrayOf(CascadeType.ALL))
        var grantCall: GrantCallDAO,
        @ManyToOne(cascade = arrayOf(CascadeType.ALL))
        var student: StudentDAO,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "application")
        var reviews: MutableList<ReviewDAO>,
        var meanScores: Double,
        @OneToMany(fetch = FetchType.LAZY,mappedBy="application")
        var dataItemAnswers: MutableList<DataItemAnswer>
) {
    constructor() : this(0, Date(), 1, true, "", GrantCallDAO(), StudentDAO(), mutableListOf(),0.0, mutableListOf()) {

    }

   fun updateMeanScores(){
       this.meanScores = this.reviews.map { it.score }.average()
    }
}

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class UserDAO(
        @Id
        @GeneratedValue(strategy = GenerationType.TABLE)
        open var id: Long,
        @Column(nullable = false, unique = true)
        open var name: String,
        @Column(nullable = false)
        open var password: String,
        @Column(nullable = false,unique=true)
        open var email: String,
        open var address: String,
        open var roles: String
){
    constructor() : this(0, "", "", "", "", "")

    fun changePassword(password: String){
        this.password = password
    }
}

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class RegularUserDAO(
        id: Long,
        name: String,
        password: String,
        email: String,
        address: String,
        @ManyToOne(cascade = arrayOf(CascadeType.ALL))
        open var institution: InstitutionDAO,
        roles: String
) : UserDAO(id,name,password,email,address,roles){
    constructor() : this(0, "", "", "", "", InstitutionDAO(), "")

}


interface EntityI{
        var id: Long
        var name: String
        var contact: String
}

@Entity
data class StudentDAO(


        var student_id: Long,
        var student_name: String,
        var student_password: String,
        var student_email: String,
        var student_address: String,
        @ManyToOne
        var student_institution: InstitutionDAO,
        @Lob
        var cv: ByteArray,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "student")
        var applications: MutableList<ApplicationDAO>

) : RegularUserDAO(student_id, student_name, student_password, student_email, student_address, student_institution,"ROLE_STUDENT") {
    constructor() : this(0, "", "", "", "", InstitutionDAO(),ByteArray(0), mutableListOf()) {

    }
}

@Entity
data class ReviewerDAO(

        var reviewer_id: Long,
        var reviewer_name: String,
        var reviewer_password: String,
        var reviewer_email: String,
        var reviewer_address: String,
        @ManyToOne
        var reviewer_institution: InstitutionDAO,
        @OneToMany(cascade = arrayOf(CascadeType.ALL))
        var panelsChairs: MutableList<EvaluationPanelDAO>,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "reviewer")
        var reviews: MutableList<ReviewDAO>

): RegularUserDAO(reviewer_id, reviewer_name, reviewer_password, reviewer_email, reviewer_address, reviewer_institution, "ROLE_REVIEWER"){
    @ManyToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "reviewers")
    var evaluationPanels: MutableList<EvaluationPanelDAO> = mutableListOf()
    constructor() : this(0, "", "", "", "", InstitutionDAO(), mutableListOf(),mutableListOf()) {

    }
}

@Entity
data class InstitutionDAO(
        @Id
        @GeneratedValue
        override var id: Long,
        @Column(nullable = false, unique = true)
        override var name: String,
        @Column(nullable = false)
        override var contact: String,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "institution")
        var users: MutableList<RegularUserDAO>

) : EntityI {
    constructor() : this(0, "", "", mutableListOf()) {

    }
}

@Entity
data class SponsorDAO(

        var sponsor_id: Long,
        var sponsor_name: String,
        var sponsor_password: String,
        var sponsor_email: String,
        var sponsor_address: String,
        override var contact: String
): UserDAO(sponsor_id,sponsor_name,sponsor_password,sponsor_email,sponsor_address,"ROLE_SPONSOR"),EntityI {

    @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "sponsor")
    var grantCalls: MutableList<GrantCallDAO> = mutableListOf()

    constructor() : this(0, "", "", "", "", "") {

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
        @OneToMany(fetch = FetchType.LAZY,mappedBy="grantCall")
        var dataItems: List<DataItem>,
        @ManyToOne(fetch = FetchType.LAZY)
        var sponsor: SponsorDAO,
        @OneToOne(cascade = arrayOf(CascadeType.ALL), mappedBy = "grantCall")
        var evaluationPanel: EvaluationPanelDAO,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "grantCall")
        var applications: MutableList<ApplicationDAO>
) {
    constructor() : this(0, "", "", "", 0.0, Date(), Date(), mutableListOf(),SponsorDAO(), EvaluationPanelDAO(), mutableListOf()) {

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
        @ManyToOne(cascade = arrayOf(CascadeType.ALL))
        var application: ApplicationDAO,
        @ManyToOne(cascade = arrayOf(CascadeType.ALL))
        var reviewer: ReviewerDAO,
        var score: Int,
        var observations: String
){

    constructor() : this(0, ApplicationDAO(), ReviewerDAO(), 0, ""){

    }
}

@Entity
data class DataItem(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,
        var mandatory: Boolean,
        var name: String,
        var datatype: String,
        @ManyToOne(cascade = arrayOf(CascadeType.ALL),fetch = FetchType.LAZY)
        var grantCall: GrantCallDAO,
        @OneToMany(fetch = FetchType.LAZY)
        var answers: List<DataItemAnswer>

){
    constructor() : this(0,true,"","", GrantCallDAO(), mutableListOf()){

    }
}

@Entity
data class DataItemAnswer(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,
        @ManyToOne(cascade = arrayOf(CascadeType.ALL),fetch = FetchType.LAZY)
        var dataItem: DataItem,
        @ManyToOne(cascade = arrayOf(CascadeType.ALL),fetch = FetchType.LAZY)
        var application: ApplicationDAO,
        var value: String
        ){
    constructor() : this(0,DataItem(),ApplicationDAO(),""){

    }
}