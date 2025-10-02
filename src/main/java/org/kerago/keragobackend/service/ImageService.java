package org.kerago.keragobackend.service;

import org.kerago.keragobackend.exception.ResourceNotFoundException;
import org.kerago.keragobackend.model.Images;
import org.kerago.keragobackend.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageService {
    @Autowired
    ImageRepository imageRepository;


    public Images getImagebyid(Long id) {
        return imageRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("resouse not found"));
    }
}
