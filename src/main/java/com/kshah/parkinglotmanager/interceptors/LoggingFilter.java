package com.kshah.parkinglotmanager.interceptors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Enumeration;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Component
public class LoggingFilter extends OncePerRequestFilter {

    private ObjectMapper objectMapper;

    @Autowired
    public LoggingFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        // Cache the request so we can read the request payload without consuming the InputStream
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(httpServletRequest);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(httpServletResponse);

        // Service actual request
        filterChain.doFilter(requestWrapper, responseWrapper);

        // Get request URL, HTTP method and request headers
        String requestUrl = requestWrapper.getRequestURL().toString();
        HttpMethod httpMethod = HttpMethod.valueOf(requestWrapper.getMethod());
        HttpHeaders requestHeaders = new HttpHeaders();
        Enumeration headerNames = requestWrapper.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = (String) headerNames.nextElement();
            requestHeaders.add(headerName, requestWrapper.getHeader(headerName));
        }

        // Convert request payload to string
        String requestBody = IOUtils.toString(requestWrapper.getInputStream(), UTF_8);
        JsonNode requestBodyAsJson = objectMapper.readTree(requestBody);

        RequestEntity<JsonNode> requestEntity = new RequestEntity<>(requestBodyAsJson, requestHeaders, httpMethod, URI.create(requestUrl));

        // Log request
        log.info(requestEntity.toString());

        // Get response status and response headers
        HttpStatus responseStatus = HttpStatus.valueOf(responseWrapper.getStatusCode());
        HttpHeaders responseHeaders = new HttpHeaders();
        for (String headerName : responseWrapper.getHeaderNames()) {
            responseHeaders.add(headerName, responseWrapper.getHeader(headerName));
        }

        // Convert response payload to string
        String responseBody = IOUtils.toString(responseWrapper.getContentInputStream(), UTF_8);
        JsonNode responseBodyAsJson = objectMapper.readTree(responseBody);
        ResponseEntity<JsonNode> responseEntity = new ResponseEntity<>(responseBodyAsJson, responseHeaders, responseStatus);

        // Log response
        log.info(responseEntity.toString());

        // Copy content of response into original response as ContentCachingResponseWrapper will cache the response body by reading it from the output stream, effectively emptying it
        responseWrapper.copyBodyToResponse();
    }
}
