package com.mona.inventoryms;

import com.mona.inventoryms.security.RsaKeyProperties;
import com.mona.inventoryms.security.SpringSecurityAuditorAware;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class InventorymsApplication {

	@Bean
	public AuditorAware<String> auditorAware(){
		return new SpringSecurityAuditorAware();
	}

	public static void main(String[] args) {
		SpringApplication.run(InventorymsApplication.class, args);
	}

}
