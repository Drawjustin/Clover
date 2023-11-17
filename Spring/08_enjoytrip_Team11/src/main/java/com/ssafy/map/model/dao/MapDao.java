package com.ssafy.map.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.map.model.AttractionInfoDto;
import com.ssafy.map.model.GugunDto;
import com.ssafy.map.model.SearchAttractionDto;
import com.ssafy.map.model.SidoDto;


@Mapper
public interface MapDao {	
	
	List<SidoDto> searchAllSido();
	List<GugunDto> searchAllGugun(int sidoCode);
	List<AttractionInfoDto> search(SearchAttractionDto searchAttractionDto);

}
