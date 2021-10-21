package com.pintailconsultingllc;

import com.pintailconsultingllc.models.CompanyModel;
import com.pintailconsultingllc.testing.extensions.CompanyModelInjectionExtension;
import com.pintailconsultingllc.testing.extensions.DatabaseManagementExtension;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({
        CompanyModelInjectionExtension.class,
        DatabaseManagementExtension.class
})
@DisplayName("Jupiter extensions demonstration")
public class JupiterExtensionDemonstrationTests {

    CompanyModel companyModel;

    public void setCompanyModel(CompanyModel companyModel) {
        this.companyModel = companyModel;
    }

    @Test
    @DisplayName("verify companyModel is being injected")
    void companyModelInjectionTest() {
        assertNotNull(companyModel);
    }

}
