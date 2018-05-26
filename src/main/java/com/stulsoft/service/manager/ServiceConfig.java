/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.service.manager;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.logging.log4j.util.Strings;
import org.quartz.CronExpression;

/**
 * Holds a service configuration
 *
 * @author Yuriy Stul
 */
public class ServiceConfig {
    private final String name;
    private final String description;
    private final String address;
    private final boolean withRetry;
    private final Integer timeout;
    private final String cronExpression;

    /**
     * Initializes a new instance of the ServiceConfig class.
     *
     * @param name           the name of the service
     * @param description    description of the service; optional
     * @param address        address of the service
     * @param withRetry      true, if to retry; false - otherwise
     * @param timeout        timeout in seconds; must be defined, if the <i>withRetry</i> is true
     * @param cronExpression the cron expression
     */
    public ServiceConfig(String name, String description, String address, boolean withRetry, Integer timeout, String cronExpression) {
        if (Strings.isEmpty(name)) throw new IllegalArgumentException("name should be defined");
        if (Strings.isEmpty(address)) throw new IllegalArgumentException("address should be defined");
        if (Strings.isEmpty(cronExpression)) throw new IllegalArgumentException("cronExpression should be defined");
        if (withRetry && timeout == null)
            throw new IllegalArgumentException("timeout should be defined for withRetry equal true");
        if (!CronExpression.isValidExpression(cronExpression))
            throw new IllegalArgumentException(String.format("Invalid cronExpression \"%s\"", cronExpression));
        this.name = name;
        this.description = description;
        this.address = address;
        this.withRetry = withRetry;
        this.timeout = timeout;
        this.cronExpression = cronExpression;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getAddress() {
        return address;
    }

    public boolean isWithRetry() {
        return withRetry;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ServiceConfig)) return false;

        ServiceConfig that = (ServiceConfig) o;

        return new EqualsBuilder()
                .append(isWithRetry(), that.isWithRetry())
                .append(getName(), that.getName())
                .append(getDescription(), that.getDescription())
                .append(getAddress(), that.getAddress())
                .append(getTimeout(), that.getTimeout())
                .append(getCronExpression(), that.getCronExpression())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getName())
                .append(getDescription())
                .append(getAddress())
                .append(isWithRetry())
                .append(getTimeout())
                .append(getCronExpression())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("description", description)
                .append("address", address)
                .append("withRetry", withRetry)
                .append("timeout", timeout)
                .append("cronExpression", cronExpression)
                .toString();
    }
}
