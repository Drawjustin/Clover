package com.ssafy.member.model.service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.member.model.MemberDto;
import com.ssafy.member.model.MemberModifyDto;
import com.ssafy.member.model.dao.MemberDao;
import com.ssafy.qnaboard.model.QnaBoardDto;
import com.ssafy.qnaboard.model.QnaBoardListDto;
import com.ssafy.tripboard.model.TripBoardDto;
import com.ssafy.tripboard.model.TripBoardListDto;
import com.ssafy.util.EmailService;

@Service
public class MemberServiceImpl implements MemberService{
		
	private final EmailService emailService;
	private final MemberDao memberDao;
	int passCnt = 0; //비밀번호 찾기를 유도하기 위한 count변수
	
	@Autowired
	public MemberServiceImpl(MemberDao memberDao, EmailService emailService) {
		super();
		this.memberDao = memberDao;
		this.emailService = emailService;
	}
	
	@Override
	public void registerUser(MemberDto memberDto) throws Exception{
		memberDao.insertUser(memberDto);
	}

	@Override
	public MemberDto login(MemberDto memberDto) {
		MemberDto loginMember = memberDao.login(memberDto);
		System.out.println(loginMember.toString());
		
		if(loginMember==null) {
			System.out.println("아이디를 못찾았습니다.");
			return null;
		}
		if(memberDto.getUserPass().equals(loginMember.getUserPass())) {
			System.out.println("로그인 성공");
			passCnt=0;
		}
		else {
			System.out.println("login fail");
			passCnt++;
			return null;
		}
		if(passCnt==5) {
			System.out.println("비밀번호 찾기를 이용하세요.");
			passCnt=0;
		}
		return loginMember;
		
	}

	@Override
	public MemberModifyDto modifyUser(MemberModifyDto memberModifyDto) {
		int modifyUser = memberDao.updateUser(memberModifyDto);
		if(modifyUser==0) {
			System.out.println("실패");
			return null;
		}
		else {
			System.out.println("성공");
			return memberModifyDto;
		}
	}

	@Override
	public void deleteUser(String userId) {
		memberDao.deleteUser(userId);
	}

	@Override
	public MemberDto findPassword(MemberDto memberDto) {
		MemberDto findMember = memberDao.selectFindPass(memberDto);
		
		if(findMember!=null && findMember.getUserQuestionResult().equals(memberDto.getUserQuestionResult())) {
			String newPass = RandomStringUtils.randomAlphanumeric(8);
			System.out.println("이메일 전송???");
	        try {
	        emailService.sendMail(newPass,findMember.getUserEmail());
	        }catch(Exception e) {
	        	System.out.println("이메일 처리중 에러");
	        	e.printStackTrace();
	        }
	        System.out.println(newPass);
			findMember.setUserPass(newPass);
			memberDao.updateNewPass(findMember);
			return findMember;
		}
		return null;
	}

	@Override
	public MemberDto findUserId(MemberDto memberDto) {
		return memberDao.selectFindId(memberDto);
	}

//	@Override
//	public MemberDto modifyNewPass(MemberDto memberDto) {
//		String newPass = RandomStringUtils.randomAlphanumeric(8); // 난수 생성
//	
//		
//		return newPass;
//	}

	@Override
	public TripBoardListDto selectLikeBoard(Map<String, String> map) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "1" : map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		String user_id = map.get("userId") == null ? "" : map.get("userId");
		param.put("start", start);
		param.put("list_size", sizePerPage);
		param.put("userId", user_id);
		System.out.println("---"+start+" "+sizePerPage);
		// key(검색 조건)이 user_id일 때
		String key = map.get("key");
		param.put("key", key == null ? "" : key);

		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "b.user_id");{
			int sortkey = Integer.parseInt(map.get("sortkey") == null ? "1" : map.get("sortkey"));
			param.put("sortkey", sortkey);
			
	
				
		}
		
			List<TripBoardDto> list = memberDao.selectLikeBoard(param);
		
		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "user_id");
		int totalArticleCount = memberDao.getTotalArticleCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;

		// list와 페이지 정보를 하나의 DTO에 담아서 리턴
		
		TripBoardListDto list2 = new TripBoardListDto();
		list2.setArticles(list);
		list2.setCurrentPage(currentPage);
		list2.setTotalPageCount(totalPageCount);
		
		
		System.out.println(list2.toString());
		return  list2;
	}

	@Override
	public void deleteLikeBoard(TripBoardDto tripboarddto) {
		memberDao.deleteLikeBoard(tripboarddto);
	}


	
	
	@Override
	public MemberDto userInfo(String userId) throws Exception {
		return memberDao.userInfo(userId);
	}

	@Override
	public void saveRefreshToken(String userId, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", refreshToken);
		memberDao.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userId) throws Exception {
		return memberDao.getRefreshToken(userId);
	}

	@Override
	public void deleRefreshToken(String userId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", userId);
		map.put("token", null);
		memberDao.deleteRefreshToken(map);
	}
	
	
	
	
	
	
	
	
}
