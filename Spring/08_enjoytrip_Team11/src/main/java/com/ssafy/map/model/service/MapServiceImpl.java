package com.ssafy.map.model.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.map.model.AttractionInfoDto;
import com.ssafy.map.model.GugunDto;
import com.ssafy.map.model.SearchAttractionDto;
import com.ssafy.map.model.SidoDto;
import com.ssafy.map.model.dao.MapDao;



@Service
public class MapServiceImpl implements MapService {

	private MapDao dao;

	@Autowired
	public MapServiceImpl(MapDao dao) {
		super();
		this.dao = dao;
	}

	@Override
	public List<SidoDto> searchAllSido() {
		return dao.searchAllSido();
	}

	@Override
	public List<GugunDto> searchAllGugun(int sidoCode) {
		return dao.searchAllGugun(sidoCode);
	}

	@Override
	public List<AttractionInfoDto> search(SearchAttractionDto searchAttractionDto) {
		return dao.search(searchAttractionDto);
	}

}
