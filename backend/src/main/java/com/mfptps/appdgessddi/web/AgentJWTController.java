package com.mfptps.appdgessddi.web;

import com.mfptps.appdgessddi.security.jwt.JWTFilter;
import com.mfptps.appdgessddi.security.jwt.TokenProvider;
import com.mfptps.appdgessddi.service.AgentService;
import com.mfptps.appdgessddi.service.dto.AuthenticationInformationDto;
import com.mfptps.appdgessddi.web.vm.LoginVM;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller to authenticate agents.
 */
@RestController
@RequestMapping("/api")
public class AgentJWTController {

    private final Logger log = LoggerFactory.getLogger(AgentJWTController.class);

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final AgentService agentService;

    public AgentJWTController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder,
            AgentService agentService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.agentService = agentService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationInformationDto> authorize(@Valid @RequestBody LoginVM loginVM) {

        log.debug("Login with information {}", loginVM);

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginVM.getUsername(), loginVM.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        boolean rememberMe = (loginVM.isRememberMe() == null) ? false : loginVM.isRememberMe();
        // String jwt = tokenProvider.createToken(authentication, rememberMe);
        AuthenticationInformationDto informationDto = tokenProvider.createCustomAuthToken(authentication, rememberMe, agentService.getStructureOfAgent(loginVM.getUsername()));
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + informationDto.getAccessToken());
        return new ResponseEntity<>(informationDto, httpHeaders, HttpStatus.OK);
    }
}
