package com.hse.miemfinance.config.security;

import com.hse.miemfinance.security.JwtTokenFilter;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTParser;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoderFactory;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
@ConditionalOnProperty(prefix = "miemfinance", name = "sso-enabled", havingValue = "true")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final JwtTokenFilter jwtTokenFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.cors()
				.and()
				.csrf().disable();
		http
				.addFilterBefore(
						jwtTokenFilter,
						UsernamePasswordAuthenticationFilter.class
				);
		http
				.authorizeRequests()
				.antMatchers("/api/**").permitAll()
				.antMatchers("/**").authenticated();
		http
				.oauth2Login()
				.defaultSuccessUrl("/health")
				.redirectionEndpoint()
				.baseUri("/api/login/oauth2/code/")
				.and()
				.tokenEndpoint()
				.accessTokenResponseClient(accessTokenResponseClient());
	}

	@Bean
	public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
		DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();
		return accessTokenResponseClient;
	}

	@Bean
	public JwtDecoderFactory<ClientRegistration> jwtDecoderFactory() {

		final JwtDecoder decoder = new JwtDecoder() {

			@SneakyThrows
			@Override
			public Jwt decode(String token) throws JwtException {
				JWT jwt = JWTParser.parse(token);
				return createJwt(token, jwt);
			}

			private Jwt createJwt(String token, JWT parsedJwt) {
				try {
					Map<String, Object> headers = new LinkedHashMap<>(parsedJwt.getHeader().toJSONObject());
					Map<String, Object> claims = new HashMap<>(parsedJwt.getJWTClaimsSet().getClaims());
					Date iat = (Date) claims.get("iat");
					claims.put("iat", iat.toInstant());
					Date exp = (Date) claims.get("exp");
					claims.put("exp", exp.toInstant());
					return Jwt.withTokenValue(token)
							.headers(h -> h.putAll(headers))
							.claims(c -> c.putAll(claims))
							.build();
				} catch (Exception ex) {
					if (ex.getCause() instanceof ParseException) {
						throw new JwtException(String.format("An error occurred while attempting to decode the Jwt: %s", "Malformed payload"));
					} else {
						throw new JwtException(String.format("An error occurred while attempting to decode the Jwt: %s", ex.getMessage()), ex);
					}
				}
			}
		};
		return context -> decoder;
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.addAllowedOrigin("*");
		configuration.addAllowedHeader("*");
		configuration.addAllowedMethod("*");
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

}
