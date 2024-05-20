package com.rest.mongospring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.rest.mongospring.collections.Photo;
import com.rest.mongospring.service.PhotoService;



@RestController
@RequestMapping("/photo")
public class PhotoController {
	
	@Autowired
	private PhotoService photoService;
	
	@PostMapping("")
	public String addPhoto(@RequestParam("image") MultipartFile image) {
		
		String id = photoService.addPhoto(image.getOriginalFilename(),image);
		
		return id;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Resource> downloadPhoto(String id){
		
		Photo photo = photoService.getPhoto(id);
		 
		Resource resource = new ByteArrayResource(photo.getImage().getData());
		
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;fileName=\""+photo.getTitle()+ "\"" )
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(resource);
				
				}

}
