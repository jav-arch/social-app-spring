package com.jk.socialapp.controllers;

import com.jk.socialapp.config.AppConstant;
import com.jk.socialapp.payloads.ApiResponse;
import com.jk.socialapp.payloads.PostDto;
import com.jk.socialapp.payloads.PostReponse;
import com.jk.socialapp.services.FileService;
import com.jk.socialapp.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{catId}/posts")
    public ResponseEntity<PostDto> createPost( @RequestBody PostDto postDto, @PathVariable Integer userId,
                                              @PathVariable Integer catId){
        PostDto postDto1 = postService.createPost(postDto,userId,catId);
        return new ResponseEntity<>(postDto1, HttpStatus.CREATED);

    }

    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser( @PathVariable Integer userId){
        List<PostDto> postDtos = postService.getPostByUser(userId);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @GetMapping("/category/{catId}/posts")
    public ResponseEntity<List<PostDto>> getPostByCategory( @PathVariable Integer catId){
        List<PostDto> postDtos = postService.getPostByCategory(catId);
        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
        PostDto postDto = postService.getPostById(postId);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostReponse> getPosts(@RequestParam(value="pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required =
            false) Integer pageNumber,
                                                @RequestParam(value = "pageSize", defaultValue = AppConstant.PAGE_SIZE,
                                                        required =
                                                          false) Integer pageSize,
                                                @RequestParam(value = "sortBy", defaultValue = AppConstant.SORT_BY,required =
                                                        false) String sortBy){
        PostReponse postReponse = postService.getAllPost(pageNumber,pageSize,sortBy);
        return new ResponseEntity<>(postReponse, HttpStatus.OK);
    }

    @PutMapping("/category/{catId}/posts/{postId}")
    public ResponseEntity<PostDto> updatePost( @RequestBody PostDto postDto, @PathVariable Integer postId,
                                              @PathVariable  Integer catId){
        PostDto postDto1 = postService.updatePost(postDto,postId,catId);
        return new ResponseEntity<>(postDto1, HttpStatus.OK);
    }

    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId)
    {
        postService.deletePost(postId);
        ApiResponse apiResponse = new ApiResponse("Post deleted Successfully", true);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/posts/search")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@RequestParam(value = "keyword",required = true) String keyword){
        List<PostDto> postDtos = postService.searchPost(keyword);
        return new ResponseEntity<>(postDtos,HttpStatus.OK);
    }

    @PostMapping("/posts/image/upload/{postId}")
    public ResponseEntity<PostDto> imageUpload(@RequestParam("image") MultipartFile image,
                                                   @PathVariable Integer postId) throws IOException {

        PostDto postDto = postService.getPostById(postId);
        String filename = fileService.uploadImage(path,image);

        postDto.setImageUrl(filename);
        PostDto postDto1 =postService.updatePost(postDto,postId,3);
        System.out.println(filename+ "SSsSs");
        return new ResponseEntity<>(postDto1,HttpStatus.OK);
    }
}
