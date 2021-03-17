package com.eurodyn.qlack.webdesktop.app.controller;

import com.eurodyn.qlack.fuse.aaa.dto.SessionDTO;
import com.eurodyn.qlack.fuse.aaa.model.User;
import com.eurodyn.qlack.fuse.aaa.repository.UserRepository;
import com.eurodyn.qlack.fuse.aaa.service.AccountingService;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.Instant;

@Log
@RestController
@RequestMapping("/api/session")
public class SessionController {

    private final UserRepository userRepository;
    private final AccountingService accountingService;

    public SessionController(UserRepository userRepository,
                             AccountingService accountingService) {
        this.userRepository = userRepository;
        this.accountingService = accountingService;
    }

    @GetMapping("/terminate")
    public void terminateSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof DefaultOAuth2User) {
            User user = userRepository.findByUsername(((DefaultOAuth2User) principal).getName());
            this.accountingService.terminateSessionByUserId(user.getId());
            log.info("Session terminated for user " + user.getUsername());
        }
    }

    @GetMapping("/init")
    public void initSession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof DefaultOAuth2User) {
            User user = userRepository.findByUsername(((DefaultOAuth2User) principal).getName());
            SessionDTO sessionDto = new SessionDTO();
            sessionDto.setUserId(user.getId());
            sessionDto.setCreatedOn(Instant.now().toEpochMilli());
            this.accountingService.createSession(sessionDto);
            log.info("Session initiated for user " + user.getUsername());
        }
    }

    @GetMapping("/history")
    public Page<SessionDTO> sessionHistory(@RequestParam("username") String username, Pageable pageable) {
        User user = userRepository.findByUsername(username);
        if (user == null) return null;
        return this.accountingService.getSessions(user.getId(), pageable);
    }

}
