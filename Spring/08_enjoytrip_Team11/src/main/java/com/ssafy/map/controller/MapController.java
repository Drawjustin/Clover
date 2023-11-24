package com.ssafy.map.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.map.model.AttractionInfoDto;
import com.ssafy.map.model.GugunDto;
import com.ssafy.map.model.SearchAttractionDto;
import com.ssafy.map.model.SidoDto;
import com.ssafy.map.model.service.MapService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;


@CrossOrigin(origins = { "*" }, methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT,
		RequestMethod.POST, RequestMethod.DELETE }, maxAge = 6000)
@RestController
@RequestMapping("/attraction")
public class MapController {
	@Autowired
	private MapService service;

//	@Autowired
//	public MapController(MapService service) {
//		super();
//		this.service = service;
//	}

	@GetMapping("/sido")
	ResponseEntity<List<SidoDto>> getSidoList() {
		List<SidoDto> list = service.searchAllSido();
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/gugun")
	ResponseEntity<List<GugunDto>> getGugunList(@RequestParam("sido") int sidoCode) {
		System.out.println(sidoCode);
		List<GugunDto> list = service.searchAllGugun(sidoCode);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

//	@GetMapping("/search")
//	ResponseEntity<List<AttractionInfoDto>> getList(@RequestBody  SearchAttractionDto searchAttractionDto) {
//		System.out.println(searchAttractionDto);
//		List<AttractionInfoDto> list = service.search(searchAttractionDto);
//		return new ResponseEntity<>(list, HttpStatus.OK);
//	}
	
//	@GetMapping("/search")
//	ResponseEntity<List<AttractionInfoDto>> getList(@RequestParam("sidoCode") int sidoCode,
//	        @RequestParam("gugunCode") int gugunCode,
//	        @RequestParam(value = "contentType") int[] contentType,
//	        @RequestParam String searchWord) {
//		System.out.println(sidoCode+" "+gugunCode+" "+contentType[0]);
//		//return new ResponseEntity<>(list, HttpStatus.OK);
//		return new ResponseEntity<>(HttpStatus.OK);
//	}
	@GetMapping("/search")
	ResponseEntity<List<AttractionInfoDto>> getList(@ModelAttribute SearchAttractionDto searchAttractionDto) {
		System.out.println(searchAttractionDto.toString());
		if(searchAttractionDto.getContentType().length == 0) searchAttractionDto.setContentType(null);
		List<AttractionInfoDto> list = service.search(searchAttractionDto);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
//	@GetMapping("/search/{}")
//	ResponseEntity<List<AttractionInfoDto>> getList(@RequestParam Map<String, Object> item) {
//		SearchAttractionDto searchAttractionDto = new SearchAttractionDto();
//		searchAttractionDto.setSidoCode((Integer) item.get("sidoCode"));
//		searchAttractionDto.setGugunCode((Integer) item.get("gugunCode"));
//		searchAttractionDto.setSearchWord((String) item.get("searchWord"));
//		searchAttractionDto.setContentType((int[]) item.get("contentType"));
//
//		List<AttractionInfoDto> list = service.search(searchAttractionDto);
//		return new ResponseEntity<>(list, HttpStatus.OK);
//	}
}
