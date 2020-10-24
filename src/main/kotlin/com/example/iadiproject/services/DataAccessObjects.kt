package com.example.iadiproject.services

import com.example.iadiproject.api.*
import java.util.*
import javax.persistence.*
import com.fasterxml.jackson.annotation.*
import org.hibernate.annotations.NotFound
import org.springframework.web.multipart.MultipartFile
import java.io.File
import kotlin.contracts.contract

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
        @OneToMany(cascade = arrayOf(CascadeType.ALL),fetch = FetchType.LAZY,mappedBy="application")
        var reviews: MutableList<ReviewDAO>
) {
    constructor() : this(0,Date(),1,true,"",GrantCallDAO(), StudentDAO(), mutableListOf()) {

    }
}
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class UserDAO(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long,
        @Column(nullable = false)
        var name: String,
        @Column(nullable = false)
        var password: String,
        //@Column(nullable = false,unique=true)
        var email: String,
        var address: String,
        @ManyToOne(cascade = arrayOf(CascadeType.ALL), mappedBy = "students", fetch = FetchType.LAZY)
        var institution: InstitutionDAO
){
    constructor() : this(0,"","","","",InstitutionDAO())
}

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class EntityDAO(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long,
        @Column(nullable = false)
        var name: String,
        @Column(nullable = false)
        var contact: String
){
    constructor() : this(0,"","")
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
        var cv: MultipartFile?,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "student")
        var applications: MutableList<ApplicationDAO>

) : UserDAO(id,name,password,email,address,institution) {
    constructor() : this(0,"","","","",InstitutionDAO(), null, mutableListOf()) {

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
        @OneToMany(cascade = arrayOf(CascadeType.ALL),fetch = FetchType.LAZY)
        var panelsChairs: MutableList<EvaluationPanelDAO>,
        @OneToMany(cascade = arrayOf(CascadeType.ALL),fetch = FetchType.LAZY,mappedBy="reviewer")
        var reviews: MutableList<ReviewDAO>

): UserDAO(id,name,password,email,address,institution){
    @ManyToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "reviewers", fetch = FetchType.LAZY)
    var evaluationPanels: MutableList<EvaluationPanelDAO> = mutableListOf()
    constructor() : this(0,"","","","",InstitutionDAO(),mutableListOf(),mutableListOf()) {

    }
}

@Entity
data class InstitutionDAO(

        override var id: Long,
        override var name: String,
        override var contact: String,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "institution", fetch = FetchType.LAZY)
        var users: MutableList<UserDAO>

): EntityDAO(id,name,contact){
    constructor() : this(0,"","", mutableListOf()) {

    }
}

@Entity
data class SponsorDAO(

        override var id: Long,
        override var name: String,
        override var contact: String

): EntityDAO(id,name,contact) {
    @OneToMany(cascade = arrayOf(CascadeType.ALL),fetch = FetchType.LAZY,mappedBy="sponsor")
    var grantCalls: MutableList<GrantCallDAO> = mutableListOf()
    constructor() : this(0,"","") {

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
        @OneToOne(cascade = arrayOf(CascadeType.ALL), mappedBy = "grantCall", fetch = FetchType.LAZY)
        var evaluationPanel: EvaluationPanelDAO,
        @OneToMany(cascade = arrayOf(CascadeType.ALL),mappedBy="grantCall")
        var applications: MutableList<ApplicationDAO>
) {
    constructor() : this(0,"","","",0.0,Date(),Date(),"", SponsorDAO(),EvaluationPanelDAO(), mutableListOf()) {

    }
}


@Entity
data class EvaluationPanelDAO(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,
        @ManyToOne(cascade = arrayOf(CascadeType.ALL),fetch = FetchType.LAZY,optional = true)
        var panelchair: ReviewerDAO?,
        var winnerId: Long,
        @OneToOne(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY,optional = true)
        var grantCall: GrantCallDAO?



){
    @ManyToMany(cascade = arrayOf(CascadeType.ALL),  fetch = FetchType.LAZY)
    var reviewers: MutableList<ReviewerDAO> = mutableListOf()
    constructor() : this(0,null,-1, null) {

    }
}

@Entity
data class ReviewDAO(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    @ManyToOne(cascade = arrayOf(CascadeType.ALL),mappedBy="reviews")
    var application: ApplicationDAO,
    @ManyToOne(cascade = arrayOf(CascadeType.ALL),mappedBy="reviews")
    var reviewer: ReviewerDAO,
    var score: Int,
    var observations: String
){

    constructor() : this(0,ApplicationDAO(),ReviewerDAO(),0,""){

    }
}
