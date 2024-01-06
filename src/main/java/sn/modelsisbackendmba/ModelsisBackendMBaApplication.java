package sn.modelsisbackendmba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ModelsisBackendMBaApplication {

    public static void main(String[] args) {
        System.setProperty("server.servlet.context-path", "/api/modelsis");
        SpringApplication.run(ModelsisBackendMBaApplication.class, args);
    }

}
