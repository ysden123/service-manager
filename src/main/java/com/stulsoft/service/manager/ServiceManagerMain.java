/*
 * Copyright (c) 2018. Yuriy Stul
 */

package com.stulsoft.service.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Yuriy Stul
 */
public class ServiceManagerMain {
    private static final Logger logger = LoggerFactory.getLogger(ServiceManagerMain.class);

    public static void main(String[] args) {
        logger.info("Started ServiceManager");
        logger.info("Stopped ServiceManager");
    }
}
