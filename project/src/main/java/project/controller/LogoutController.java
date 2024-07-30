package project.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class LogoutController {
	
	@GetMapping("/logout")
	public ResponseEntity<Object> logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Map<String, String> result = new HashMap<>();
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			new SecurityContextLogoutHandler().logout(request, response, authentication);
			result.put("code", HttpStatus.OK.toString());
			result.put("message" , "logout has been completed");
			return ResponseEntity.status(HttpStatus.OK).body(result);
		}
		
		result.put("code", HttpStatus.BAD_REQUEST.toString());
		result.put("message" , "badRequest");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
	}

	
}
