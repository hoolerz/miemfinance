package com.hse.miemfinance.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.hse.miemfinance.model.dto.JwtDTO;
import com.hse.miemfinance.service.UserAuthenticationService;
import com.hse.miemfinance.service.UserService;
import com.hse.miemfinance.util.JwtUtil;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/login")
public class AuthenticationController {

	private final UserService userService;

	private final UserAuthenticationService userAuthenticationService;

	private final JwtUtil jwtUtil;

	private final AuthenticationManager authenticationManager;

	//TODO: Refactor
	@RequestMapping(method = RequestMethod.GET, value = "/callback")
	public ResponseEntity health(@RequestParam String code) throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpURLConnection connection = getAccessTokenRequestConnection(code);
		InputStream inputStream;
		if (connection.getResponseCode() >= HttpURLConnection.HTTP_BAD_REQUEST) {
			inputStream = connection.getErrorStream();
			String errorDescription = new JSONObject(IOUtils.toString(inputStream, StandardCharsets.UTF_8)).getString(
					"error_description");
			String SSO_ERROR = "Error while performing sso request: ";
			return ResponseEntity.status(418).body(SSO_ERROR + errorDescription);
		}
		inputStream = connection.getInputStream();
		String idToken = new JSONObject(IOUtils.toString(inputStream, StandardCharsets.UTF_8)).getString(
				"id_token");
		client.close();
		DecodedJWT decodedIdToken = JWT.decode(idToken);
		String username = decodedIdToken.getClaim("preferred_username").asString().split("@")[0];
		String name = decodedIdToken.getClaim("name").asString();
		String email = decodedIdToken.getClaim("email").asString();
		if (!userService.isPresent(username)) {
			userService.register(username, email, name);
		}
		UserDetails userDetails = userAuthenticationService.loadUserByUsername(username);
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities()));
		//userAuthenticationService.authenticateAndSave(userDetails, new WebAuthenticationDetailsSource().buildDetails(request));
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
		//usernamePasswordAuthenticationToken.setDetails(details);
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		final String token = jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok().body(new JwtDTO(token, username));
	}

	private HttpURLConnection getAccessTokenRequestConnection(String authenticationCode) throws Exception {
		String charset = StandardCharsets.UTF_8.name();
		HttpURLConnection connection = (HttpURLConnection)
				new URL("https://login.microsoftonline.com/common/oauth2/v2.0/token").openConnection();
		connection.setDoOutput(true);
		connection.setRequestProperty("Accept-Charset", charset);
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
		Map<String, Object> params = new LinkedHashMap<>();
		params.put("grant_type", "authorization_code");
		params.put("scope","openid email profile");
		params.put("code", authenticationCode);
		params.put("client_id", "e0298532-643b-40af-a866-a309039151ea");
		//params.put("client_secret", "WeM7Q~tWCkIUdWhD0hxGEhTVtbKgNT3tgelI-");
		params.put("redirect_uri", "miem-invest://oauth/callback");
		params.put("name", "Freddie");
		StringBuilder postData = new StringBuilder();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			if (postData.length() != 0) {
				postData.append('&');
			}
			postData.append(URLEncoder.encode(param.getKey(), charset));
			postData.append('=');
			postData.append(URLEncoder.encode(String.valueOf(param.getValue()), charset));
		}
		byte[] postDataBytes = postData.toString().getBytes(charset);
		OutputStream output = connection.getOutputStream();
		output.write(postDataBytes);
		return connection;
	}

}
