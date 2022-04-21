package com.kowalczyk.iwill.repesitory;


import com.kowalczyk.iwill.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {


//    @Query("select distinct p from Post p left join fetch p.comments")
//    List<Post> findAllPostsWithComments();


}
