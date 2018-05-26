/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.service.manager;

import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Yuriy Stul
 */
public class ServiceConfigTest {

    @Test
    public void testServiceConfig() {
        String name = "name";
        String description = "description";
        String address = "address";
        Integer timeout = 123;
        String cronExpression = "0 0 12 1/1 * ? *";

        ServiceConfig serviceConfig = new ServiceConfig(name, description, address, true, timeout, cronExpression);
        assertNotNull(serviceConfig);
        assertEquals(serviceConfig.getName(), name);
        assertEquals(serviceConfig.getDescription(), description);
        assertEquals(serviceConfig.getAddress(), address);
        assertEquals(serviceConfig.isWithRetry(), true);
        assertEquals(serviceConfig.getTimeout(), timeout);
        assertEquals(serviceConfig.getCronExpression(), cronExpression);
    }

    @Test
    public void testServiceConfig_failure() {
        String name = "name";
        String description = "description";
        String address = "address";
        Integer timeout = 123;
        String cronExpression = "0 0 12 1/1 * ? *";
        try {
            new ServiceConfig(null, description, address, true, timeout, cronExpression);
            fail("name null value should be prevented");
        } catch (IllegalArgumentException ignore) {
        }
        try {
            new ServiceConfig(name, null, address, true, timeout, cronExpression);
        } catch (IllegalArgumentException ignore) {
            fail("description null value should be not prevented");
        }
        try {
            new ServiceConfig(name, description, null, true, timeout, cronExpression);
            fail("address null value should be prevented");
        } catch (IllegalArgumentException ignore) {
        }
        try {
            new ServiceConfig(name, description, address, true, null, cronExpression);
            fail("timeout null value should be prevented");
        } catch (IllegalArgumentException ignore) {
        }
        try {
            new ServiceConfig(name, description, address, false, null, cronExpression);
        } catch (IllegalArgumentException ignore) {
            fail("timeout null value should be not prevented for withRetry = false");
        }
        try {
            new ServiceConfig(name, description, address, true, timeout, null);
            fail("cronExpression null value should be prevented");
        } catch (IllegalArgumentException ignore) {
        }
        try {
            new ServiceConfig(name, description, address, true, timeout, "* * 66");
            fail("Invalid cronExpression value should be prevented");
        } catch (IllegalArgumentException ignore) {
        }
    }

    @Test
    public void testToString() {
        String name = "name";
        String description = "description";
        String address = "address";
        Integer timeout = 123;
        String cronExpression = "0 0 12 1/1 * ? *";
        ServiceConfig serviceConfig = new ServiceConfig(name, description, address, true, timeout, cronExpression);
        assertNotNull(serviceConfig);
        assertNotNull(serviceConfig.toString());
    }
}