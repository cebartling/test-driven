package com.pintailconsultingllc.testing.extensions;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.logging.Logger;

public class DatabaseManagementExtension implements
        BeforeAllCallback,
        AfterAllCallback,
        BeforeEachCallback,
        AfterEachCallback,
        BeforeTestExecutionCallback,
        AfterTestExecutionCallback {

    Logger logger = Logger.getLogger("DatabaseManagementExtension");

    @Override
    public void afterAll(ExtensionContext context) throws Exception {
        logger.info("===> afterAll callback");
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        logger.info("===> afterEach callback");
    }

    @Override
    public void beforeAll(ExtensionContext context) throws Exception {
        logger.info("===> beforeAll callback");
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        logger.info("===> beforeEach callback");
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        logger.info("===> afterTestExecution callback");

    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        logger.info("===> beforeTestExecution callback");
    }
}
