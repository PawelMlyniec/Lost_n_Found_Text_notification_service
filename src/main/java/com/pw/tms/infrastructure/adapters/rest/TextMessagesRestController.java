package com.pw.tms.infrastructure.adapters.rest;

import com.pw.tms.domain.TextMessage;
import com.pw.tms.domain.TextMessageId;
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
    public Integer getUnreadMessagesCountForTargetUserId(@PathVariable String id) {

        var unreadMessagesCount = 0;
        var foundMessagesList = facade.getAllMessagesForTargetUserId(TextMessageId.of(id)); // TODO: nie TextMessageId.of(id) tylko string

        for(TextMessage m : foundMessagesList) {
            if(!m.isRead()) {
                unreadMessagesCount++;
            }
        }

        return unreadMessagesCount;
    }

    @GetMapping("/{id}/messages")
    public List<TextMessageRest> getAllMessagesForTargetUserId(@PathVariable String id) {

        var foundMessagesList = facade.getAllMessagesForTargetUserId(TextMessageId.of(id));
        var foundMessagesRestList = new ArrayList<TextMessageRest>();

        for(TextMessage m : foundMessagesList) {
            foundMessagesRestList.add(TextMessageRest.fromDomain(m));
        }

        return foundMessagesRestList;
    }

    @GetMapping("/{id}/chats")
    public List<TextMessageRest> getNewestMessagesForTargetUserId(@PathVariable String id) {

        var foundMessagesList = facade.getAllMessagesForTargetUserId(TextMessageId.of(id));
        var foundMessagesRestList = new ArrayList<TextMessageRest>();
        var sourceUsersIds = new ArrayList<String>();

        for(TextMessage m : foundMessagesList) {
            if(!sourceUsersIds.contains(m.sourceUserId())) {
                sourceUsersIds.add(m.sourceUserId());
                foundMessagesRestList.add(TextMessageRest.fromDomain(m));
            }
        }

        return foundMessagesRestList;
    }

    @GetMapping("/{sourceUserId}/{targetUserId}")
    public List<TextMessageRest> getAllMessagesForUsersIds(@PathVariable String sourceUserId, @PathVariable String targetUserId) {

        var foundMessagesList = facade.getAllMessagesForUsersIds(TextMessageId.of(sourceUserId), TextMessageId.of(targetUserId));
        var foundMessagesRestList = new ArrayList<TextMessageRest>();

        for(TextMessage m : foundMessagesList) {
            foundMessagesRestList.add(TextMessageRest.fromDomain(m));
        }

        return foundMessagesRestList;
    }

}
