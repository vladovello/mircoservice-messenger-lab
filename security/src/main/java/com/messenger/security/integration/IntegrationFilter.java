package com.messenger.security.integration;

import com.messenger.security.SecurityConst;
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
import java.util.Objects;

@RequiredArgsConstructor
public class IntegrationFilter extends OncePerRequestFilter {
    private final String apiKey;

    @Override
    public void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (!Objects.equals(request.getHeader(SecurityConst.HEADER_API_KEY), apiKey)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(new IntegrationAuthentication());
        filterChain.doFilter(request, response);
    }
}

