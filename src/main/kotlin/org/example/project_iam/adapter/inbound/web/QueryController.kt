package org.example.project_iam.adapter.inbound.web

import org.example.project_iam.application.port.`in`.ProcessQueryUseCase
import org.example.project_iam.domain.model.Answer
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/query")
class QueryController(
    private val processQueryUseCase: ProcessQueryUseCase
) {

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun answerQuery(@RequestBody request: QueryRequest): Mono<AnswerResponse> {
        return processQueryUseCase.processQuery(request.query)
            .map { answer: Answer -> AnswerResponse(answer.content) }
    }
}