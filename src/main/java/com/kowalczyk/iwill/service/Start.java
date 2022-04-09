package com.kowalczyk.iwill.service;

import com.kowalczyk.iwill.controller.dto.ItemDTO;
import com.kowalczyk.iwill.model.ClientServ;
import com.kowalczyk.iwill.model.Comment;
import com.kowalczyk.iwill.model.Item;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.kowalczyk.iwill.controller.mapper.ClientServDTOMapper.mapClientServToDTOList;
import static com.kowalczyk.iwill.controller.mapper.ItemMapper.mapToItem;

@Component
public class Start {
    ClientServService clientServService;
    ItemService itemService;
    CommentService commentService;

    public Start(ClientServService clientServService, ItemService itemService, CommentService commentService) {
        this.clientServService = clientServService;
        this.itemService = itemService;
        this.commentService = commentService;
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
                .item(item)
                .comment(comment)
                .build();

        clientServService.addClientService(clientServ);

        List<ClientServ> clientServices = clientServService.getClientServices();
        System.out.println(mapClientServToDTOList(clientServices));
        System.out.println(clientServices);

    }

}
