package org.gycoding.accounts.infrastructure.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.gycoding.accounts.domain.exceptions.AccountsAPIError;
import org.gycoding.exceptions.model.APIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class CustomCORSFilter extends OncePerRequestFilter {
    @Value("${allowed.origin}")
    private String allowedOrigin;

    @Value("${allowed.apiKey}")
    private String allowedApiKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String origin = request.getHeader("Host");
        String apiKey = request.getHeader("x-api-key");

        if ((origin != null && origin.startsWith(allowedOrigin)) || (apiKey != null && apiKey.equals(allowedApiKey))) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(
                    new APIException(
                        AccountsAPIError.FORBIDDEN.getCode(),
                        AccountsAPIError.FORBIDDEN.getMessage(),
                        AccountsAPIError.FORBIDDEN.getStatus().value()
                    ).toString()
            );
        }
    }
}
