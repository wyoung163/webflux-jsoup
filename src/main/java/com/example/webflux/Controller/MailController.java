package com.example.webflux.Controller;

import com.example.webflux.Service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequiredArgsConstructor
public class MailController {

    @Autowired
    private final MailService mailService;

    @GetMapping("/mail")
    public Flux<Void> sendMail() {
        return mailService.sendMails();
    }


}
