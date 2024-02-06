package com.jk.socialapp.repositories;

import com.jk.socialapp.models.Category;
import com.jk.socialapp.models.Post;
import com.jk.socialapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

   List<Post> findByUser(User user);
   List<Post> findByCategory(Category category);

//   @Query("select p from Post p where p.title like :key")
//   List<Post> searchByTitle(@Param("key") String title);
   List<Post> findByPostTitleContaining(String title);
}
