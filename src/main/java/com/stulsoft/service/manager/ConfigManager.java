/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.service.manager;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * Config manager
 *
 * @author Yuriy Stul
 */
public class ConfigManager {
    private static final Logger logger = LoggerFactory.getLogger(ConfigManager.class);
    private static volatile ConfigManager instance = null;

    private final HashMap<String, ServiceConfig> serviceConfigs;

    private ConfigManager() {
        logger.info("Initializing ConfigManager for {} environment", System.getProperty("environment", "production"));
        serviceConfigs = new HashMap<>();
        init();
    }

    /**
     * Returns an instance of the ConfigManager class.
     *
     * @return the instance of the ConfigManager class.
     */
    public static ConfigManager instance() {
        if (instance == null) {
            synchronized (ConfigManager.class) {
                if (instance == null) {
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }

    private void init() {
        String configFileName;
        String environment = System.getProperty("environment", "production");
        switch (environment) {
            case "production":
                configFileName = "application.conf";
                break;
            case "unit-test":
                configFileName = "application-unit-test.conf";
                break;
            default:
                String message = "Unsupported environment " + environment;
                logger.error(message);
                throw new RuntimeException(message);
        }
        Config config = ConfigFactory.parseFile(new File(configFileName));
        serviceConfigs.putAll(
                config.getConfigList("services").stream().map(serviceConfig -> {
                    Integer timeout;
                    try {
                        if (serviceConfig.getIsNull("timeout")) {
                            timeout = null;
                        } else {
                            timeout = serviceConfig.getInt("timeout");
                        }
                    } catch (Exception ignore) {
                        timeout = null;
                    }
                    return new ServiceConfig(serviceConfig.getString("name"),
                            serviceConfig.getString("description"),
                            serviceConfig.getString("address"),
                            serviceConfig.getBoolean("withRetry"),
                            timeout,
                            serviceConfig.getString("cronExpression"));
                })
                        .collect(Collectors.toMap(ServiceConfig::getName, sc -> sc)));
        logger.info("{} services in the configuration", serviceConfigs.size());
        logger.info("  {} services with retry", serviceConfigs.values().stream().filter(ServiceConfig::isWithRetry).count());
        logger.info("  {} services without retry", serviceConfigs.values().stream().filter(sc -> !sc.isWithRetry()).count());
    }

    /**
     * Returns a collection of the all ServiceConfig objects.
     *
     * @return the collection of the all ServiceConfig objects.
     */
    public Collection<ServiceConfig> getAllServiceConfigs() {
        return serviceConfigs.values();
    }
}
