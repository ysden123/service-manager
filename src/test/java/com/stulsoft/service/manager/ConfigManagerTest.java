/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.service.manager;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Yuriy Stul
 */
public class ConfigManagerTest {

    @Before
    public void setUp() {
        System.setProperty("environment", "unit-test");
    }

    @Test
    public void instance() {
        List<ConfigManager> managers = new ArrayList<>();
        List<Thread> threads = new ArrayList<>();
        Random random = new Random(System.currentTimeMillis());
        for (int i = 1; i <= 10; ++i) {
            Thread t = new Thread(() -> {
                try {
                    Thread.sleep(100 + random.nextInt(500));
                } catch (Exception ignore) {
                }
                managers.add(ConfigManager.instance());
            });
            threads.add(t);
            t.start();
        }

        threads.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException ignore) {
            }
        });

        managers.forEach(manager -> assertTrue(managers.get(0) == manager));
    }

    @Test
    public void getAllServiceConfigs() {
        Collection<ServiceConfig> serviceConfigs = ConfigManager.instance().getAllServiceConfigs();
        assertEquals(2, serviceConfigs.size());
        assertTrue(serviceConfigs
                .stream()
                .anyMatch(sc -> sc.getName().equals("service1")));
        assertTrue(serviceConfigs
                .stream()
                .anyMatch(sc -> sc.getName().equals("service2")));
    }
}