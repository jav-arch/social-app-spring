package com.jk.socialapp.services;

import com.jk.socialapp.exceptions.ResourceNotFoundException;
import com.jk.socialapp.models.Comment;
import com.jk.socialapp.models.Post;
import com.jk.socialapp.models.User;
import com.jk.socialapp.payloads.CommentDto;
import com.jk.socialapp.repositories.CommentRepository;
import com.jk.socialapp.repositories.PostRepository;
import com.jk.socialapp.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto,Integer postId, Integer userId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFoundException(
                "Post", "Id", postId));
        User user = userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException(
                "User", "Id", userId));
        Comment comment = modelMapper.map(commentDto, Comment.class);
        comment.setUser(user);
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        CommentDto commentDto1 = modelMapper.map(savedComment,CommentDto.class);
        return commentDto1;
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment =commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException(
                "Comment", "Id", commentId));
        commentRepository.delete(comment);
    }
}
