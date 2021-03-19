package com.eurodyn.qlack.webdesktop.app.controller;

import com.eurodyn.qlack.fuse.aaa.dto.SessionDTO;
import com.eurodyn.qlack.webdesktop.app.service.SessionService;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Log
@RestController
@RequestMapping("/api/session")
public class SessionController {

    private final SessionService sessionService;

    public SessionController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @PostMapping("/terminate")
    public void terminate(@RequestHeader("Cookie") String cookie) {
        this.sessionService.terminate(cookie);
    }

    @PostMapping("/init")
    public void init(@RequestHeader("Cookie") String cookie) {
        this.sessionService.init(cookie);
    }

    @GetMapping("/history-by-username")
    public Page<SessionDTO> sessionHistoryByUsername(@RequestParam("username") String username, Pageable pageable) {
        return this.sessionService.sessionHistoryByUsername(username, pageable);
    }

    @GetMapping("/history-by-userid")
    public Page<SessionDTO> sessionHistoryById(@RequestParam("id") String id, Pageable pageable) {
        return this.sessionService.sessionHistoryById(id, pageable);
    }

}
