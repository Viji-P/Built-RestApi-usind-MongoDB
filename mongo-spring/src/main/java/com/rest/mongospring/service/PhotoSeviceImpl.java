package com.rest.mongospring.service;

import java.io.IOException;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rest.mongospring.collections.Photo;
import com.rest.mongospring.repository.PhotoRepository;

@Service
public class PhotoSeviceImpl implements PhotoService {

	@Autowired
	private PhotoRepository photoRepository;
	
	@Override
	public String addPhoto(String originalFilename, MultipartFile image)  {
		
		Photo photo = new Photo();
		
		photo.setTitle(originalFilename);
		try {
			photo.setImage(new Binary(BsonBinarySubType.BINARY,image.getBytes()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return photoRepository.save(photo).getId();
	}

	@Override
	public Photo getPhoto(String id) {
		// TODO Auto-generated method stub
		return photoRepository.findById(id).get();
	}

}
