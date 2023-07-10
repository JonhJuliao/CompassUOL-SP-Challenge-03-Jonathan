package com.challenge3.msusers.filter;

import com.challenge3.msusers.exception.JwtTokenMalformedException;
import com.challenge3.msusers.exception.JwtTokenMissingException;
import com.challenge3.msusers.security.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private UserDetailsService userDetailsService;

    private JwtUtil jwtUtil;

    @Autowired
    public JwtRequestFilter(UserDetailsService userDetailsService, JwtUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String email = null;
        String jwtToken = null;

        if (authorizationHeader != null) {
            jwtToken = authorizationHeader;
            try {
                email = jwtUtil.getClaims(jwtToken).getSubject();
            } catch (ExpiredJwtException e) {
                logger.warn("Expired JWT token");
            } catch (UnsupportedJwtException e) {
                logger.warn("Unsupported JWT token");
            } catch (MalformedJwtException e) {
                logger.warn("Malformed JWT token");
            } catch (SignatureException e) {
                logger.warn("Invalid JWT signature");
            } catch (IllegalArgumentException e) {
                logger.warn("Empty or null JWT token");
            }
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

            try {
                if (jwtUtil.validateToken(jwtToken)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (JwtTokenMalformedException e) {
                throw new RuntimeException(e);
            } catch (JwtTokenMissingException e) {
                throw new RuntimeException(e);
            }
        }

        chain.doFilter(request, response);
    }
}

