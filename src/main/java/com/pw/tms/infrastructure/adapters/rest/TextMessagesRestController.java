package com.pw.tms.infrastructure.adapters.rest;

import com.pw.tms.domain.ports.incoming.TextMessageFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.pw.tms.domain.UserOperation.getAuthenticatedUserId;

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

        var sentMessage = facade.sendTextMessage(message
            .withSourceUserId(getAuthenticatedUserId())
            .toDomain());
        return TextMessageRest.fromDomain(sentMessage);
    }

    @GetMapping("/unread")
    public Long getUnreadMessagesCountForTargetUserId() {
        return facade.getUnreadMessagesCountForTargetUserId();
    }

    @GetMapping("/chats")
    public List<TextMessageRest> getAllChatsForUserId() {

        return facade.getAllChatsForUserId()
                .stream().map(TextMessageRest::fromDomain).collect(Collectors.toList());
    }

    @GetMapping
    public Page<TextMessageRest> getAllMessagesBetweenUsers(@RequestParam String secondUserId, Pageable pageable) {

        return facade.getAllMessagesBetweenUsers(secondUserId, pageable)
                .map(TextMessageRest::fromDomain);
    }

}
