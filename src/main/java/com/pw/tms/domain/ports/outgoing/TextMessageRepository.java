package com.pw.tms.domain.ports.outgoing;

import com.pw.tms.domain.TextMessage;
import org.springframework.data.repository.CrudRepository;

public interface TextMessageRepository extends CrudRepository<TextMessage, String> {

}
