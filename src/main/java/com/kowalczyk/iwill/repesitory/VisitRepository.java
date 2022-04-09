package com.kowalczyk.iwill.repesitory;


import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {


//    @Query("select distinct p from Post p left join fetch p.comments")
//    List<Post> findAllPostsWithComments();


}
