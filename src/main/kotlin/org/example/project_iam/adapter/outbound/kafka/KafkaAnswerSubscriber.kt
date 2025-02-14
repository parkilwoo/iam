package org.example.project_iam.adapter.outbound.kafka

import org.example.project_iam.application.service.QueryResponseBroker
import org.example.project_iam.domain.model.Answer
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaAnswerSubscriber(
    private val queryResponseBroker: QueryResponseBroker
) {
    @KafkaListener(topics = ["\${kafka.topic.answer}"], groupId = "\${kafka.group.id}")
    fun listen(answerMessage: AnswerMessage) {
        // 전송 DTO -> Domain 변환
        val answer = Answer(answerMessage.relationId, answerMessage.content)
        queryResponseBroker.complete(answerMessage.relationId, answer)
    }
}