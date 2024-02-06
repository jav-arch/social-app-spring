package com.jk.socialapp.repositories;

import com.jk.socialapp.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Integer> {
}
