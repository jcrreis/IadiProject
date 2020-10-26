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
        var reviews: MutableList<ReviewDAO>
) {
    constructor() : this(0, Date(), 1, true, "", GrantCallDAO(), StudentDAO(), mutableListOf()) {

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
        @ManyToOne(cascade = arrayOf(CascadeType.ALL), mappedBy = "students")
        var institution: InstitutionDAO,
        @ManyToMany
        @JoinTable(name = "users_roles", joinColumns = arrayOf(JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = JoinColumn(name = "role_id", referencedColumnName = "id")))
        var roles: MutableList<Role>
){
    constructor() : this(0, "", "", "", "", InstitutionDAO(), mutableListOf())

    fun changePassword(password: String){
        this.password = password
        return
    }
}

@Entity
data class Role(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long,
        var name: String,
        @ManyToMany(mappedBy = "roles")
        var users: MutableList<UserDAO>,
        @ManyToMany()
        @JoinTable(name = "roles_privileges", joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")], inverseJoinColumns = [JoinColumn(name = "privilege_id", referencedColumnName = "id")])
        var privileges: MutableList<Privilege>
){

    constructor() : this(0,"", mutableListOf(), mutableListOf())
}

@Entity
data class Privilege(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long,
    var name: String,
    @ManyToMany(mappedBy = "privileges")
    var roles: MutableList<Role>
){
    constructor() : this(0, "", mutableListOf())
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
        override var roles: MutableList<Role>,
        @Lob
        var cv: String,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "student")
        var applications: MutableList<ApplicationDAO>

) : UserDAO(id, name, password, email, address, institution,roles) {
    constructor() : this(0, "", "", "", "", InstitutionDAO(), mutableListOf(),"", mutableListOf()) {

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
        override var roles: MutableList<Role>,
        @OneToMany(cascade = arrayOf(CascadeType.ALL))
        var panelsChairs: MutableList<EvaluationPanelDAO>,
        @OneToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "reviewer")
        var reviews: MutableList<ReviewDAO>

): UserDAO(id, name, password, email, address, institution, roles){
    @ManyToMany(cascade = arrayOf(CascadeType.ALL), mappedBy = "reviewers")
    var evaluationPanels: MutableList<EvaluationPanelDAO> = mutableListOf()
    constructor() : this(0, "", "", "", "", InstitutionDAO(), mutableListOf(), mutableListOf(),mutableListOf()) {

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
