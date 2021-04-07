package com.purple.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.purple.demo.handler.LoginFailHandler;
import com.purple.demo.handler.LoginSuccessHandler;
import com.purple.demo.security.CustomOAuth2Provider;
import com.purple.demo.security.CustomOAuth2UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true) //secured 어노테이션 활성화, 특정메소드에 간단하게 권한을 걸고 싶을때 , prePostEnabled => preAuthorize, PostAuthorize 어노테이션 활성화
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	
		@Autowired
		private UserDetailsService userService;

		/*@Autowired
		private final CustomOAuth2UserService customOAuth2UserService;
		*/
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
		@Override
		public void configure(WebSecurity web) throws Exception {
			web.ignoring().antMatchers("/resources/**");
			
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable();
		
			http.authorizeRequests()
				.antMatchers("/bookmark/**", "/layout/**", "/search/**", "/userpage/**","/mypage/**", "/feed/**", "/notice/**").hasAnyRole("USER","ADMIN")			
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/**").permitAll()
				.and()
					.oauth2Login()
					.loginPage("/welcome")
					.defaultSuccessUrl("/feed");
					//.userInfoEndpoint();
					//.userService(service);
			
			http.formLogin()
					.loginPage("/welcome")
					.loginProcessingUrl("/login")  //이 주소가 호출이되면 시쿠리티가 낚아채서 대신 로그인 해줌
					.defaultSuccessUrl("/feed")  //로그인 성공시
					.usernameParameter("user_id")  //username값을 user_id 로 받아옴
					.passwordParameter("user_pw")
					.successHandler(new LoginSuccessHandler())
					.failureHandler(new LoginFailHandler());
					
			
			http.logout()
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
					.logoutSuccessUrl("/welcome")
					.invalidateHttpSession(true) // 세션 제거
					.deleteCookies("JSESSIONID") // 쿠키 제거
					.clearAuthentication(true); // 권한정보 제거 
	
			http.sessionManagement()
					.maximumSessions(1) // 최대 세션 허용 수 
					.maxSessionsPreventsLogin(true) // 중복 로그인 시 x
					.expiredUrl("/welcome"); // 세션 만료 또는 중복 시 리다이렉트되는 url

//					.sessionRegistry(null); 
			
			http.exceptionHandling()
				.accessDeniedPage("/welcome");
		}
		
		@Bean
		public SessionRegistry sessionRegistry() {
			return new SessionRegistryImpl();
		}

		@Bean
		public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
			return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(
					new HttpSessionEventPublisher());
		}
		
		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
		}
		
		@Bean
		public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties,
				@Value("${custom.oauth2.kakao.client-id}") String kakaoClientId,
				@Value("${custom.oauth2.kakao.client-secret}") String kakaoClientSecret,
				@Value("${custom.oauth2.naver.client-id}") String naverClientId,
				@Value("${custom.oauth2.naver.client-secret}") String naverClientSecret) {
			
			List<ClientRegistration> registrations = oAuth2ClientProperties.getRegistration().keySet().stream()
					.map(client -> getRegistration(oAuth2ClientProperties, client))
					.filter(Objects::nonNull)
					.collect(Collectors.toList());
			registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
					.clientId(kakaoClientId)
					.clientSecret(kakaoClientSecret)				
					.build()
			);		
			registrations.add(CustomOAuth2Provider.NAVER.getBuilder("naver")
					.clientId(naverClientId)
					.clientSecret(naverClientSecret)				
					.build()
			);
			return new InMemoryClientRegistrationRepository(registrations);
		}

		private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client) {
			if ("google".equals(client)) {
				OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("google");
				return CommonOAuth2Provider.GOOGLE.getBuilder(client)
						.clientId(registration.getClientId())
						.clientSecret(registration.getClientSecret())
						.scope("email")
						.build();
			} else if ("facebook".equals(client)) {
				OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("facebook");
				return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
						.clientId(registration.getClientId())
						.clientSecret(registration.getClientSecret())
						.userInfoUri("https://graph.facebook.com/me?fields=id,name,email,link")
						.scope("email")
						.build();
			}
			return null;
		}
}