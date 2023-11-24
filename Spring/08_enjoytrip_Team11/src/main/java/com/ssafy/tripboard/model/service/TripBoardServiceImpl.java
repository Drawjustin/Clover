package com.ssafy.tripboard.model.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.tripboard.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ssafy.tripboard.model.dao.TripBoardDao;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TripBoardServiceImpl implements TripBoardService {

    private TripBoardDao tripBoardDao;

    @Value("${file.path.upload-images}")
    private String uploadImagePath;

    @Autowired
    public TripBoardServiceImpl(TripBoardDao tripBoardDao) {
        super();
        this.tripBoardDao = tripBoardDao;
    }

    @Override
    @Transactional
    public void writeArticle(TripBoardDto tripBoardDto) throws Exception {
        tripBoardDao.insertArticle(tripBoardDto);
        int tripArticleId = tripBoardDto.getTripArticleId();
        if (tripBoardDto.getTripBoardFileDto() != null) {
            TripBoardFileDto thumbnailImage = tripBoardDto.getTripBoardFileDto();
            thumbnailImage.setTripArticleId(tripBoardDto.getTripArticleId());
            tripBoardDao.insertFile(thumbnailImage);
        }

        if(tripBoardDto.getTags() == null || tripBoardDto.getTags().isEmpty()) return;
        List<TagDto> tags = tripBoardDto.getTags();
        for(int i = 0; i < tags.size(); i++){
            tags.get(i).setTripArticleId(tripArticleId);
            System.out.println(tags.get(i));
        }

        tripBoardDao.insertTag(tags);

    }

    @Override
    public TripBoardDto getArticle(int tripArticleId) throws Exception {
        return tripBoardDao.selectArticle(tripArticleId);
    }

    @Override
    public TripBoardListDto listArticle(Map<String, String> map) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("word", map.get("word") == null ? "" : map.get("word"));
        param.put("order", map.get("order").isEmpty() ? "register_time" : map.get("order"));
        int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
        int sizePerPage = Integer.parseInt(map.get("spp") == null ? "20" : map.get("spp"));
        int start = currentPage * sizePerPage - sizePerPage;
        param.put("start", start);
        param.put("list_size", sizePerPage);

        // key(검색 조건)이 user_id일 때

        String key = map.get("key");
        if("user_id".equals(key)) param.put("key", "tbd.user_id");
        else param.put("key", key == null ? "" : key);

//        if ("user_id".equals(key)) param.put("key", key == null ? "" : "tbd.user_id");



        System.out.println(param);
        List<TripBoardDto> list = tripBoardDao.selectArticleList(param);

//        if ("user_id".equals(key)) param.put("key", key == null ? "" : "user_id");
        int totalArticleCount = tripBoardDao.getTotalArticleCount(param);
        int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;

        System.out.println(totalArticleCount);

        // list와 페이지 정보를 하나의 DTO에 담아서 리턴
        TripBoardListDto tripBoardListDto = new TripBoardListDto();
        tripBoardListDto.setArticles(list);
        tripBoardListDto.setCurrentPage(currentPage);
        tripBoardListDto.setTotalPageCount(totalPageCount);

        return tripBoardListDto;
    }

    @Override
    public void updateHit(int tripArticleId) throws Exception {
        tripBoardDao.updateHit(tripArticleId);
    }

    @Override
    @Transactional
    public void modifyArticle(TripBoardDto tripBoardDto) throws Exception {
        tripBoardDao.updateArticle(tripBoardDto);
        int tripArticleId = tripBoardDto.getTripArticleId();
        if(tripBoardDto.getTripBoardFileDto()!=null){
            TripBoardFileDto fileDto = tripBoardDto.getTripBoardFileDto();
            fileDto.setTripArticleId(tripArticleId);
            tripBoardDao.updateFile(fileDto);
        }

        tripBoardDao.deleteTagsByArticleId(tripArticleId);
        tripBoardDao.insertTag(tripBoardDto.getTags());


    }

    @Override
    @Transactional
    public void removeArticle(int tripArticleId) throws Exception {
        TripBoardFileDto fileDto = tripBoardDao.selectFile(tripArticleId);
        File file = new File(uploadImagePath + File.separator + fileDto.getSaveFolder() + File.separator + fileDto.getSaveFile());
        file.delete();

        //file db는 cascading으로 삭제될듯?
        tripBoardDao.deleteArticle(tripArticleId);
    }

    @Override
    public List<TripArticleCommentDto> listArticleComment(int tripArticleId) throws Exception {
        return tripBoardDao.selectArticleCommentList(tripArticleId);
    }

    @Override
    public void writeArticleComment(TripArticleCommentDto tripArticleCommentDto) throws Exception {
        System.out.println(tripArticleCommentDto);
        tripBoardDao.insertArticleComment(tripArticleCommentDto);
    }

    @Override
    public void deleteArticleComment(int tripArticleCommentId) throws Exception {
        tripBoardDao.deleteArticleComment(tripArticleCommentId);
    }

    @Override
    public void modifyArticleComment(TripArticleCommentDto tripArticleCommentDto) throws Exception {
        tripBoardDao.updateArticleComment(tripArticleCommentDto);
    }

}
