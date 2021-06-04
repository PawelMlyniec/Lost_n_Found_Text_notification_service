package com.pw.tms.domain.textmessage;

import com.pw.tms.domain.textmessage.TextMessage;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface TextMessageRepository extends CrudRepository<TextMessage, String>, QuerydslPredicateExecutor<TextMessage> {

}
