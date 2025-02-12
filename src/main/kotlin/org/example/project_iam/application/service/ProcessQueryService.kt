package org.example.project_iam.application.service

import org.example.project_iam.application.port.`in`.ProcessQueryUseCase
import org.example.project_iam.application.port.out.QueryPublisher
import org.example.project_iam.domain.model.Answer
import org.example.project_iam.domain.model.Query
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.Sinks
import java.time.Duration
import java.util.UUID

@Service
class ProcessQueryService(
    private val queryPublisher: QueryPublisher,
    private val queryResponseBroker: QueryResponseBroker
) : ProcessQueryUseCase {

    override fun processQuery(content: String): Mono<Answer> {
        // 1. Create Relation ID
        val relationId = UUID.randomUUID().toString()
        val query = Query(relationId, content)

        // 2. Create Sink and register
        val sink = Sinks.one<Answer>()
        queryResponseBroker.registerSink(relationId, sink)

        // 3. Send to kafka
        queryPublisher.publish(query)

        // 4. Return Sink
        return sink.asMono()
            .timeout(Duration.ofSeconds(10), Mono.error(IllegalStateException("Query Timeout")))
            .doOnError { queryResponseBroker.removeSink(relationId) }
    }
}