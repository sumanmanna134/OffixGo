package com.offlix.offlixstore.service.image;

import com.offlix.offlixstore.constant.AppConstant;
import com.offlix.offlixstore.execption.ImageNotFoundException;
import com.offlix.offlixstore.model.Image;
import com.offlix.offlixstore.repository.ImageRepository;
import com.offlix.offlixstore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;


@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(()-> new ImageNotFoundException(AppConstant.IMAGE_NOT_FOUND));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete,()->{
            throw new ImageNotFoundException(AppConstant.IMAGE_NOT_FOUND);
        });

    }

    @Override
    public Image saveImage(MultipartFile file, Long productId) {
        return null;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        try {
            Image image = getImageById(imageId);
            image.setName(file.getOriginalFilename());
            image.setImage(new SerialBlob(file.getBytes()));
        }catch (IOException | SQLException e){
            throw new RuntimeException(e.getMessage());
        }


    }
}
