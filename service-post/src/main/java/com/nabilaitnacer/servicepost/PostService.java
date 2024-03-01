package com.nabilaitnacer.servicepost;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private  final  PostRepository postRepository;
    private final ModelMapper modelMapper;

    public List<PostEntityDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(postEntity -> modelMapper.map(postEntity, PostEntityDto.class))
                .toList();
    }
    public PostEntityDto getPostById(Long id) {
        PostEntity postEntity = postRepository.findById(id).orElseThrow();
        return modelMapper.map(postEntity, PostEntityDto.class);
    }
    public PostEntityDto createPost(PostEntityDto postEntityDto) {
        postEntityDto.setCreatedAt(LocalDateTime.now());
        PostEntity postEntity = modelMapper.map(postEntityDto, PostEntity.class);
        return modelMapper.map(postRepository.save(postEntity), PostEntityDto.class);
    }
    public PostEntityDto updatePost(Long id, PostEntityDto postEntityDto) {
        postEntityDto.setUpdatedAt(LocalDateTime.now());
        PostEntity postEntity = modelMapper.map(postEntityDto, PostEntity.class);
        postEntity.setId(id);
        return modelMapper.map(postRepository.save(postEntity), PostEntityDto.class);
    }
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public  List<PostEntityDto> getPostsByUserId(Long id) {
        log.info("id: {}", id);
        return postRepository.findPostEntitiesByUserId(id).stream()
                .map(postEntity -> modelMapper.map(postEntity, PostEntityDto.class))
                .toList();
    }



}
