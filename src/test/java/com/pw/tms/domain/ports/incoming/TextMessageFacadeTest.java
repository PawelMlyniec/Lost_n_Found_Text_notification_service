package com.pw.tms.domain.ports.incoming;

import com.pw.tms.domain.TextMessage;
import com.pw.tms.infrastructure.util.Threads;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootTest
class TextMessageFacadeTest {

    @Autowired
    TextMessageFacade textMessageFacade;

    @Test
    void shouldSendMultipleTextMessages() throws InterruptedException, ExecutionException {

        var numThreads = 1;
        var executorService = Executors.newFixedThreadPool(numThreads);
        var tasks = new ArrayList<Future<?>>();

        for (int taskNo = 0; taskNo < numThreads; taskNo++) {
            Future<?> task = executorService.submit(() -> {
                for (long i = 0; i < 1_000_000; i++) {
                    var message = TextMessage.builder()
                        .withId("123")
                        .withSourceUserId(Long.toString(i))
                        .withTargetUserId("2")
                        .withContent("&".repeat(50 * 1024)) // 50 kB
                        .build();
                    textMessageFacade.sendTextMessage(message);
//                    Threads.sleep(0, 100_000);
                }
            });
            tasks.add(task);
        }
        for (var task : tasks) {
            task.get();
        }
    }
}