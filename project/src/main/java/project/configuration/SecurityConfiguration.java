package project.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import project.security.CustomAuthenticationSuccessHandler;
import project.security.JwtRequestFilter;


@Configuration
public class SecurityConfiguration {
	
	@Autowired
	private CustomAuthenticationSuccessHandler successHandler;
	
	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((auth) -> auth
				.requestMatchers("/", "/login", "/home", "/join", "/joinProc","/swagger-ui/**", "/v3/api-docs/**", "/my-api-docs/**", "/jpa/**").permitAll()	
				.requestMatchers("/admin").hasRole("ADMIN")
				//.requestMatchers("/jpa/**").hasAnyRole("ADMIN", "USER")
				.anyRequest().authenticated()
			);
		http
			.formLogin((auth) -> auth 
				.loginPage("/login")
				.loginProcessingUrl("/loginProc")
				.permitAll()
				// .defaultSuccessUrl("/board/openBoardList.do")
				.successHandler(successHandler)
			);
		
	    http
		 	.csrf((auth) -> auth.disable());
		
		http
			.sessionManagement((auth) -> auth
				.sessionFixation((sessionFixation) -> sessionFixation
					.newSession()
					.maximumSessions(1)
					.maxSessionsPreventsLogin(true))
			);
		
		http
			.logout((auth) -> auth
				.logoutUrl("/logout")
				.logoutSuccessUrl("/")
			);
		
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	@Bean
	BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
