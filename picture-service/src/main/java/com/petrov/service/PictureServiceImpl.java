package com.petrov.service;

import com.petrov.persist.PictureRepository;
import com.petrov.persist.model.Picture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;

@Service
public class PictureServiceImpl implements PictureService {

    private final Logger logger = LoggerFactory.getLogger(PictureServiceImpl.class);

    @Value("${picture.storage.path}")
    private String storagePath;

    private final PictureRepository pictureRepository;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }


    @Override
    public Optional<String> getPictureContentTypeById(Long id) {
        return pictureRepository.findById(id).map(Picture::getContentType);
    }

    @Override
    public Optional<byte[]> getPictureDataById(Long id) {
        return pictureRepository.findById(id).map(picture -> Paths.get(storagePath, picture.getStorageNumber()))
                .filter(Files::exists)
                .map(path -> {
                    try {
                        return Files.readAllBytes(path);
                    } catch (IOException e) {
                        logger.error("Can't read file with id " + id, e);
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public String createPicture(byte[] picture) {
        String filename = UUID.randomUUID().toString();
        try (OutputStream os = Files.newOutputStream(Paths.get(storagePath, filename))) {
            os.write(picture);
        } catch (IOException e) {
            logger.error("Can't write file", e);
            throw new RuntimeException(e);
        }
        return filename;
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        try {
            Files.delete(pictureRepository.findById(id)
                    .map(picture -> Paths.get(storagePath, picture.getStorageNumber()))
                    .orElseThrow(() -> new IOException("Can't find image")));
        } catch (IOException e) {
            logger.error("Can't delete file", e);
            throw new RuntimeException(e);
        }
        pictureRepository.deleteById(id);
    }

    @Override
    public Optional<Picture> findById(Long id) {
        return pictureRepository.findById(id);
    }

    @Override
    public Long findProductId(Long picture){
        return pictureRepository.getById(picture).getProduct().getId();
    }
}
