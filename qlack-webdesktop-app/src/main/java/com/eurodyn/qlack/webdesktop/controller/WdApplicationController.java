package com.eurodyn.qlack.webdesktop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eurodyn.qlack.webdesktop.dto.WdApplicationDTO;
import com.eurodyn.qlack.webdesktop.service.WdApplicationService;

import lombok.extern.java.Log;


/**
 * Provides Web Desktop application REST service endpoints.
 *
 * @author European Dynamics SA.
 */
@Log
@RestController
@RequestMapping("/apps")
public class WdApplicationController {

    private WdApplicationService wdApplicationService;

    @Autowired
    public WdApplicationController(WdApplicationService wdApplicationService) {
        this.wdApplicationService = wdApplicationService;
    }

    /**
     * Get all the active Web Desktop applications
     * @return a list of the active Web Desktop applications
     */
    @GetMapping(path = "/all")
    public List<WdApplicationDTO> getActiveApplications() {
        return wdApplicationService.findAllActiveApplications();
    }

    /**
     * Get all the active Web Desktop applications filtered
     * @return a list of the active Web Desktop applications
     */
    @GetMapping(path = "/filtered")
    public List<WdApplicationDTO> getFilteredActiveApplications() {
        return wdApplicationService.findAllActiveApplicationsFilterGroupName();
    }

}
