package com.sap.ro.plc.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.hibernate.service.spi.ServiceRegistryAwareService;
import org.hibernate.service.spi.ServiceRegistryImplementor;
import org.hibernate.service.spi.Stoppable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.text.MessageFormat.format;

public class StaticMultiTenantConnectionProviderImpl
        extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl
        implements ServiceRegistryAwareService, Stoppable {

    private static final Logger LOGGER = LoggerFactory.getLogger(StaticMultiTenantConnectionProviderImpl.class);

    private Map<String, DataSource> dataSourceMap = new ConcurrentHashMap<>();

    public StaticMultiTenantConnectionProviderImpl() {
        LOGGER.debug("initialised");
        this.dataSourceMap.put("tenant1", getDataSource("1"));
        this.dataSourceMap.put("tenant2", getDataSource("2"));
    }

    @Override
    protected DataSource selectAnyDataSource() {
        LOGGER.debug("select any data source");
        return dataSourceMap.get("tenant1");
    }

    @Override
    protected DataSource selectDataSource(String tenantIdentifier) {
        LOGGER.debug("select data source for tenant '{}'", tenantIdentifier);
        return dataSourceMap.get(tenantIdentifier);
    }

    @Override
    public void injectServices(ServiceRegistryImplementor serviceRegistry) {
        LOGGER.debug("inject services: {}", serviceRegistry);
    }

    /*private DataSource tenant1DataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:15432/postgres");
        dataSource.setUsername("postgres");
        dataSource.setPassword("zaq12wsx");
        return dataSource;
    }

    private DataSource tenant2DataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:25432/postgres");
        dataSource.setUsername("postgres");
        dataSource.setPassword("zaq12wsx");
        return dataSource;
    }*/

    private DataSource getDataSource(String tenantId) {

        HikariConfig config = getHikariConfig(tenantId);
        return new HikariDataSource(config);
    }

    private HikariConfig getHikariConfig(String tenantId) {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("org.postgresql.Driver");
        config.setUsername("postgres");
        config.setPassword("zaq12wsx");
        config.setJdbcUrl(format("jdbc:postgresql://localhost:{0}5432/postgres", tenantId));
        config.setPoolName(format("tenant{0}PooledDataSource", tenantId));
        config.setMaximumPoolSize(100);
        config.setMinimumIdle(10);
        config.setIdleTimeout(30000);
        return config;
    }

    @Override
    public void stop() {
        LOGGER.debug("stopping");
        if (dataSourceMap != null) {
            dataSourceMap.clear();
            dataSourceMap = null;
        }
    }
}
