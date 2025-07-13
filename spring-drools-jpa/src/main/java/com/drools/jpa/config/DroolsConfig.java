package com.drools.jpa.config;

import org.kie.api.KieServices;
import org.kie.api.builder.*;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

@Configuration
public class DroolsConfig {

    private static final String RULES_PATH = "rules";
    private final KieServices kieServices = KieServices.Factory.get();


    @Bean
    public KieContainer kieContainer() throws IOException {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        for (String drlFile : getRuleFiles(RULES_PATH)) {
            kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_PATH + "/" + drlFile));
        }
        // fetch rule from file. Same way we can fetch from DB column where the rule is stored in string format
        kieFileSystem.write("src/main/resources/" + RULES_PATH + "/orders-discount-icici.drl",
                ResourceFactory.newByteArrayResource(ICICI_DRL_RULE.getBytes()));
        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
        kb.buildAll();
        KieModule kieModule = kb.getKieModule();
        KieContainer kieContainer = kieServices.newKieContainer(kieModule.getReleaseId());
        return kieContainer;
    }

    private String[] getRuleFiles(String rulesPath) throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(rulesPath);
        if (url == null) {
            throw new IOException("Rules folder not found: " + rulesPath);
        }
        File rulesFolder = new File(url.getFile());
        return Objects.requireNonNull(rulesFolder.list((dir, name) -> name.endsWith(".drl")));
    }

    @Bean
    public KieSession kieSession(KieContainer kieContainer) {
        return kieContainer.newKieSession();
    }

    private static final String ICICI_DRL_RULE = """
            package rules
            
            import com.drools.jpa.model.Order
            
            rule "ICICI - 25% Discount"
            agenda-group "orders-discount-icici"
            when
                $order: Order(price > 300 && discount == null)
            then
                $order.setDiscount(25.0);
                System.out.println("Applied 25% ICICI Discount");
            end
            """;
}
