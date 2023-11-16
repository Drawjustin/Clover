package com.ssafy.vue.map.model.service;

import java.util.List;

import com.ssafy.vue.map.model.SidoGugunCodeDto;

public interface MapService {

	List<SidoGugunCodeDto> getSido() throws Exception;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;
	
}
