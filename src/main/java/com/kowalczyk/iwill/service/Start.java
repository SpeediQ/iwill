package com.kowalczyk.iwill.service;

import com.kowalczyk.iwill.controller.dto.ItemDTO;
import com.kowalczyk.iwill.model.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static com.kowalczyk.iwill.controller.mapper.ItemMapper.mapToItem;
import static com.kowalczyk.iwill.controller.mapper.VisitDTOMapper.mapVisitToDTO;

@Component
public class Start {
    ClientServService clientServService;
    ItemService itemService;
    CommentService commentService;
    VisitService visitService;
    ClientCardService clientCardService;
    ClientService clientService;

    public Start(ClientServService clientServService, ItemService itemService, CommentService commentService, VisitService visitService, ClientCardService clientCardService, ClientService clientService) {
        this.clientServService = clientServService;
        this.itemService = itemService;
        this.commentService = commentService;
        this.visitService = visitService;
        this.clientCardService = clientCardService;
        this.clientService = clientService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runExamplee() {


        ItemDTO itemDTO = ItemDTO.builder()
                .title("Elektrostymulacja jako usługa")
                .desc("opis do elktrostymulacji")
                .price(120.00D)
                .build();
        itemService.addItem(mapToItem(itemDTO));

        List<Item> items = itemService.getItems();
        Item item = items.get(0);
        itemService.addItem(item);

        Comment comment = Comment.builder()
                .desc("Opis przebiegu elektrostymulacji dla konkretnego klienta")
                .item(item)
                .build();
        commentService.addComment(comment);


        ClientServ clientServ = ClientServ.builder()
                .desc("Usługa wykonana dla klienta")
                .comment(comment)
                .build();

        clientServService.addClientService(clientServ);

        Visit visit = Visit.builder()
                .desc("Wizyta klienta - posiada listę usług")
                .clientServs(Collections.singletonList(clientServ))
                .build();

        visitService.addVisit(visit);

        clientServ.setVisit(visit);
        clientServService.updateClientService(clientServ);


        ClientCard clientCard = ClientCard.builder()
                .desc("Karta klienta - posiada listę wizyt")
                .visits(Collections.singletonList(visit))
                .build();

        clientCardService.addClientCard(clientCard);
        visit.setClientCard(clientCard);
        visitService.updateVisit(visit);


        Client client = Client.builder()
                .firstname("Marcin")
                .lastname("Abc")
                .desc("Client desc")
                .clientCard(clientCard)
                .build();

        clientCard.setClient(client);
        clientService.addClient(client);
        clientCardService.updateClientCard(clientCard);
;

    }

}
