package com.project.repository;

import com.project.entity.AppUser;
import com.project.entity.Property;
import com.project.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    //findByUserId
    List<Review> findByAppUser(AppUser user);

    //findByUserandProperty
    boolean existsByAppUserAndProperty(AppUser user, Property property);
}