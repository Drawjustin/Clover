package com.ssafy.qnaboard.controller;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.qnaboard.model.QnaArticleCommentDto;
import com.ssafy.qnaboard.model.QnaBoardDto;
import com.ssafy.qnaboard.model.QnaBoardListDto;
import com.ssafy.qnaboard.model.service.QnaBoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = { "*" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.POST, RequestMethod.DELETE }, maxAge = 6000)
@RestController
@RequestMapping("/qnaboard")
@Api("QNA 게시판 컨트롤러  API V1")
public class QnaBoardController {
	private static final Logger logger = LoggerFactory.getLogger(QnaBoardController.class);

	private QnaBoardService qnaBoardService;

	public QnaBoardController(QnaBoardService qnaBoardService) {
		super();
		this.qnaBoardService = qnaBoardService;
	}

	@ApiOperation(value = "QnA 글작성", notes = "새로운 게시글 정보를 입력한다.")
	@PostMapping
	public ResponseEntity<Void> writeArticle(
			@RequestBody @ApiParam(value = "QnA 정보.", required = true) QnaBoardDto qnaBoardDto) {
		logger.info("writeArticle qnaBoardDto - {}", qnaBoardDto);

		try {
			qnaBoardService.writeArticle(qnaBoardDto);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}

	@ApiOperation(value = "QnA 글보기", notes = "QnA id에 해당하는 게시글의 정보를 반환한다.", response = QnaBoardDto.class)
	@GetMapping("/{qnaArticleId}")
	public ResponseEntity<QnaBoardDto> getArticle(
			@PathVariable("qnaArticleId") @ApiParam(value = "얻어올 글의 id.", required = true) int qnaArticleId)
			throws Exception {
		logger.info("getArticle - 호출 : " + qnaArticleId);
		qnaBoardService.updateHit(qnaArticleId);
		return new ResponseEntity<QnaBoardDto>(qnaBoardService.getArticle(qnaArticleId), HttpStatus.OK);
	}

	@ApiOperation(value = "수정 할 QnA 글 얻기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = QnaBoardDto.class)
	@GetMapping("/modify/{qnaArticleId}")
	public ResponseEntity<QnaBoardDto> getModifyArticle(
			@PathVariable("qnaArticleId") @ApiParam(value = "얻어올 글의 글번호.", required = true) int qnaArticleId)
			throws Exception {
		logger.info("getModifyArticle - 호출 : " + qnaArticleId);
		return new ResponseEntity<QnaBoardDto>(qnaBoardService.getArticle(qnaArticleId), HttpStatus.OK);
	}

	@ApiOperation(value = "QnA 글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@ApiResponses({ @ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
			@ApiResponse(code = 500, message = "서버에러!!") })
	@GetMapping
	public ResponseEntity<QnaBoardListDto> listArticle(
			@RequestParam @ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) Map<String, String> map) {
		System.out.println(map.toString());
		logger.info("listArticle map - {}", map);
		try {
			QnaBoardListDto qnaBoardListDto = qnaBoardService.listArticle(map);
			HttpHeaders header = new HttpHeaders();
			header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
			return ResponseEntity.ok().headers(header).body(qnaBoardListDto);
		} catch (Exception e) {
//			return exceptionHandling(e);
			return null;
		}
	}

	@ApiOperation(value = "QnA 게시글 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/{qnaArticleId}")
	public ResponseEntity<String> deleteArticle(
			@PathVariable("qnaArticleId") @ApiParam(value = "살제할 글의 글번호.", required = true) int qnaArticleId)
			throws Exception {
		logger.info("deleteArticle - 호출");
		qnaBoardService.removeArticle(qnaArticleId);
		return ResponseEntity.ok().build();

	}

	@ApiOperation(value = "게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping
	public ResponseEntity<String> modifyArticle(
			@RequestBody @ApiParam(value = "수정할 글정보.", required = true) QnaBoardDto qnaBoardDto) throws Exception {
		logger.info("modifyArticle - 호출 {}", qnaBoardDto);

		qnaBoardService.modifyArticle(qnaBoardDto);
		return ResponseEntity.ok().build();
	}

	// 댓글 관련

	@ApiOperation(value = "댓글 목록", notes = "게시글 번호에 해당하는 댓글목록을 갖고온다..", response = QnaArticleCommentDto.class)
	@GetMapping("/comment/{qnaArticleId}")
	public ResponseEntity<List<QnaArticleCommentDto>> getArticleComment(
			@PathVariable("qnaArticleId") @ApiParam(value = "얻어올 게시글 글번호.", required = true) int qnaArticleId)
			throws Exception {
		logger.info("getArticleComment - 호출 : " + qnaArticleId);
		HttpHeaders header = new HttpHeaders();
		header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		return new ResponseEntity<List<QnaArticleCommentDto>>(qnaBoardService.listArticleComment(qnaArticleId),
				HttpStatus.OK);
	}

	@ApiOperation(value = "댓글작성", notes = "새로운 댓글 정보를 입력한다.")
	@PostMapping(value = "/comment")
	public ResponseEntity<?> writeArticleComment(
			@RequestBody @ApiParam(value = "댓글 정보.", required = true) QnaArticleCommentDto qnaArticleCommentDto) {
		logger.info("writeArticleComment boardCommentDto - {}", qnaArticleCommentDto);
		System.out.println("in writeArticleComment");

		try {
			qnaBoardService.writeArticleComment(qnaArticleCommentDto);
			return new ResponseEntity<Void>(HttpStatus.CREATED);
		} catch (Exception e) {
			return null;
//		return exceptionHandling(e);
		}
	}

	@ApiOperation(value = "댓글수정", notes = "수정할 댓글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping(value = "/comment")
	public ResponseEntity<String> modifyArticleComment(
			@RequestBody @ApiParam(value = "수정할 댓글정보.", required = true) QnaArticleCommentDto qnaArticleCommentDto)
			throws Exception {
		logger.info("modifyArticleComment - 호출 {}", qnaArticleCommentDto);

		qnaBoardService.modifyArticleComment(qnaArticleCommentDto);
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "댓글 삭제", notes = "댓글번호에 해당하는 댓글정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/comment/{articleCommentId}")
	public ResponseEntity<String> deleteArticleComment(
			@PathVariable("articleCommentId") @ApiParam(value = "삭제할 댓글의 글번호.", required = true) int qnaArticleCommentId)
			throws Exception {
		logger.info("deleteArticleComment - 호출");
		qnaBoardService.deleteArticleComment(qnaArticleCommentId);
		return ResponseEntity.ok().build();

	}

}
