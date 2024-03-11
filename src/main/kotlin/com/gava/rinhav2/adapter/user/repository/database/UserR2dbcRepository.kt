package com.gava.rinhav2.adapter.user.repository.database

import com.gava.rinhav2.domain.user.model.User
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface UserR2dbcRepository : R2dbcRepository<User, Long> {
    @Query("""
        UPDATE users SET balance = (:value + users.balance) 
        WHERE id = :id AND (:value + users.balance) > (-account_limit) RETURNING *
    """)
    fun updateBalance(id: Long, value: Long): Mono<User>
}