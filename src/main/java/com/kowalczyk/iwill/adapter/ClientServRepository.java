package com.kowalczyk.iwill.adapter;


import com.kowalczyk.iwill.model.ClientServ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientServRepository extends JpaRepository<ClientServ, Long> {
    List<ClientServ> findAll();

    ClientServ save(ClientServ entity);

//    @Query("select distinct p from Post p left join fetch p.comments")
//    List<Post> findAllPostsWithComments();



}
