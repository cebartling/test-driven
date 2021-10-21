package com.pintailconsultingllc.testing.extensions;

import com.pintailconsultingllc.models.CompanyModel;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

public class CompanyModelInjectionExtension implements TestInstancePostProcessor {

    @Override
    public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
        testInstance.getClass().getMethod("setCompanyModel", CompanyModel.class)
                .invoke(testInstance, new CompanyModel("Tesla Inc."));
    }
}
