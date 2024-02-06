package com.jk.socialapp.services;

import com.jk.socialapp.payloads.PostDto;
import com.jk.socialapp.payloads.PostReponse;

import java.util.List;

public interface PostService {
    public PostDto createPost(PostDto postDto, Integer catId, Integer userId);

    public PostDto updatePost(PostDto postDto, Integer postId, Integer catId);

    public void deletePost(Integer postId);

    public PostReponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy);

    public PostDto getPostById(Integer postId);

    public List<PostDto> getPostByCategory(Integer categoryId);

    public  List<PostDto> getPostByUser(Integer userId);

    public List<PostDto> searchPost(String keyword);
}
