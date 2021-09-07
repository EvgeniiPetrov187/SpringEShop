package com.petrov.service;

import com.petrov.persist.model.Picture;

import java.util.Optional;

public interface PictureService {

    Optional<String> getPictureContentTypeById(Long id);

    Optional<byte[]> getPictureDataById(Long id);

    String createPicture(byte[] picture);

    void deleteById(Long id);

    Optional<Picture> findById(Long id);

    Long findProductId(Long picture);
}
