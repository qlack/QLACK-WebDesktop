package com.eurodyn.qlack.webdesktop.app.controller;

import com.eurodyn.qlack.fuse.aaa.dto.SessionDTO;
import com.eurodyn.qlack.fuse.aaa.model.User;
import com.eurodyn.qlack.fuse.aaa.repository.UserRepository;
import com.eurodyn.qlack.fuse.aaa.service.AccountingService;
import com.eurodyn.qlack.webdesktop.app.service.SessionService;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
@RestController
@RequestMapping("/api/session")
public class SessionController {

    private final UserRepository userRepository;
    private final AccountingService accountingService;
    private final SessionService sessionService;

    public SessionController(UserRepository userRepository,
                             AccountingService accountingService,
                             SessionService sessionService) {
        this.userRepository = userRepository;
        this.accountingService = accountingService;
        this.sessionService = sessionService;
    }

    @PostMapping("/terminate")
    public void terminateSession() {
        this.sessionService.terminateSession();
    }

    @PostMapping("/init")
    public void initSession(@RequestHeader("Cookie") String cookie) {
        this.sessionService.initSession(cookie);
    }

    @GetMapping("/history-by-username")
    public Page<SessionDTO> sessionHistoryByUsername(@RequestParam("username") String username, Pageable pageable) {
        return this.sessionService.sessionHistoryByUsername(username,pageable);
    }

    @GetMapping("/history-by-userid")
    public Page<SessionDTO> sessionHistoryById(@RequestParam("id") String id, Pageable pageable) {
       return this.sessionService.sessionHistoryById(id,pageable);
    }

}
