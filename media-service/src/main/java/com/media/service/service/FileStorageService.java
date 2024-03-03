package com.media.service.service;

import com.media.service.dto.MediaDto;
import com.media.service.exception.InvalidFileException;
import com.media.service.exception.InvalidFileNameException;
import com.media.service.exception.StorageException;
import com.media.service.model.Media;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService {
    @Value("${file.upload-dir}")
    private String uploadDirectory;


    private final Environment environment;
    private final  WebApplicationContext webApplicationContext;

    public MediaDto store(MultipartFile file, Long userId, Long postId) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        log.info("storing file {}", filename);

        try {
            if (file.isEmpty()) {
                log.warn("failed to store empty file {}", filename);
                throw new InvalidFileException("Failed to store empty file " + filename);
            }

            if (filename.contains("..")) {
                // This is a security check
                log.warn("cannot store file with relative path {}", filename);
                throw new InvalidFileNameException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }

            String newFilename = UUID.randomUUID().toString().substring(0,5)+"-"+filename ;
            String encodedFilename = URLEncoder.encode(newFilename, StandardCharsets.UTF_8).replace("+", "%20");
            try (InputStream inputStream = file.getInputStream()) {
                Path userDir = Paths.get(uploadDirectory);

                if(Files.notExists(userDir)) {
                    Files.createDirectory(userDir);
                }

                Files.copy(inputStream, userDir.resolve(newFilename),
                        StandardCopyOption.REPLACE_EXISTING);
            }

            String gatewayHostName = "localhost"; // replace with your gateway's hostname
            String gatewayPort = "8222"; // replace with your gateway's port
            String mediaServicePath = "/api/v1/medias/images"; // path to the media service in your gateway configuration
            // create the file URL using the gateway hostname, port, media service path, user ID, post ID, and new filename
            // (use the UTF-8 charset) and store it in the fileUrl variable
            String fileUrl = String.format("http://%s:%s%s/%s",
                    gatewayHostName, gatewayPort, mediaServicePath,  encodedFilename);

            log.info("successfully stored file {} location {}", filename, fileUrl);

        return MediaDto.builder()
                .filename(newFilename)
                .userId(userId)
                .mediaUuid(postId+""+ userId +  UUID.randomUUID().toString().substring(0,4))
                .postId(postId)
                .fileType(file.getContentType())
                .size(file.getSize())
                .uri(fileUrl)
                .createdDate(LocalDateTime.now())
                .build();
        }
        catch (IOException e) {
            log.error("failed to store file {} error: {}", filename, e);
            throw new StorageException("Failed to store file " + filename, e);
        }
    }
    public void delete( String filename) {
        try {

            Path filePath = Paths.get(uploadDirectory, filename);
            Files.delete(filePath);
        } catch (IOException e) {
            log.error("Failed to delete file {} error: {}", filename, e);
            throw new StorageException("Failed to delete file " + filename, e);
        }
    }
}
