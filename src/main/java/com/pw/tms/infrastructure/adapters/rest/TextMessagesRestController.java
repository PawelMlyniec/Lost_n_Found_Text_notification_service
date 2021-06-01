package com.pw.tms.infrastructure.adapters.rest;

import com.pw.tms.domain.TextMessage;
import com.pw.tms.domain.ports.incoming.TextMessageFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/{id}")
    public Long getUnreadMessagesCountForTargetUserId(@PathVariable String id) {
        return facade.getUnreadMessagesCountForTargetUserId(id);
    }

    @GetMapping("/{id}/chats")
    public List<TextMessageRest> getAllChatsForUserId(@PathVariable String id) {

        var foundMessagesList = facade.getAllChatsForUserId(id);
        var foundMessagesRestList = new ArrayList<TextMessageRest>();

        for(TextMessage m : foundMessagesList) {
            foundMessagesRestList.add(TextMessageRest.fromDomain(m));
        }

        return foundMessagesRestList;
    }

    @GetMapping("/{firstUserId}/{secondUserId}")
    public List<TextMessageRest> getAllMessagesBetweenUsers(@PathVariable String firstUserId, @PathVariable String secondUserId) {

        var foundMessagesList = facade.getAllMessagesBetweenUsers(firstUserId, secondUserId);
        var foundMessagesRestList = new ArrayList<TextMessageRest>();

        for(TextMessage m : foundMessagesList) {
            foundMessagesRestList.add(TextMessageRest.fromDomain(m));
        }

        return foundMessagesRestList;
    }

}
