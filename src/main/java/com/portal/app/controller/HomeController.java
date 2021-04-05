package com.portal.app.controller;

import com.portal.app.model.User;
import com.portal.app.payload.ApiResponse;
import com.portal.app.payload.request.ChangePwd;
import com.portal.app.payload.request.SearchRequest;
import com.portal.app.repository.UserRepository;
import com.portal.app.security.CurrentUser;
import com.portal.app.security.UserPrincipal;
import com.portal.app.service.SearchService;
import com.portal.app.service.VerificationTokenService;
import com.portal.app.util.AppConstants;
import com.portal.app.util.LoggerUtil;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private SearchService searchService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private VerificationTokenService verificationTokenService;

    @GetMapping("/")
    public String defaultView(@RequestParam(value = "rentSale", required = false) String rentSale, Model model) {
        model.addAttribute("title", "Buy or Sale in Hyderabad | Hyderabad Real Estate | Propmitra");
        if(rentSale != null && (rentSale.equalsIgnoreCase("Rent") || rentSale.equalsIgnoreCase("Sale"))) {
            model.addAttribute("meta", searchService.getSearchMeta(rentSale));
        } else {
            model.addAttribute("meta", searchService.getSearchMeta("Sale"));
        }
        /*SearchRequest request = new SearchRequest();
        request.setRentOrSale("ALL");
        model.addAttribute("results", searchService.searchProperties(request));*/
        return "index";
    }

    @GetMapping({"/login", "/register", "/dashboard", "/property-list", "/reports/*", "/verify", "/inactive",
            "/customers", "/submit-listing/*", "/inquiry"})
    public String loginView(Model model) {
        return "forward:/index.html";
    }

    /*@GetMapping({"/logout"})
    public String logout(Model model) {
        return "redirect:/signin";
    }*/

    @GetMapping({"/signin.html", "/signin"})
    public String signInView(Model model) {
        return "signin";
    }

    @GetMapping({"/signup.html", "/signup"})
    public String signUpView(Model model) {
        return "signup";
    }

    @GetMapping("/properties")
    public String getProperties(@ModelAttribute SearchRequest request, Model model) {
        model.addAttribute("results", searchService.searchProperties(request));
        model.addAttribute("title", "Show Properties");
        model.addAttribute("rentOrSale", request.getRentOrSale());
        model.addAttribute("city", request.getLocation());
        model.addAttribute("propType", request.getPropType());
        model.addAttribute("propStatus", request.getPropStatus());
        model.addAttribute("minPrice", request.getMinPrice());
        model.addAttribute("maxPrice", request.getMaxPrice());
        model.addAttribute("meta", searchService.getSearchMeta((StringUtils.isNotBlank(request.getRentOrSale()) ? request.getRentOrSale() : "Sale")));
        return "properties";
    }

    @PostMapping ("/properties")
    public String searchProperties(@RequestBody SearchRequest request, Model model) {
        model.addAttribute("results", searchService.searchProperties(request));
        model.addAttribute("title", "Show Properties");
        return String.format("results :: results(rentOrSale='%s')", request.getRentOrSale());
    }

    @GetMapping ("/property-info/{propId}")
    public String searchPropertiesView(@PathVariable String propId, Model model) {
        model.addAttribute("prop", searchService.getPropertyInfo(propId));
        model.addAttribute("title", "Property Info");
        return "property-info";
    }

    @GetMapping("/forgotpwd")
    public String forgotpwdView(Model model) {
        model.addAttribute("title", "Forgot Password");
        return "forgotpwd";
    }

    @PostMapping("/forgotpwd")
    public String forgotpwdAction(String email, Model model) {
        model.addAttribute("title", "Forgot Password");
        Boolean response = verificationTokenService.createForgotVerification(email);
        if(response == null) {
            model.addAttribute("status", "not-exist");
        } else if(Boolean.FALSE.equals(response)) {
            model.addAttribute("status", "mail-error");
        } else if(Boolean.TRUE.equals(response)) {
            model.addAttribute("status", "success");
        }
        return "forgotpwd";
    }

    @GetMapping("/resetpwd")
    public String resetPasswordView(String code, Model model) {

        LoggerUtil.debug(logger, "IN :: resetPassword :: request details : ", code);
        try {
            boolean response = verificationTokenService.verifyResetToken(code);
            LoggerUtil.debug(logger, "OUT :: resetPassword :: response : ", response);
            if(response) {
                model.addAttribute("status", "success");
                model.addAttribute("code", code);
            } else {
                model.addAttribute("status", "failed");
            }
        } catch (Exception exception) {
            logger.error("Exception occurred while processing request :: ", exception);
            LoggerUtil.debug(logger, "OUT :: resetPassword", StringUtils.EMPTY);
            model.addAttribute("status", "error");
        }
        return "resetpwd";
    }

    @PostMapping("/resetpwd")
    public String resetPassword(String code, String upass, Model model) {

        LoggerUtil.debug(logger, "IN :: resetPassword", StringUtils.EMPTY);
        try {
            boolean response = verificationTokenService.resetPassword(code, upass);
            LoggerUtil.debug(logger, "OUT :: resetPassword :: response : ", response);
            if(response) {
                model.addAttribute("status", "reset");
            } else {
                model.addAttribute("status", "failed");
            }
        } catch (Exception exception) {
            logger.error("Exception occurred while processing request :: ", exception);
            LoggerUtil.debug(logger, "OUT :: resetPassword", StringUtils.EMPTY);
            model.addAttribute("status", "error");
        }
        return "resetpwd";
    }

    @GetMapping("/verify-email")
    public String verifyEmail(String code, Model model) {

        LoggerUtil.debug(logger, "IN :: verifyEmail :: request details : ", code);
        try {
            boolean response = verificationTokenService.verifyToken(code);
            LoggerUtil.debug(logger, "OUT :: verifyEmail :: response : ", response);
            if(response) {
                model.addAttribute("status", "success");
            } else {
                model.addAttribute("status", "failed");
            }
        } catch (Exception exception) {
            logger.error("Exception occurred while processing request :: ", exception);
            LoggerUtil.debug(logger, "OUT :: verifyEmail", StringUtils.EMPTY);
            model.addAttribute("status", "error");
        }
        return "account-activation";
    }

    @GetMapping("/verify-phone")
    public String verifyPhone(String code, Model model) {

        LoggerUtil.debug(logger, "IN :: verifyPhone :: request details : ", code);
        try {
            boolean response = verificationTokenService.verifyToken(code);
            LoggerUtil.debug(logger, "OUT :: verifyPhone :: response : ", response);
            if(response) {
                model.addAttribute("status", "success");
            } else {
                model.addAttribute("status", "failed");
            }
        } catch (Exception exception) {
            logger.error("Exception occurred while processing request :: ", exception);
            LoggerUtil.debug(logger, "OUT :: verifyPhone", StringUtils.EMPTY);
            model.addAttribute("status", "error");
        }
        return "account-activation";
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String getUserProfile(@CurrentUser UserPrincipal currentUser, Model model) {
        model.addAttribute("title", "My Profile | Propmitra");
        if(currentUser != null) {
            Optional<User> userModel = userRepository.findByEmail(currentUser.getEmail());
            model.addAttribute("profile", userModel.get());
        }
        return "profile";
    }

    @PostMapping("/profile")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String updateProfile(@CurrentUser UserPrincipal currentUser, @ModelAttribute com.portal.app.payload.User user, Model model) {
        Optional<User> userOptional = userRepository.findById(user.getId());
        User userModel = userOptional.get();
        if(userModel != null) {
            if(StringUtils.isNotBlank(user.getEmail()) && !user.getEmail().equalsIgnoreCase(userModel.getEmail())) {
                if (userRepository.existsByEmail(user.getEmail())) {
                    model.addAttribute("error", "Email Address already in use!");
                    model.addAttribute("profile", userModel);
                    return "profile";
                }
            }

            if(StringUtils.isNotBlank(user.getUsername()) && !user.getUsername().equalsIgnoreCase(userModel.getUsername())) {
                if (userRepository.existsByUsername(user.getUsername())) {
                    model.addAttribute("error", "Username is already taken!");
                    model.addAttribute("profile", userModel);
                    return "profile";
                }
            }
            userModel.setUsername(user.getUsername());
            userModel.setAddress(user.getAddress());
            userModel.setCity(user.getCity());
            userModel.setContact(user.getContact());
            userModel.setName(user.getName());
            userModel.setEmail(user.getEmail());
            userRepository.save(userModel);
            model.addAttribute("success", "Profile updated successfully");
            model.addAttribute("profile", userModel);
            return "profile";
        } else {
            model.addAttribute("error", "Profile does not exist!");
            model.addAttribute("profile", userModel);
            return "profile";
        }
    }

    @GetMapping("/changepwd")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String changepwdView(@CurrentUser UserPrincipal currentUser, Model model) {
        model.addAttribute("title", "Change Password | Propmitra");
        return "changepwd";
    }

    @PostMapping("/changepwd")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public String changepwd(@CurrentUser UserPrincipal currentUser, @ModelAttribute ChangePwd request, Model model) {
        model.addAttribute("title", "Change Password | Propmitra");
        if(currentUser != null) {
            if(passwordEncoder.matches(request.getCurrentPass(), currentUser.getPassword())) {
                User userModel = userRepository.findById(currentUser.getId()).get();
                if(userModel != null) {
                    userModel.setPassword(passwordEncoder.encode(request.getNewPass()));
                    userRepository.save(userModel);
                    model.addAttribute("success", "Password updated successfully. Please logout and login again.");
                    return "changepwd";
                } else {
                    model.addAttribute("error", "Unable to update password at this time. Please try again later!");
                    return "changepwd";
                }
            } else {
                model.addAttribute("error", "Incorrect current password");
                return "changepwd";
            }
        } else {
            model.addAttribute("error", "Unable to update password at this time. Please try again later!");
            return "changepwd";
        }
    }

    @RequestMapping("/old")
    String oldView(Model model) {
        model.addAttribute("title", "Propmitra");
        return "index_old";
    }

    @RequestMapping("/404")
    String get404ErrorPage(Model model) {
        model.addAttribute("title", "404 - Not Found");
        return "404";
    }

    @RequestMapping("/401")
    String get401ErrorPage(Model model) {
        model.addAttribute("title", "404 - Not Found");
        return "404";
    }

    @RequestMapping("/403")
    String get403ErrorPage(Model model) {
        model.addAttribute("title", "403 - Access Forbidden");
        return "403";
    }

    @RequestMapping("/500")
    String get500ErrorPage(Model model) {
        model.addAttribute("title", "500 - Internal Server Error");
        return "500";
    }
}
