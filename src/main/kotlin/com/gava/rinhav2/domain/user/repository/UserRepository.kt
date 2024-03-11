package com.gava.rinhav2.domain.user.repository

import com.gava.rinhav2.domain.user.model.Transaction
import com.gava.rinhav2.domain.user.model.User
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface UserRepository {
    fun updateBalance(id: Long, value: Long): Mono<User>
    fun findTransactionsByUserId(id: Long): Flux<Transaction>
    fun saveTransaction(transaction: Transaction): Mono<Transaction>
    fun findUserById(id: Long): Mono<User>
}