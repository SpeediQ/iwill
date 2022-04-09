package com.kowalczyk.iwill.service;

import com.kowalczyk.iwill.controller.dto.ItemDTO;
import com.kowalczyk.iwill.model.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.kowalczyk.iwill.controller.mapper.ItemMapper.mapToItem;

@Component
public class Start {
    ClientServService clientServService;
    ItemService itemService;
    CommentService commentService;
    VisitService visitService;
    ClientCardService clientCardService;

    public Start(ClientServService clientServService, ItemService itemService, CommentService commentService, VisitService visitService, ClientCardService clientCardService) {
        this.clientServService = clientServService;
        this.itemService = itemService;
        this.commentService = commentService;
        this.visitService = visitService;
        this.clientCardService = clientCardService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runExamplee() {


        ItemDTO itemDTO = ItemDTO.builder()
                .desc("testDesc")
                .title("testTitle")
                .price(10D)
                .build();
        itemService.addItem(mapToItem(itemDTO));

        List<Item> items = itemService.getItems();
        Item item = items.get(0);
        itemService.addItem(item);

        Comment comment = Comment.builder()
                .desc("Desc comment")
                .item(item)
                .build();
        commentService.addComment(comment);


        ClientServ clientServ = ClientServ.builder()
                .desc("client service desc")
                .comment(comment)
                .build();

        clientServService.addClientService(clientServ);

        Visit visit = Visit.builder()
                .desc("visit desc")
                .clientServs(Collections.singletonList(clientServ))
                .build();

        visitService.addVisit(visit);

        clientServ.setVisit(visit);
        clientServService.updateClientService(clientServ);


//        ClientCard clientCard = ClientCard.builder()
//                .desc("clientCard desc")
//                .visits(Collections.singletonList(visit))
//                .build();
//
//        clientCardService.addClientCard(clientCard);
//        visit.setClientCard(clientCard);
//        visitService.updateVisit(visit);
    }

}
