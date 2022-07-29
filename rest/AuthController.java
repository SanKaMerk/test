package ru.greenatom.atomskils.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import ru.greenatom.atomskils.security.JwtTokenRepository;
import ru.greenatom.atomskils.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private UserService service;
    @Autowired
    private JwtTokenRepository jwtTokenRepository;

    public AuthController(UserService service) {
        this.service = service;
    }

    @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ru.greenatom.atomskils.model.User getAuthUser(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        User user = (principal instanceof User) ? (User) principal : null;
        ru.greenatom.atomskils.model.User byLogin = this.service.getByLogin(user.getUsername());
        CsrfToken csrfToken = jwtTokenRepository.generateToken(request, byLogin);
        jwtTokenRepository.saveToken(csrfToken, request, response);
        return Objects.nonNull(user) ? byLogin : null;
    }
}
