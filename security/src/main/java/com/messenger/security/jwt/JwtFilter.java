package com.messenger.security.jwt;

import com.messenger.security.SecurityConst;
import com.messenger.security.exception.UnauthorizedException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

@Slf4j
public class JwtFilter extends OncePerRequestFilter {
    private final ValidationParams validationParams;

    public JwtFilter(ValidationParams validationParams) {
        this.validationParams = validationParams;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        var jwt = request.getHeader(SecurityConst.HEADER_JWT);
        if (jwt == null) {
            log.info("No JWT header was provided");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        var jwtSplit = jwt.split(" ");

        if (Objects.equals(jwtSplit[0], "Bearer")) {
            jwt = jwtSplit[1];
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        PayloadPrincipal userData;
        try {
            var key = Keys.hmacShaKeyFor(validationParams.getValidationKey().getBytes(StandardCharsets.UTF_8));
            var data = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt);

            var idStr = String.valueOf(data.getBody().getSubject());

            userData = new PayloadPrincipal(
                    idStr == null ? null : UUID.fromString(idStr),
                    String.valueOf(data.getBody().get("log"))
            );
        } catch (JwtException e) {
            throw new UnauthorizedException(e.getMessage());
        }

        var authentication = new PayloadAuthentication(userData);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}

