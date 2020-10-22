package com.example.iadiproject.services

import com.example.iadiproject.api.*
import java.util.*
import javax.persistence.*
import com.fasterxml.jackson.annotation.*
import org.hibernate.annotations.NotFound

@Entity
data class ApplicationDAO(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,
        var submissionDate: Date,
        var status: Int,
        var decision: Boolean,
        var justification: String
) {
    constructor() : this(0,Date(),1,true,"") {

    }
}
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract class UserDAO(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
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
data class StudentDAO(
        override var id: Long,
        override var name: String,
        override var password: String,
        override var email: String,
        override var address: String,
        override var institution: InstitutionDAO,
        var cv: String

) : UserDAO(id,name,password,email,address,institution) {
    constructor() : this(0,"","","","",InstitutionDAO(),"") {

    }
}

@Entity
data class ReviewerDAO(
        override var id: Long,
        override var name: String,
        override var password: String,
        override var email: String,
        override var address: String,
        override var institution: InstitutionDAO


): UserDAO(id,name,password,email,address,institution){
    @ManyToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "reviewers", fetch = FetchType.LAZY)
    var evaluationPanels: MutableList<EvaluationPanelDAO> = mutableListOf()
    constructor() : this(0,"","","","",InstitutionDAO()) {

    }
}

@Entity
data class InstitutionDAO(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,
        @Column(nullable = false)
        var name: String,
        @Column(nullable = false)
        var contact: String,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "institution", fetch = FetchType.LAZY)
        var users: MutableList<UserDAO>

) {
    constructor() : this(0,"","", mutableListOf()) {

    }
}

@Entity
data class SponsorDAO(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,
        var name: String,
        var contact: String

) {
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
        var evaluationPanel: EvaluationPanelDAO
) {
    constructor() : this(0,"","","",0.0,Date(),Date(),"", SponsorDAO(),EvaluationPanelDAO()) {

    }
}


@Entity
data class EvaluationPanelDAO(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long,
        //@ManyToOne(cascade = arrayOf(CascadeType.ALL),fetch = FetchType.LAZY,optional = true)
        //var panelchair: ReviewerDAO?,
        var winnerId: Long,
        @OneToOne(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY,optional = true)
        var grantCall: GrantCallDAO?



){
    @ManyToMany(cascade = arrayOf(CascadeType.ALL),  fetch = FetchType.LAZY)
    var reviewers: MutableList<ReviewerDAO> = mutableListOf()
    constructor() : this(0,-1, null) {

    }
}

