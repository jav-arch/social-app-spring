package com.jk.socialapp.services;

import com.jk.socialapp.exceptions.ResourceNotFoundException;
import com.jk.socialapp.models.Category;
import com.jk.socialapp.models.Post;
import com.jk.socialapp.models.User;
import com.jk.socialapp.payloads.PostDto;
import com.jk.socialapp.payloads.PostReponse;
import com.jk.socialapp.repositories.CategoryRepository;
import com.jk.socialapp.repositories.PostRepository;
import com.jk.socialapp.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService{
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto, Integer catId, Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id", userId));
        Category category = categoryRepository.findById(catId).orElseThrow(()-> new ResourceNotFoundException("Category","Id",catId));
        Post post = modelMapper.map(postDto, Post.class);
        post.setImageUrl("default.png");
        post.setCreatedAt(new Date());
        post.setCategory(category);
        post.setUser(user);
        Post savedPost = postRepository.save(post);
        return modelMapper.map(savedPost,PostDto.class);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId, Integer catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(()-> new ResourceNotFoundException(
                "Category", "Id", catId));
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post","Id",
                postId));
        post.setPostTitle(postDto.getPostTitle());
        post.setContent(postDto.getContent());
        post.setImageUrl(postDto.getImageUrl());
        post.setCategory(category);
        Post updatedPost = postRepository.save(post);
        PostDto postDto1 = modelMapper.map(updatedPost,PostDto.class);
        return postDto1;
    }

    @Override
    public void deletePost(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Id",
                postId));
        postRepository.delete(post);
    }

    @Override
    public PostReponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy) {
        Pageable p = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy).descending());
        Page<Post> posts = postRepository.findAll(p);
        List<Post> postList = posts.getContent();
        List<PostDto> postDtos = postList.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        PostReponse postReponse = new PostReponse(postDtos, posts.getNumber(),posts.getSize(),posts.getTotalElements(),posts.getTotalPages(),posts.isLast());
        return postReponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "Id",
                postId));
        PostDto postDto = modelMapper.map(post,PostDto.class);
        return postDto;
    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException(
                "Category", "Id", categoryId));
        List<Post> posts =  postRepository.findByCategory(category);
        List<PostDto> postDtos = posts.stream().map((post)-> modelMapper.map(post,PostDto.class)).collect(Collectors.toList());

        return postDtos;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "Id",
                userId));
        List<Post> posts = postRepository.findByUser(user);
        List<PostDto> postDtos =
                posts.stream().map((post) -> modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public List<PostDto> searchPost(String keyword)
    {
        List<Post> posts = postRepository.findByPostTitleContaining(keyword);
        List<PostDto> postDtos = posts.stream().map((post) -> modelMapper.map(post,PostDto.class)).collect(Collectors.toList());
        return postDtos;
    }
}
