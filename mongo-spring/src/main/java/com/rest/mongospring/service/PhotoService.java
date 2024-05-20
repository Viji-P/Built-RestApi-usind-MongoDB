package com.rest.mongospring.service;

import org.springframework.web.multipart.MultipartFile;

import com.rest.mongospring.collections.Photo;

public interface PhotoService {

	String addPhoto(String originalFilename, MultipartFile image);

	Photo getPhoto(String id);

}
