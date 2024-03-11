package com.gava.rinhav2.adapter.user.api

import com.gava.rinhav2.adapter.user.api.dto.TransactionResponseDto
import com.gava.rinhav2.adapter.user.api.dto.StatementResponseDto
import com.gava.rinhav2.adapter.user.api.dto.TransactionRequestDto
import com.gava.rinhav2.adapter.user.api.dto.toBalance
import com.gava.rinhav2.adapter.user.api.dto.toDto
import com.gava.rinhav2.adapter.user.api.dto.toModel
import com.gava.rinhav2.adapter.user.api.dto.toTransactionResponseDto
import com.gava.rinhav2.domain.user.service.UserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono


@RestController
class UserController(
    private val userService: UserService,
) {

    @PostMapping("clientes/{id}/transacoes")
    fun executeTransaction(
        @PathVariable("id") userId: Long,
        @RequestBody @Valid transactionRequest: TransactionRequestDto
    ): Mono<TransactionResponseDto> {
        val value = if (transactionRequest.tipo == "c") transactionRequest.valor else -transactionRequest.valor
        return userService.executeTransaction(
            userId, value.toLong(), transactionRequest.toModel(userId)
        ).map { it.toTransactionResponseDto() }
    }

    @GetMapping("clientes/{id}/extrato")
    fun getStatement(@PathVariable("id") userId: Long): Mono<StatementResponseDto> {
        val (userInfo, transactions)= userService.getStatement(userId)

        return userInfo.zipWith(transactions.toDto()) { user, transactionsDto ->
            StatementResponseDto(user.toBalance(), transactionsDto)
        }
    }
}