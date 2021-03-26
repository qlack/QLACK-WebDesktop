package com.eurodyn.qlack.webdesktop.app.service;

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
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.Instant;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Log
@Service
public class SessionService {

    private final UserRepository userRepository;
    private final AccountingService accountingService;
    private final EntityManager entityManager;

    public SessionService(UserRepository userRepository,
                          AccountingService accountingService,
                          EntityManager entityManager) {
        this.userRepository = userRepository;
        this.accountingService = accountingService;
        this.entityManager = entityManager;
    }

    public void terminate(String cookie) {

        String jSessionId = this.retrieveJSessionIdFromCookie(cookie);

        if (jSessionId == "") {
            return;
        }

        Boolean alreadyOnDatabase = this.checkJSessionIdAlreadyOnDatabase(jSessionId);

        if (!alreadyOnDatabase) {
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof DefaultOAuth2User) {
            this.accountingService.terminateSessionByApplicationSessionId(jSessionId);
            log.info("Session " + jSessionId + " terminated");
        }

    }

    public void init(String cookie) {

        String jSessionId = this.retrieveJSessionIdFromCookie(cookie);

        if (jSessionId == "") {
            return;
        }

        Boolean alreadyOnDatabase = this.checkJSessionIdAlreadyOnDatabase(jSessionId);

        if (alreadyOnDatabase) {
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        if (principal instanceof DefaultOAuth2User) {
            User user = userRepository.findByUsername(((DefaultOAuth2User) principal).getName());
            SessionDTO sessionDto = new SessionDTO();
            sessionDto.setUserId(user.getId());
            sessionDto.setCreatedOn(Instant.now().toEpochMilli());
            sessionDto.setApplicationSessionId(jSessionId);

            this.accountingService.createSession(sessionDto);
            log.info("Session " + jSessionId + " initiated");
        }

    }

    private String retrieveJSessionIdFromCookie(String cookie) {

        if (cookie == null) {
            return "";
        }

        Pattern pattern = Pattern.compile("JSESSIONID=.*$");
        Matcher matcher = pattern.matcher(cookie);

        if (!matcher.find()) {
            return "";
        }

        String jSessionIdCookieExpr = matcher.group(0);
        String[] jSessionIdCookieparts = jSessionIdCookieExpr.split("=");
        String jSessionId = jSessionIdCookieparts[1];

        return jSessionId;
    }

    private Boolean checkJSessionIdAlreadyOnDatabase(String jSessionId) {
        Query query = entityManager.createNativeQuery(
                "SELECT * FROM aaa_session WHERE application_session_id = :application_session_id");
        query.setParameter("application_session_id", jSessionId);
        List<Object> resultList = query.getResultList();

        if (resultList == null) {
            return false;
        }

        if (resultList.size() == 0) {
            return false;
        }

        return true;
    }

    public Page<SessionDTO> sessionHistoryByUsername(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            return null;
        }
        return this.accountingService.getSessions(user.getId(), pageable);
    }

    public Page<SessionDTO> sessionHistoryById(String id, Pageable pageable) {
        return this.accountingService.getSessions(id, pageable);
    }

}
