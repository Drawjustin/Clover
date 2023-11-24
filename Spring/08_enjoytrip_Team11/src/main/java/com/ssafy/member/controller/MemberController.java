package com.ssafy.member.controller;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.MemberModifyDto;
import com.ssafy.member.model.service.MemberService;
import com.ssafy.tripboard.model.TripBoardDto;
import com.ssafy.util.JWTUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
@CrossOrigin(origins = { "*" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.POST, RequestMethod.DELETE }, maxAge = 6000)
@Controller
@RequestMapping("/user")
public class MemberController {
	private MemberService memberService;
	private JWTUtil jwtUtil;

	public MemberController(MemberService memberService, JWTUtil jwtUtil) {
		super();
		this.memberService = memberService;
		this.jwtUtil = jwtUtil;
	}

	//회원등록
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody MemberDto memberDto){
		System.out.println("들어옴?");
		try {
			memberService.registerUser(memberDto);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<String>("중복된 아이디입니다.",HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}
	
	
	
	
	//로그인
	@PostMapping("/login")
	public ResponseEntity<Map<String,Object>>login(@RequestBody MemberDto memberDto){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			System.out.println(memberDto.toString());
			MemberDto loginUser = memberService.login(memberDto);
			if(loginUser != null) {
				System.out.println(loginUser.toString());
				String accessToken = jwtUtil.createAccessToken(loginUser.getUserId());
				String refreshToken = jwtUtil.createRefreshToken(loginUser.getUserId());
//				발급받은 refresh token을 DB에 저장.
				memberService.saveRefreshToken(loginUser.getUserId(), refreshToken);	
//				JSON으로 token 전달.
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				
				status = HttpStatus.CREATED;
			} else {
				resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
				status = HttpStatus.UNAUTHORIZED;
			} 
			
		} catch (Exception e) {
			System.out.println("로그인실패");
			e.printStackTrace();
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	
	
	
	//로그인
//	@PostMapping("/login")
//	public ResponseEntity<Map<String,Object>>login(@RequestBody MemberDto memberDto){
//		MemberDto loginMember = memberService.login(memberDto);
//		if(loginMember==null) { //로그인실패
//			return new ResponseEntity<Map<String, Object>>(HttpStatus.UNAUTHORIZED); //로그인을 실패했다는 걸 fe에서 받
//		}
//		else {
//			Map<String, Object> resBody = new HashMap<String, Object>();
//			resBody.put("userId", loginMember.getUserId()); // FE에 보내줄 body
//			return ResponseEntity.ok(resBody);
//		}
//	}
	
	//회원수정
	@PutMapping("/update")
	public ResponseEntity<?>modifyUser(@RequestBody MemberModifyDto memberModifyDto){
		System.out.println("1");
		MemberModifyDto updateMember = memberService.modifyUser(memberModifyDto);
		System.out.println(updateMember.getUserId());
		System.out.println(updateMember.getUserPass());
		return new ResponseEntity<Map<String, Object>>(HttpStatus.OK);
	}
	
	//회원삭제
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<?>deleteUser(@PathVariable("userId") String userId){
		memberService.deleteUser(userId);
		return new ResponseEntity<Map<String, Object>>(HttpStatus.OK);
	}
	
	//비밀번호 찾기
	@GetMapping("/findPass")
	public ResponseEntity<?>findPass(@RequestParam String userId, @RequestParam String userEmail, @RequestParam String userQuestionResult){
		MemberDto memberDto = new MemberDto();
		memberDto.setUserId(userId);
		memberDto.setUserEmail(userEmail);
		memberDto.setUserQuestionResult(userQuestionResult);
		MemberDto resultmember = memberService.findPassword(memberDto);

		if(resultmember==null) {
			return null;
		}

		return new ResponseEntity<MemberDto>(resultmember,HttpStatus.OK);
	}
	
	//아이디 찾기
	@GetMapping("/findId")
	public ResponseEntity<?>findId(@RequestParam String userName, @RequestParam String userEmail){
		System.out.println(111);
		MemberDto memberDto = new MemberDto();
		memberDto.setUserName(userName);
		memberDto.setUserEmail(userEmail);
		MemberDto result = memberService.findUserId(memberDto);
		if(result==null) {
			return null;
		}
		
		return new ResponseEntity<MemberDto>(result, HttpStatus.OK);
	}
	// 마이페이지 좋아요 순으로 갖고오기
	@GetMapping("/Mylikepage")
	public ResponseEntity<?>Mylikepagefind(@RequestParam Map<String, String> map){
//		Map<String,String> map2 = new HashMap<>();
//		map2.put("userId", "user1");
//		map2.put("sortkey","2");
//		System.out.println(map2.toString());
		System.out.println(map.toString());
		return new ResponseEntity<>(memberService.selectLikeBoard(map),HttpStatus.OK);
	}
	@DeleteMapping("/Mylikepage")
	public ResponseEntity<?>Mylikepagedelete(@ModelAttribute TripBoardDto tripboarddto){
		System.out.println(tripboarddto.toString());
		memberService.deleteLikeBoard(tripboarddto);
		return new ResponseEntity<>(HttpStatus.OK);
	}	
	
	
	
	
	
	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{userId}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("userId") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userId,
			HttpServletRequest request) {
//		logger.debug("userId : {} ", userId);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		if (jwtUtil.checkToken(request.getHeader("Authorization"))) {
		try {
//				로그인 사용자 정보.
				MemberDto memberDto = memberService.userInfo(userId);
				memberDto.setUserPass("0");
				resultMap.put("userInfo", memberDto);
				status = HttpStatus.OK;
			} catch (Exception e) {
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
	@GetMapping("/logout/{userId}")
	public ResponseEntity<?> removeToken(@PathVariable ("userId") @ApiParam(value = "로그아웃할 회원의 아이디.", required = true) String userId) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			memberService.deleRefreshToken(userId);
			status = HttpStatus.OK;
		} catch (Exception e) {
				resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

	@ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody MemberDto memberDto, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refreshToken");
		if (jwtUtil.checkToken(token)) {
			if (token.equals(memberService.getRefreshToken(memberDto.getUserId()))) {
				String accessToken = jwtUtil.createAccessToken(memberDto.getUserId());
				resultMap.put("access-token", accessToken);
				status = HttpStatus.CREATED;
			}
		} else {
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
