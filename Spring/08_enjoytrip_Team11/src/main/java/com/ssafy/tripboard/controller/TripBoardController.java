package com.ssafy.tripboard.controller;

import java.io.File;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tripboard")
public class TripBoardController {
	@PostMapping("/upload-image")
	void uploadImage(File file) {
		System.out.println(file);
	}
}
