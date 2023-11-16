package com.ssafy.vue.map.model.mapper;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.vue.map.model.SidoGugunCodeDto;

public interface MapMapper {

	List<SidoGugunCodeDto> getSido() throws SQLException;
	List<SidoGugunCodeDto> getGugunInSido(String sido) throws SQLException;
	
}
