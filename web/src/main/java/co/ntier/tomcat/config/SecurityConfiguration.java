package co.ntier.tomcat.config;


import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;

import co.ntier.tomcat.security.UsernamePasswordAuthenticationProvider;

@Configuration
@ImportResource("/WEB-INF/config/security.xml")
public class SecurityConfiguration {

	@Bean(name="org.springframework.security.authenticationManager")
	public ProviderManager providerManager(){
		List<AuthenticationProvider> providers = new ArrayList<AuthenticationProvider>();
		providers.add( new UsernamePasswordAuthenticationProvider("default", "password", "ROLE_DEFAULT"));
		return new ProviderManager( providers );
	}
	
}
