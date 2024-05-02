package com.sorsix.intern.backend.domain

import jakarta.persistence.*

@Entity
@Table(name = "app_user")
@Inheritance(strategy = InheritanceType.JOINED)

open class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var number: String? = null,
    var name: String? = null,
    var address: String? = null,
    var email: String? = null,
    var imgUser: String? = null
)