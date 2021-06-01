package com.pw.tms.domain.ports.outgoing;

import com.pw.tms.domain.TextMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TextMessageRepository extends JpaRepository<TextMessage, String> {

    List<TextMessage> findByTargetUserId(String targetUserId);

    List<TextMessage> findBySourceUserId(String sourceUserId);

    List<TextMessage> findBySourceUserIdAndTargetUserId(String sourceUserId, String targetUserId);

    Long countByIsReadAndTargetUserId(boolean isRead, String targetUserId);
}
