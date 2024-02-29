//package com.media.service.service.impl;
//
//import com.media.service.dto.MediaDTO;
//import com.media.service.model.Media;
//import com.media.service.repository.MediaRepository;
//import com.media.service.service.MediaService;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.modelmapper.ModelMapper;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.web.multipart.MultipartFile;
//
//
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//class MediaServiceImplTest {
//
//    private MediaRepository mediaRepository;
//    private MediaService mediaService;
//    private ModelMapper modelMapper;
//    @BeforeEach
//    void setUp() {
//        mediaRepository = mock(MediaRepository.class);
//        modelMapper = mock(ModelMapper.class);
//        mediaService = new MediaServiceImpl(mediaRepository, modelMapper);
//    }
//
//    @Test
//    void addMedia() throws Exception {
//        MultipartFile file = new MockMultipartFile("nabil", "nabil.txt", "text/plain", "Hello, World!".getBytes());
//        Media media = new Media();
//        media.setName("nabil.txt");
//        when(mediaRepository.save(any(Media.class))).thenReturn(media);
//        when(modelMapper.map(any(), any())).thenReturn(media);
//
//        MediaDTO result = mediaService.addMedia(file, 1L);
//
//        assertEquals("nabil.txt", result.getName());
//        verify(mediaRepository, times(1)).save(any(Media.class));
//    }
//
//    @Test
//    void deleteMedia() throws Exception {
//        Media media = new Media();
//        media.setPostId(1L);
//        media.setPathImage("C:\\Users\\sap\\Desktop\\social-network-microservice\\Images");
//        when(mediaRepository.findMediaByPostId(1L)).thenReturn(Optional.of(media));
//
//        mediaService.deleteMedia(1L);
//
//        verify(mediaRepository, times(1)).deleteByPostId(1L);
//    }
//
//    @Test
//    void getMediaByPostId() {
//        Media media = new Media();
//        media.setPostId(1L);
//        when(mediaRepository.findMediaByPostId(1L)).thenReturn(Optional.of(media));
//        when(modelMapper.map(any(), any())).thenReturn(new MediaDTO());
//
//        MediaDTO result = mediaService.getMediaByPostId(1L);
//
//        assertEquals(1L, result.getPostId());
//        verify(mediaRepository, times(2)).findMediaByPostId(1L);
//    }
//}