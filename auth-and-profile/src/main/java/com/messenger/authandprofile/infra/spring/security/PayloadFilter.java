package com.messenger.authandprofile.infra.spring.security;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.messenger.authandprofile.infra.spring.exception.UnauthorizedException;
import com.messenger.authandprofile.shared.model.PayloadPrincipal;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

@RequiredArgsConstructor
public class PayloadFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        var encodedPrincipal = request.getHeader(SecurityConst.HEADER_PRINCIPAL);
        if (encodedPrincipal == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        PayloadPrincipal userData;
        try {
            var data = Base64.getDecoder().decode(encodedPrincipal);
            userData = new Gson().fromJson(new String(data), PayloadPrincipal.class);
        } catch (JsonSyntaxException e) {
            throw new UnauthorizedException();
        }

        var authentication = new PayloadAuthentication(userData);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}

