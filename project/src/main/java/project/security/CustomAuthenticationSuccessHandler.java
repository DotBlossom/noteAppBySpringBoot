package project.security;



import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import project.common.JwtUtils;
import project.entity.UserEntity;
import project.repository.UserRepository;
@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private JwtUtils jwtUtils;



	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		UserDetails userDetails = (UserDetails)authentication.getPrincipal();
		UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
		
		// 로그인 성공 후 수행할 작업들을 기술
		// 예) 데이터베이스에 접속 로그를 기록, 알림 이메일을 발송, ...
		
		String jwtToken = jwtUtils.generateToken(userEntity);
		log.debug(jwtToken);
		
		// 세션에 사용자 정보를 저장
		//request.getSession().setAttribute("user", userEntity);

		// 응답 헤더에 생성한 토큰을 설정
		response.setHeader("token", jwtToken);

		// 리다이렉트 
		response.sendRedirect("/home");
	}


}
