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


        override var id: Long,
        override var name: String,
        override var password: String,
        override var email: String,
        override var address: String,
        @ManyToOne
        override var institution: InstitutionDAO,
        @Lob
        var cv: ByteArray,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "student")
        var applications: MutableList<ApplicationDAO>

) : RegularUserDAO(id, name, password, email, address, institution,"ROLE_STUDENT") {
    constructor() : this(0, "", "", "", "", InstitutionDAO(),ByteArray(0), mutableListOf()) {

    }
}

@Entity
data class ReviewerDAO(

        override var id: Long,
        override var name: String,
        override var password: String,
        override var email: String,
        override var address: String,
        @ManyToOne
        override var institution: InstitutionDAO,
        @OneToMany(cascade = arrayOf(CascadeType.ALL))
        var panelsChairs: MutableList<EvaluationPanelDAO>,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "reviewer")
        var reviews: MutableList<ReviewDAO>

): RegularUserDAO(id, name, password, email, address, institution, "ROLE_REVIEWER"){
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

        override var id: Long,
        override var name: String,
        override var password: String,
        override var email: String,
        override var address: String,
        override var contact: String
): UserDAO(id,name,password,email,address,"ROLE_SPONSOR"),EntityI {

    @OneToMany(cascade = arrayOf(CascadeType.ALL),fetch = FetchType.LAZY, mappedBy = "sponsor")
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
        @OneToMany(cascade = arrayOf(CascadeType.REMOVE),fetch = FetchType.LAZY,mappedBy="grantCall")
        var dataItems: List<DataItem>,
        @ManyToOne
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
        @OneToMany(cascade = arrayOf(CascadeType.REMOVE),fetch = FetchType.LAZY)
        var answers: List<DataItemAnswer>

){
    @ManyToOne(cascade = arrayOf(CascadeType.ALL),fetch = FetchType.LAZY)
    lateinit var grantCall: GrantCallDAO
    constructor() : this(0,true,"","",mutableListOf()){

    }
}

@Entity
data class DataItemAnswer(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,
        @ManyToOne(cascade = arrayOf(CascadeType.ALL),fetch = FetchType.LAZY)
        var dataItem: DataItem,
        var value: String
        ){
    @ManyToOne(cascade = arrayOf(CascadeType.ALL),fetch = FetchType.LAZY)
    lateinit var application: ApplicationDAO
    constructor() : this(0,DataItem(),""){

    }
}

/*
@Entity
data class CurriculumDAO(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,
        @ManyToOne(cascade = arrayOf(CascadeType.ALL),fetch = FetchType.LAZY)
        var dataItem: DataItem,

        var value: String
){
    @ManyToOne(cascade = arrayOf(CascadeType.ALL),fetch = FetchType.LAZY)
    lateinit var application: ApplicationDAO
    constructor() : this(0,DataItem(),""){

    }
}*/