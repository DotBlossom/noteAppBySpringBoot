package project.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import project.dto.JoinDto;
import project.service.JoinService;

@RestController
public class JoinController {
	@Autowired
	JoinService joinService;
	
	@PostMapping("/join")
	public ResponseEntity<Object> join(@RequestBody JoinDto joinDto) throws Exception{
		
		Map<String, String> result = new HashMap<>();
		
		if (joinService.joinProcess(joinDto)) {
			
			result.put("code", HttpStatus.OK.toString());
			result.put("message" , "회원 가입 성공");
			return ResponseEntity.status(HttpStatus.OK).body(result);
			// front에서 redirect response by
		} else {
			// E code 받아서 리턴
			result.put("code", HttpStatus.BAD_REQUEST.toString());
			result.put("message" , "문제");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
		}
		
	}

	

}
