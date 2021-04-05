package com.portal.app.controller;

import com.portal.app.model.User;
import com.portal.app.payload.ApiResponse;
import com.portal.app.payload.PagedResponse;
import com.portal.app.payload.UserIdentityAvailability;
import com.portal.app.payload.UserSummary;
import com.portal.app.repository.UserRepository;
import com.portal.app.security.CurrentUser;
import com.portal.app.security.UserPrincipal;
import com.portal.app.util.AppConstants;
import com.portal.app.util.CommonUtil;
import org.apache.catalina.mapper.Mapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
        return userSummary;
    }

    @GetMapping("/checkUsernameAvailability")
    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
        Boolean isAvailable = !userRepository.existsByUsername(username);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/checkEmailAvailability")
    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
        Boolean isAvailable = !userRepository.existsByEmail(email);
        return new UserIdentityAvailability(isAvailable);
    }

    @GetMapping("/registrations")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registrations() {
        List<User> users = (List<User>)userRepository.findRegistrations();
        if(CollectionUtils.isNotEmpty(users)) {
            List<com.portal.app.payload.User> response = users.stream().map(user ->
                    new ModelMapper().map(user, com.portal.app.payload.User.class)).collect(Collectors.toList());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public PagedResponse<com.portal.app.payload.User> getActiveInactiveUsers(@CurrentUser UserPrincipal currentUser,
                                                                          @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                                          @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        CommonUtil.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<User> users = userRepository.findActiveInactiveUsers(pageable);
        if (users.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), users.getNumber(),
                    users.getSize(), users.getTotalElements(), users.getTotalPages(), users.isLast());
        }
        if(users != null && CollectionUtils.isNotEmpty(users.getContent())) {
            List<com.portal.app.payload.User> response = users.map(user ->
                    new ModelMapper().map(user, com.portal.app.payload.User.class)).getContent();
            return new PagedResponse<>(response, users.getNumber(),
                    users.getSize(), users.getTotalElements(), users.getTotalPages(), users.isLast());
        } else {
            return new PagedResponse<>();
        }
    }

    @PostMapping("/activeUpdate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateActiveStatus(@RequestParam(value = "value") String value, @RequestParam(value = "uids") List<Long> userIds) {
        int count = userRepository.approveOrReject(userIds, Byte.valueOf(value));
        if (count > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUSerStatus(@RequestParam(value = "id") Long id,
                                              @RequestParam(value = "active") Byte active) {
        int count = userRepository.updateStatus(id, active);
        if (count > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
