package com.kowalczyk.iwill.service;

import com.kowalczyk.iwill.adapter.*;
import com.kowalczyk.iwill.controller.ClientCardController;
import com.kowalczyk.iwill.controller.ClientController;
import com.kowalczyk.iwill.controller.ItemController;
import com.kowalczyk.iwill.model.*;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Start {
    private ClientRepository clientRepository;
    private ItemRepository itemRepository;
    private ClientCardRepository clientCardRepository;
    private ClientServRepository clientServRepository;
    private CommentRepository commentRepository;
    private VisitRepository visitRepository;

    public Start(ClientRepository clientRepository, ItemRepository itemRepository, ClientCardRepository clientCardRepository, ClientServRepository clientServRepository, CommentRepository commentRepository, VisitRepository visitRepository) {
        this.clientRepository = clientRepository;
        this.itemRepository = itemRepository;
        this.clientCardRepository = clientCardRepository;
        this.clientServRepository = clientServRepository;
        this.commentRepository = commentRepository;
        this.visitRepository = visitRepository;
    }


    @EventListener(ApplicationReadyEvent.class)
    public void runExamplee() {
        Item item = new Item("itemTitle", "itemDesc", 100D);
        Item item2 = new Item("itemTitle2", "itemDesc2", 1000D);
        Client client = new Client();

        ClientCard clientCard = new ClientCard();

        clientCard.setDesc("dsa");
        ClientServ clientServ = new ClientServ();

        Comment comment = new Comment();
        comment.setDesc("Comment1 Desc");
        Visit visit = new Visit();
        visit.setDesc("visit1 desc");
        Visit visit2 = new Visit();
        // CLIENT -- CLIENT_CARD
        client.setClientCard(clientCard);
        // CLIENT_CARD -- VISIT
        visit.setClientCard(clientCard);
        List<Visit> visits = new ArrayList<>();
        visits.add(visit);
        ;
        clientCard.setVisits(visits);
        // CLIENT_SERV -- VISIT
        clientServ.setVisit(visit);
        List<ClientServ> clientServs = new ArrayList<>();
        clientServs.add(clientServ);
        visit.setClientServs(clientServs);
        // ITEM -- COMMENT
        comment.setItem(item);


        clientCardRepository.save(clientCard);
        clientRepository.save(client);
        itemRepository.save(item2);
        itemRepository.save(item);
        clientServRepository.save(clientServ);
        commentRepository.save(comment);
        visitRepository.save(visit2);
        visitRepository.save(visit);

        Client client1 = new Client();
        ItemController itemController = new ItemController(itemRepository);
        ClientService clientService = new ClientService(clientRepository);
        ClientController clientController = new ClientController(clientRepository, clientService);
        ClientCardController clientCardController = new ClientCardController(clientCardRepository);
        Item newCopyItem = itemController.copyToNewItem(itemController.getItem(2L).getBody());

        newCopyItem.setDesc("itttele");
        itemRepository.save(newCopyItem);
        Comment comment1 = new Comment("DESC", newCopyItem);
        commentRepository.save(comment1);

        Client body = clientController.getClientById(1L).getBody();
        Client client2 = clientController.copyToNewItem(body);
        clientRepository.save(client2);

//        ClientCard clientCard1 = client2.getClientCard();
//        if (clientCard1 != null) {
//            ClientCard clientCard2 = clientCardController.copyToNewItem(clientCard1);
//            clientCardRepository.save(clientCard2);
//        }

    }
}
