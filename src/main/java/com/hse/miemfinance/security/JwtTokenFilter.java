package com.hse.miemfinance.security;

import com.hse.miemfinance.service.UserAuthenticationService;
import com.hse.miemfinance.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
// Spring will add it into the Context regardless of it being present in the SecurityConfig
@ConditionalOnProperty(prefix = "miemfinance", name = "sso-enabled", havingValue = "true")
public class JwtTokenFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	private final UserAuthenticationService userAuthenticationService;

	private final Set<String> skipUrls = new HashSet<>(Set.of("/api/login/callback", "/error"));

	private final AntPathMatcher pathMatcher = new AntPathMatcher();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		final String requestTokenHeader = request.getHeader("Authorization");

		String username = null;
		String jwtToken = null;
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.replace("Bearer ", "");
			try {
				username = jwtUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				//log.info("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				//log.info("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}

		if (jwtUtil.isTokenExpired(jwtToken)) {
			response.setStatus(418);
			response.setHeader("location", "/auth/refreshtoken");
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.write("Access Token has expired".getBytes(StandardCharsets.UTF_8));
			outputStream.flush();
			outputStream.close();
		}

		// Once we get the token validate it.
		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = userAuthenticationService.loadUserByUsername(username);

			// if token is valid configure Spring Security to manually set
			// authentication
			if (Boolean.TRUE.equals(jwtUtil.validateToken(jwtToken, userDetails))) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
				//userAuthenticationService.authenticateAndSave(userDetails, new WebAuthenticationDetailsSource().buildDetails(request));
			}
		}

		chain.doFilter(request, response);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		return skipUrls.stream().anyMatch(p -> pathMatcher.match(p, request.getRequestURI()));
	}

}
