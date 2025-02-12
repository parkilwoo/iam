package org.example.project_iam.adapter.outbound.kafka

import org.example.project_iam.application.port.out.QueryPublisher
import org.example.project_iam.domain.model.Query
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaQueryPublisher (
    private val kafkaTemplate: KafkaTemplate<String, QueryMessage>,
    @Value("\${kafka.topic.query}") private val topic: String
) : QueryPublisher {
    override fun publish(query: Query) {
        val message = QueryMessage(query.relationId, query.content)
        kafkaTemplate.send(topic, message.relationId, message)
    }
}