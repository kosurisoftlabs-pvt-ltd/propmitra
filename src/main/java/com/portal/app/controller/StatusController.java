package com.portal.app.controller;

import com.portal.app.repository.StatusRepository;
import com.portal.app.service.VerifyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    private static final Logger logger = LoggerFactory.getLogger(StatusController.class);

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private VerifyService verifyService;

    @GetMapping("/inquiry")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> updateInquiryStatus(@RequestParam(value = "id") Long id,
                                                           @RequestParam(value = "active") Byte active) {
        int count = statusRepository.updateInquiryStatus(id, active);
        if (count > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/verify")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateVerifyStatus(@RequestParam(value = "id") Long id,
                                                 @RequestParam(value = "active") Byte active) {
        int count = statusRepository.updateVerifyStatus(id, active);
        if (count > 0) {
            if(active == 1) {
                verifyService.sendVerifyStatus(id);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
