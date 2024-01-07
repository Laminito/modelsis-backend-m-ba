package sn.modelsisbackendmba.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        CorsRegistration corsRegistration = registry.addMapping("/api/modelsis/**") // Spécifie le chemin à désactiver pour CORS
                .allowedOrigins("*") // Permet toutes les origines
                .allowedMethods("*") // Permet toutes les méthodes HTTP (GET, POST, PUT, DELETE, etc.)
                .maxAge(3600);// Durée en secondes pour la mise en cache des pré-remplissages de requêtes CORS (3600 secondes = 1 heure)
    }
}
