package com.kowalczyk.iwill.UI.controller;

import com.kowalczyk.iwill.model.*;

import java.util.ArrayList;
import java.util.List;

public class VisitCreator {
    public static Client client;
    public static ClientCard clientCard;
    public static Visit visit;
    public static ClientServ clientServ;
    public static List<ClientServ> clientServList = new ArrayList<>();
    public static Comment comment;

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        VisitCreator.client = client;
    }

    public static ClientCard getClientCard() {
        return clientCard;
    }

    public static void setClientCard(ClientCard clientCard) {
        VisitCreator.clientCard = clientCard;
    }

    public static Visit getVisit() {
        return visit;
    }

    public static void setVisit(Visit visit) {
        VisitCreator.visit = visit;
    }

    public static ClientServ getClientServ() {
        return clientServ;
    }

    public static void setClientServ(ClientServ clientServ) {
        VisitCreator.clientServ = clientServ;
    }

    public static List<ClientServ> getClientServList() {
        return clientServList;
    }

    public static void setClientServList(List<ClientServ> clientServList) {
        VisitCreator.clientServList = clientServList;
    }

    public static Comment getComment() {
        return comment;
    }

    public static void setComment(Comment comment) {
        VisitCreator.comment = comment;
    }
}
