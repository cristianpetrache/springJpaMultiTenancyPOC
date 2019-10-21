package com.sap.ro.plc.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PerThreadTenantResolverService {

    private static final PerThreadTenantResolverService INSTANCE = new PerThreadTenantResolverService();
    private static Logger LOGGER = LoggerFactory.getLogger(PerThreadTenantResolverService.class);

    private ThreadLocal<String> tenantIdentifierThreadLocal = ThreadLocal.withInitial(() -> "NO_TENANT_SET");

    private PerThreadTenantResolverService() {
    }

    public static PerThreadTenantResolverService getInstance() {
        return INSTANCE;
    }

    public void setTenantIdentifier(String tenantIdentifier) {
        LOGGER.debug("setting tenant to: {}", tenantIdentifier);
        tenantIdentifierThreadLocal.set(tenantIdentifier);
    }

    public String getTenantIdentifier() {
        LOGGER.debug("retrieving tenant: {}", tenantIdentifierThreadLocal.get());
        return tenantIdentifierThreadLocal.get();
    }

    public void cleanup() {
        LOGGER.debug("cleaning up tenant: {}", tenantIdentifierThreadLocal.get());
        tenantIdentifierThreadLocal.remove();
    }
}
