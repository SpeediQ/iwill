package com.kowalczyk.iwill.repesitory;


import com.kowalczyk.iwill.model.ClientServ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientServRepository extends JpaRepository<ClientServ, Long> {


//    @Query("select distinct p from Post p left join fetch p.comments")
//    List<Post> findAllPostsWithComments();


}
