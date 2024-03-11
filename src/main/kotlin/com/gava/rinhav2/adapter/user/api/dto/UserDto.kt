package com.gava.rinhav2.adapter.user.api.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.gava.rinhav2.domain.user.model.Transaction
import com.gava.rinhav2.domain.user.model.User
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.LocalDateTime
import kotlin.math.absoluteValue


data class TransactionRequestDto(
    @field:NotNull
    @field:Min(value = 0)
    val valor: Int,
    @field:NotNull
    @field:NotEmpty
    @field:Pattern(regexp = "[c|d]")
    val tipo:  String,
    @field:NotNull
    @field:NotEmpty
    @field:Size(max = 10)
    @field:NotBlank
    val descricao:  String
)

data class TransactionResponseDto(
    val limite: Long,
    val saldo: Long
)

data class StatementResponseDto(
    val saldo: Balance,
    val ultimas_transacoes: List<TransactionDto>
)

data class Balance(
    val total: Long,
    val limite: Long,
    @field:JsonProperty("data_extrato")
    @param:JsonProperty("data_extrato")
    val dataExtrato: LocalDateTime
)

data class TransactionDto(
    val valor: Long,
    val tipo: String,
    val descricao: String,
    val realizada_em: LocalDateTime
)


fun TransactionRequestDto.toModel(userId: Long): Transaction {
    return Transaction(
        userId = userId,
        value = this.valor.toLong(),
        createdAt = LocalDateTime.now(),
        description = this.descricao,
        type = this.tipo
    )
}

fun Flux<Transaction>.toDto(): Mono<MutableList<TransactionDto>> {
    val transactions = this.map { transaction ->
        TransactionDto(
            transaction.value,
            transaction.type,
            transaction.description,
            transaction.createdAt
        )
    }
    return transactions.collectList()
}

fun User.toBalance(): Balance {
    return Balance(
        total = this.balance,
        limite = this.accountLimit,
        dataExtrato = LocalDateTime.now()
    )
}

fun User.toTransactionResponseDto(): TransactionResponseDto {
    return TransactionResponseDto(
        limite = this.accountLimit,
        saldo = this.balance
    )
}