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
//import static it.uniroma3.siw.spring.model.Credentials.DEFAULT_ROLE;

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
                // authorization paragraph: qui definiamo chi può accedere a cosa
                .authorizeRequests()
                // chiunque (autenticato o no) può accedere alle pagine index, login, register, ai css e alle immagini
                //cioè stiamo dicendo che possiamo accedere a /, ovvero la pagina iniziale, a /index, /login, e anche le risorse SOTTO i path css e images
                // log e register sono i pattern che associeremo alla form di login e accesso
                .antMatchers(HttpMethod.GET, "/", "/index",  "/css/**", "/images/**", "/edizioni/**", "/edizione/**",
                							  "/categorie/**", "/categoriaA/**", "/categoriaF/**", "/categoriaFilm/**", "/categorieFilm/**",
                							  "/categoriaArtista/**",  "/categorieArtisti/**", "/film/**", "/films/**", "/artista/**",
                							  "/newFilm/**", "/artisti/**",  "/loginForm/**", "/registrazione/**", "/login/**", "/logout/**").permitAll()
                // chiunque (autenticato o no) può mandare richieste POST al punto di accesso per login e register 
                .antMatchers(HttpMethod.POST, "/login/**", "/registrazione/**", "/artisti/**", "/film/**").permitAll()
                // solo gli utenti autenticati con ruolo ADMIN possono accedere a risorse con path /admin/**
                .antMatchers(HttpMethod.GET, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
                .antMatchers(HttpMethod.POST, "/admin/**").hasAnyAuthority(ADMIN_ROLE)
                // tutti gli utenti autenticati possono accere alle pagine rimanenti 
                .anyRequest().authenticated()

                // login paragraph: qui definiamo come è gestita l'autenticazione
                // usiamo il protocollo formlogin 
      /*l'autenticazione potremmo chiederla anche ad un ente che ci garantisce l'autenticazione esterna (es google)*/
                .and().formLogin()
                // la pagina di login si trova a /login
                // NOTA: Spring gestisce il post di login automaticamente
                //cioè quando premiamo il bottone login è il framework che gestisce i dati
                .loginPage("/login")
                // se il login ha successo, si viene rediretti al path /default
                .defaultSuccessUrl("/default")

                // logout paragraph: qui definiamo il logout
                .and().logout()
                // il logout è attivato con una richiesta GET a "/logout"
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // in caso di successo, si viene reindirizzati alla /index page
                .logoutSuccessUrl("/index")   
     /*la sessione viene cancellata: spring si occupa di mantenere in sessione i dati che permettono 
      * di riconoscere l'utente già utenticato, ma quando facciamo il logout i dati vengono cancellati*/
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
    	/*noi sappiamo che stiamo salvando le credenziali in una tabella "credencials"
    	 * perciò con la concatenazione di chiamate di metodi specifichiamo qual è la sorgente dati,
    	 * che viene trovata da sola a partire dalle informazioni che abbiamo definito nel file di configurazione, le stesse
    	 * che abbiamo usato per specificare il database e dobbiamo specificare quali sono le query per recuperare
    	 * username ruolo e psw
    	 * query sql che corrispondono alle colonne della tabella credentionals*/
        auth.jdbcAuthentication()
                //use the autowired datasource to access the saved credentials
                .dataSource(this.datasource)
                //retrieve username and role
                .authoritiesByUsernameQuery("SELECT username, ruolo FROM credenziali WHERE username=?")
                //retrieve username, password and a boolean flag specifying whether the user is enabled or not (always enabled in our case)
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