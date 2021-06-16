package it.uniroma3.siw.oscar.authentication;

import static it.uniroma3.siw.oscar.model.Credenziali.ADMIN_ROLE;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * The AuthConfiguration is a Spring Security Configuration.
 * It extends WebSecurityConfigurerAdapter, meaning that it provides the settings for Web security.
 */
@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter {

	/**
	 * The datasource is automatically injected into the AuthConfiguration (using its getters and setters)
	 * and it is used to access the DB to get the Credentials to perform authentiation and authorization
	 */
	@Autowired
	DataSource datasource;

	/**
	 * This method provides the whole authentication and authorization configuration to use.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http

				.authorizeRequests()
		
				.antMatchers(HttpMethod.GET, "/", "/index",  "/css/**", "/images/**", "/edizioni/**", "/edizione/**",
						"/categorie/**", "/categoriaA/**", "/categoriaF/**", "/categoriaFilm/**", "/categorieFilm/**",
						"/categoriaArtista/**",  "/categorieArtisti/**", "/film/**", "/films/**", "/artista/**",
						"/newFilm/**", "/artisti/**",  "/loginForm/**", "/registrazione/**", "/login/**", "/logout/**",
						"/addCommento/**","/deleteCommento/**" ).permitAll()
				.antMatchers(HttpMethod.POST, "/login/**", "/registrazione/**", "/artisti/**", "/film/**").permitAll()		
				.antMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
				.antMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
		
				.anyRequest().authenticated()		
		
				.and().formLogin()		
				.loginPage("/login")		
				.defaultSuccessUrl("/default")
		
				.and().logout()
				.logoutUrl("/logout")
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/index")   
		
				.invalidateHttpSession(true)
				.clearAuthentication(true).permitAll();
	}

	/**
	 * This method provides the SQL queries to get username and password.
	 * istruzioni a spring quando deve cercare le credenziali x verificare che il login sia corretto
	 * e dobbiamo specificare a spring dove si trovano le credenziali
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.jdbcAuthentication()
		.dataSource(this.datasource)
		.authoritiesByUsernameQuery("SELECT username, ruolo FROM credenziali WHERE username=?")
		.usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credenziali WHERE username=?");
	}

	/**
	 * This method defines a "passwordEncoder" Bean.
	 * The passwordEncoder Bean is used to encrypt and decrpyt the Credentials passwords.
	 * qui specifichiamo l'oggetto che deve essere utilizzato per codificare le psw
	 * @Bean èla radice di un componente di un'applicazione spring
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}