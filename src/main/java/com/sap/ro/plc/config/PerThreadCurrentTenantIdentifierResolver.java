package com.sap.ro.plc.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerThreadCurrentTenantIdentifierResolver
        implements CurrentTenantIdentifierResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerThreadCurrentTenantIdentifierResolver.class);

    private PerThreadTenantResolverService perThreadTenantResolverService =
            PerThreadTenantResolverService.getInstance();

    public PerThreadCurrentTenantIdentifierResolver() {
        LOGGER.debug("init: {}", this);
    }

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantIdentifier = perThreadTenantResolverService.getTenantIdentifier();
        LOGGER.debug("resolved tenant: {}", tenantIdentifier);
        return tenantIdentifier;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
