package com.ssafy.qnaboard.model.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.qnaboard.model.QnaArticleCommentDto;
import com.ssafy.qnaboard.model.QnaBoardDto;
import com.ssafy.qnaboard.model.QnaBoardListDto;
import com.ssafy.qnaboard.model.dao.QnaBoardDao;

@Service
public class QnaBoardServiceImpl implements QnaBoardService {
	private QnaBoardDao qnaBoardDao;

	@Autowired
	public QnaBoardServiceImpl(QnaBoardDao qnaBoardDao) {
		super();
		this.qnaBoardDao = qnaBoardDao;
	}

	@Override
	public void writeArticle(QnaBoardDto qnaBoardDto) throws Exception {
		qnaBoardDao.insertArticle(qnaBoardDto);
	}

	@Override
	public QnaBoardDto getArticle(int qnaArticleId) throws Exception {
		return qnaBoardDao.selectArticle(qnaArticleId);
	}

	@Override
	public QnaBoardListDto listArticle(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("list_size", sizePerPage);

		// key(검색 조건)이 user_id일 때
		String key = map.get("key");
		param.put("key", key == null ? "" : key);

		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "b.user_id");
		List<QnaBoardDto> list = qnaBoardDao.selectArticleList(param);

		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "user_id");
		int totalArticleCount = qnaBoardDao.getTotalArticleCount(param);
		
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;

		// list와 페이지 정보를 하나의 DTO에 담아서 리턴
		QnaBoardListDto qnaBoardListDto = new QnaBoardListDto();
		qnaBoardListDto.setArticles(list);
		qnaBoardListDto.setCurrentPage(currentPage);
		qnaBoardListDto.setTotalPageCount(totalPageCount);

		return qnaBoardListDto;
	}

	@Override
	public void updateHit(int qnaArticleId) throws Exception {
		qnaBoardDao.updateHit(qnaArticleId);
	}

	@Override
	public void modifyArticle(QnaBoardDto qnaBoardDto) throws Exception {
		qnaBoardDao.updateArticle(qnaBoardDto);
	}

	@Override
	public void removeArticle(int qnaArticleId) throws Exception {
		qnaBoardDao.deleteArticle(qnaArticleId);
	}

	@Override
	public List<QnaArticleCommentDto> listArticleComment(int qnaArticleId) throws Exception {
		return qnaBoardDao.selectArticleCommentList(qnaArticleId);
	}

	@Override
	public void writeArticleComment(QnaArticleCommentDto qnaArticleCommentDto) throws Exception {
		System.out.println(qnaArticleCommentDto);
		qnaBoardDao.insertArticleComment(qnaArticleCommentDto);
	}

	@Override
	public void deleteArticleComment(int qnaArticleCommentId) throws Exception {
		qnaBoardDao.deleteArticleComment(qnaArticleCommentId);
	}

	@Override
	public void modifyArticleComment(QnaArticleCommentDto qnaArticleCommentDto) throws Exception {
		qnaBoardDao.updateArticleComment(qnaArticleCommentDto);
	}

}
