package ru.prplhd.tennisscoreboard.filter;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter(value = "/*", dispatcherTypes = DispatcherType.ERROR)
public class ErrorLoggingFilter extends HttpFilter {

    private static final String STATUS_ATTRIBUTE = "jakarta.servlet.error.status_code";
    private static final String URI_ATTRIBUTE = "jakarta.servlet.error.request_uri";
    private static final String MESSAGE_ATTRIBUTE = "jakarta.servlet.error.message";
    private static final String EXCEPTION_ATTRIBUTE = "jakarta.servlet.error.exception";

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        Integer status = (Integer) req.getAttribute(STATUS_ATTRIBUTE);
        String  uri = (String) req.getAttribute(URI_ATTRIBUTE);
        String message = (String) req.getAttribute(MESSAGE_ATTRIBUTE);
        Throwable ex = (Throwable) req.getAttribute(EXCEPTION_ATTRIBUTE);

        if (status == HttpServletResponse.SC_INTERNAL_SERVER_ERROR) {
            log.error("500 UNEXPECTED ERROR {} {} msg={}", req.getMethod(), uri, message, ex);
        }

        chain.doFilter(req, res);
    }
}