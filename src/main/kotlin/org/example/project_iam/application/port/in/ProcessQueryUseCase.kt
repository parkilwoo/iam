package org.example.project_iam.application.port.`in`

import org.example.project_iam.domain.model.Answer
import reactor.core.publisher.Mono

interface ProcessQueryUseCase {
    fun processQuery(content: String): Mono<Answer>
}