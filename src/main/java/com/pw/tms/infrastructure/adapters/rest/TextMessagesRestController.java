package com.pw.tms.infrastructure.adapters.rest;

import com.pw.tms.domain.ports.incoming.TextMessageFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/textMessages")
public class TextMessagesRestController {

    private final TextMessageFacade facade;

    @Autowired
    public TextMessagesRestController(TextMessageFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    TextMessageRest sendMessage(@RequestBody TextMessageRest message) {
        var sentMessage = facade.sendTextMessage(message.toDomain());
        return TextMessageRest.fromDomain(sentMessage);
    }
}
