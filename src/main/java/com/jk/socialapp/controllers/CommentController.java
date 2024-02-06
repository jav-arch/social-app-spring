package com.jk.socialapp.controllers;

import com.jk.socialapp.payloads.ApiResponse;
import com.jk.socialapp.payloads.CommentDto;
import com.jk.socialapp.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/user/{userId}/post/{postId}/comment")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer                                                       userId,@PathVariable Integer postId){
        CommentDto commentDto1 = commentService.createComment(commentDto,postId,userId);
        return new ResponseEntity<>(commentDto1, HttpStatus.CREATED);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
        commentService.deleteComment(commentId);
        ApiResponse apiResponse = new ApiResponse("Comment Deleted Successfully", true);
        return ResponseEntity.ok(apiResponse);
    }
}
