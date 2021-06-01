package com.pw.tms.domain.ports.outgoing;

import com.pw.tms.domain.TextMessage;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface TextMessageRepository extends CrudRepository<TextMessage, String>, QuerydslPredicateExecutor<TextMessage> {

}

//public interface TextMessageRepository extends JpaRepository<TextMessage, String> {
//
//    List<TextMessage> findByTargetUserId(String targetUserId);
//
//    List<TextMessage> findBySourceUserId(String sourceUserId);
//
//    List<TextMessage> findBySourceUserIdAndTargetUserId(String sourceUserId, String targetUserId);
//
//    Long countByIsReadAndTargetUserId(boolean isRead, String targetUserId);
//}
