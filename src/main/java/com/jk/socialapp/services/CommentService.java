package com.jk.socialapp.services;

import com.jk.socialapp.payloads.CommentDto;

public interface CommentService {
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId);

    public void deleteComment(Integer commentId);
}
