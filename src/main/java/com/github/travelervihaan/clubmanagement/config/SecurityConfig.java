package com.github.travelervihaan.clubmanagement.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private DataSource dataSource;

	@Value("${spring.queries.users-query}")
	private String usersQuery;

	@Value("${spring.queries.roles-query}")
	private String rolesQuery;
/*
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.
				jdbcAuthentication()
				.usersByUsernameQuery(usersQuery)
				.authoritiesByUsernameQuery(rolesQuery)
				.dataSource(dataSource)
				.passwordEncoder(bCryptPasswordEncoder);
	}
	*/


	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception{
		httpSecurity
				.csrf().disable()
				.authorizeRequests()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/manager/**").hasRole("MANAGER")
				//.anyRequest().authenticated()
				.and()
				.formLogin().permitAll()
				.loginPage("/login").permitAll()
				.failureUrl("/login?error=true")
				.defaultSuccessUrl("/")
				.usernameParameter("username")
				.passwordParameter("password")
				.and().logout()
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				.logoutSuccessUrl("/login")
				.and().exceptionHandling().accessDeniedPage("/denied");
	}

/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
				.antMatchers("/logout").permitAll()
				.antMatchers("/employee/**").hasAnyRole("USER", "ADMIN")
				.antMatchers("/admin/**").hasRole("ADMIN")
				.and().logout().permitAll()
				.and().exceptionHandling().accessDeniedHandler((httpServletRequest, httpServletResponse, e) -> httpServletResponse.sendRedirect("/login?error"))
				.and().formLogin().permitAll();
	}
*/
	/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			authorizeRequests().antMatchers("/","/index").permitAll()
			.anyRequest().authenticated()
			//.and().authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
			.and().formLogin().loginPage("/login").permitAll()
			.and().logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll();
	}

	 */

	@Override
	public void configure(WebSecurity web){
		web
				.ignoring()
				.antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}
}
