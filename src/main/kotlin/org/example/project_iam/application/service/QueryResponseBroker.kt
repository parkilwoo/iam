package org.example.project_iam.application.service

import org.example.project_iam.domain.model.Answer
import org.springframework.stereotype.Component
import reactor.core.publisher.Sinks
import java.util.concurrent.ConcurrentHashMap

@Component
class QueryResponseBroker {

    private val sinkMap = ConcurrentHashMap<String, Sinks.One<Answer>>()

    fun registerSink(relationId: String, sink: Sinks.One<Answer>) {
        sinkMap[relationId] = sink
    }

    fun complete(relationId: String, answer: Answer) {
        sinkMap.remove(relationId)?.tryEmitValue(answer)
    }

    fun removeSink(relationId: String) {
        sinkMap.remove(relationId)
    }
}