package com.gava.rinhav2.domain.user.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("users")
data class User(
    @Id
    val id: Int,
    val accountLimit: Long,
    val balance: Long
)