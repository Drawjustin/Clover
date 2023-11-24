package com.ssafy.tripboard.controller;

import java.io.File;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.ssafy.tripboard.model.TripBoardFileDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.tripboard.model.TripArticleCommentDto;
import com.ssafy.tripboard.model.TripBoardDto;
import com.ssafy.tripboard.model.TripBoardListDto;
import com.ssafy.tripboard.model.service.TripBoardService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
        RequestMethod.POST, RequestMethod.DELETE}, maxAge = 6000)
@RestController
@RequestMapping("/tripboard")
public class TripBoardController {
    private static final Logger logger = LoggerFactory.getLogger(TripBoardController.class);

    private TripBoardService tripBoardService;

    @Value("${file.path.upload-images}")
    private String uploadImagePath;

    public TripBoardController(TripBoardService tripBoardService) {
        super();
        this.tripBoardService = tripBoardService;
    }

    @ApiOperation(value = "Trip 글작성", notes = "새로운 게시글 정보를 입력한다.")
    @PostMapping
    public ResponseEntity<Void> writeArticle(@RequestPart("tripBoardDto") TripBoardDto tripBoardDto, @RequestPart("thumbnailImage") MultipartFile thumbnailImage) {
        logger.info("writeArticle tripBoardDto - {}", tripBoardDto);
        logger.info("writeArticle thumbnailImage - {}", thumbnailImage);

        try {
            //이미지가 null이 아니면
            if (thumbnailImage != null) {
                String today = new SimpleDateFormat("yyMMdd").format(new Date());
                String saveFolder = uploadImagePath + File.separator + today;

                File folder = new File(saveFolder);
                if (!folder.exists()) folder.mkdirs();

                String originalFileName = thumbnailImage.getOriginalFilename();
                String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));

                TripBoardFileDto tripBoardFileDto = new TripBoardFileDto();
                tripBoardFileDto.setSaveFolder(today);
                tripBoardFileDto.setOriginalFile(originalFileName);
                tripBoardFileDto.setSaveFile(saveFileName);

                thumbnailImage.transferTo(new File(folder, saveFileName));

                tripBoardDto.setTripBoardFileDto(tripBoardFileDto);
            }
            tripBoardService.writeArticle(tripBoardDto);

            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    @ApiOperation(value = "Trip 글보기", notes = "Trip id에 해당하는 게시글의 정보를 반환한다.", response = TripBoardDto.class)
    @GetMapping("/{tripArticleId}")
    public ResponseEntity<TripBoardDto> getArticle(
            @PathVariable("tripArticleId") @ApiParam(value = "얻어올 글의 id.", required = true) int tripArticleId)
            throws Exception {
        logger.info("getArticle - 호출 : " + tripArticleId);
        tripBoardService.updateHit(tripArticleId);
        return new ResponseEntity<TripBoardDto>(tripBoardService.getArticle(tripArticleId), HttpStatus.OK);
    }

    @ApiOperation(value = "수정 할 Trip 글 얻기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = TripBoardDto.class)
    @GetMapping("/modify/{tripArticleId}")
    public ResponseEntity<TripBoardDto> getModifyArticle(
            @PathVariable("tripArticleId") @ApiParam(value = "얻어올 글의 글번호.", required = true) int tripArticleId)
            throws Exception {
        try {
            logger.info("getModifyArticle - 호출 : " + tripArticleId);
            return new ResponseEntity<TripBoardDto>(tripBoardService.getArticle(tripArticleId), HttpStatus.OK);
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
    }

    @ApiOperation(value = "Trip 글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
    @ApiResponses({@ApiResponse(code = 200, message = "회원목록 OK!!"), @ApiResponse(code = 404, message = "페이지없어!!"),
            @ApiResponse(code = 500, message = "서버에러!!")})
    @GetMapping
    public ResponseEntity<TripBoardListDto> listArticle(
            @RequestParam @ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) Map<String, String> map) {
        logger.info("listArticle map - {}", map);
        try {
            TripBoardListDto tripBoardListDto = tripBoardService.listArticle(map);
            System.out.println("after select list");
            System.out.println(tripBoardListDto);
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            return ResponseEntity.ok().headers(header).body(tripBoardListDto);
        } catch (Exception e) {
//			return exceptionHandling(e);
            System.err.println(e);
            return null;
        }
    }

    @ApiOperation(value = "Trip 게시글 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @DeleteMapping("/{tripArticleId}")
    public ResponseEntity<String> deleteArticle(
            @PathVariable("tripArticleId") @ApiParam(value = "살제할 글의 글번호.", required = true) int tripArticleId)
            throws Exception {
        logger.info("deleteArticle - 호출");
        tripBoardService.removeArticle(tripArticleId);
        return ResponseEntity.ok().build();

    }

    @ApiOperation(value = "게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PutMapping
    public ResponseEntity<String> modifyArticle(
            @RequestPart("tripBoardDto") TripBoardDto tripBoardDto, @RequestPart(value = "thumbnailImage", required=false) MultipartFile thumbnailImage) throws Exception {
        logger.info("modifyArticle - 호출 {}", tripBoardDto);
        try{
            if(thumbnailImage != null) {
                //이미지 삭제하고 새로 등록해줘야 함..
                //db 수정안하고 파일만 접근해서 바꿔주면 될듯??

                File file = new File(uploadImagePath + File.separator + tripBoardDto.getTripBoardFileDto().getSaveFolder() + File.separator + tripBoardDto.getTripBoardFileDto().getSaveFile());
                file.delete();

                String today = new SimpleDateFormat("yyMMdd").format(new Date());
                String saveFolder = uploadImagePath + File.separator + today;

                File folder = new File(saveFolder);
                if (!folder.exists()) folder.mkdirs();

                String originalFileName = thumbnailImage.getOriginalFilename();
                String saveFileName = UUID.randomUUID().toString() + originalFileName.substring(originalFileName.lastIndexOf('.'));

                TripBoardFileDto tripBoardFileDto = new TripBoardFileDto();
                tripBoardFileDto.setSaveFolder(today);
                tripBoardFileDto.setOriginalFile(originalFileName);
                tripBoardFileDto.setSaveFile(saveFileName);

                thumbnailImage.transferTo(new File(folder, saveFileName));

                tripBoardDto.setTripBoardFileDto(tripBoardFileDto);
            }
            else tripBoardDto.setTripBoardFileDto(null);

            tripBoardService.modifyArticle(tripBoardDto);
            return ResponseEntity.ok().build();
        }
         catch(Exception e){
             System.err.println(e);
             return null;
         }

    }

    // 댓글 관련

    @ApiOperation(value = "댓글 목록", notes = "게시글 번호에 해당하는 댓글목록을 갖고온다..", response = TripArticleCommentDto.class)
    @GetMapping("/comment/{tripArticleId}")
    public ResponseEntity<List<TripArticleCommentDto>> getArticleComment(
            @PathVariable("tripArticleId") @ApiParam(value = "얻어올 게시글 글번호.", required = true) int tripArticleId)
            throws Exception {
        logger.info("getArticleComment - 호출 : " + tripArticleId);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return new ResponseEntity<>(tripBoardService.listArticleComment(tripArticleId),
                HttpStatus.OK);
    }

    @ApiOperation(value = "댓글작성", notes = "새로운 댓글 정보를 입력한다.")
    @PostMapping(value = "/comment")
    public ResponseEntity<?> writeArticleComment(
            @RequestBody @ApiParam(value = "댓글 정보.", required = true) TripArticleCommentDto tripArticleCommentDto) {
        logger.info("writeArticleComment boardCommentDto - {}", tripArticleCommentDto);
        System.out.println("in writeArticleComment");

        try {
            tripBoardService.writeArticleComment(tripArticleCommentDto);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (Exception e) {
            return null;
//		return exceptionHandling(e);
        }
    }

    @ApiOperation(value = "댓글수정", notes = "수정할 댓글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @PutMapping(value = "/comment")
    public ResponseEntity<String> modifyArticleComment(
            @RequestBody @ApiParam(value = "수정할 댓글정보.", required = true) TripArticleCommentDto tripArticleCommentDto)
            throws Exception {
        logger.info("modifyArticleComment - 호출 {}", tripArticleCommentDto);

        tripBoardService.modifyArticleComment(tripArticleCommentDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "댓글 삭제", notes = "댓글번호에 해당하는 댓글정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
    @DeleteMapping("/comment/{articleCommentId}")
    public ResponseEntity<String> deleteArticleComment(
            @PathVariable("articleCommentId") @ApiParam(value = "삭제할 댓글의 글번호.", required = true) int tripArticleCommentId)
            throws Exception {
        logger.info("deleteArticleComment - 호출");
        tripBoardService.deleteArticleComment(tripArticleCommentId);
        return ResponseEntity.ok().build();

    }
}
