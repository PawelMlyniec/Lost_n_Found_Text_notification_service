package com.pw.tms.domain.ports.outgoing;

import com.pw.tms.domain.TextMessage;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.repository.CrudRepository;

public interface TextMessageRepository extends CrudRepository<TextMessage, String> {

}

//public interface TextMessageRepository extends JpaRepository<TextMessage, String> {
//
//}
