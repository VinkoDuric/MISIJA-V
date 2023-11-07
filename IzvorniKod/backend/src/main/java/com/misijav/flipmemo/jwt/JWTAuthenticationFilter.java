package com.misijav.flipmemo.jwt;

import com.misijav.flipmemo.model.Account;
import com.misijav.flipmemo.rest.AccountUserDetailsService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JWTAuthenticationFilter(JWTUtil jwtUtil,
                                   AccountUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            filterChain.doFilter(request, response);
            return;
        }

        for (Cookie cookie : cookies) {
            if (!cookie.getName().equals("auth"))
                continue;

            String jwt = cookie.getValue();

            try {
                String subject = jwtUtil.getSubject(jwt);

                if (subject != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    Account account = (Account) userDetailsService.loadUserByUsername(subject);

                    if (jwtUtil.isTokenValid(jwt, account.getUsername(), account.getTokenVersion())) {
                        UsernamePasswordAuthenticationToken authenticationToken =
                                new UsernamePasswordAuthenticationToken(
                                        account, null, account.getAuthorities()
                                );
                        authenticationToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(request)
                        );
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            } catch (JwtException ignored) {
                SecurityContextHolder.getContext().setAuthentication(null);
            }
        }

        filterChain.doFilter(request, response);
    }
}