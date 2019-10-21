package com.sap.ro.plc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class TenantResolverFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TenantResolverFilter.class);
    private static final String TENANT_HEADER_NAME = "TENANT";

    private PerThreadTenantResolverService perThreadTenantResolverService =
            PerThreadTenantResolverService.getInstance();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        LOGGER.info("Initialised the tenant servlet filter: {}", this);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            if (HttpServletRequest.class.isAssignableFrom(servletRequest.getClass())) {
                HttpServletRequest httpServletRequest = HttpServletRequest.class.cast(servletRequest);
                perThreadTenantResolverService.setTenantIdentifier(httpServletRequest.getHeader(TENANT_HEADER_NAME));
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            perThreadTenantResolverService.cleanup();
        }
    }
}
