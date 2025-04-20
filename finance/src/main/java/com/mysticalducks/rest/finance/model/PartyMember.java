package com.mysticalducks.rest.finance.model;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "party_member")
public class PartyMember {

    @EmbeddedId
    private PartyMemberId id;

    @Column(name = "chat_id")
    private Integer chatId;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @MapsId("partyId")
    @JoinColumn(name = "party_id", nullable = false)
    private Party party;

    public PartyMember() {}

    public PartyMember(PartyMemberId id, Integer chatId, User user, Party party) {
        this.id = id;
        this.chatId = chatId;
        this.user = user;
        this.party = party;
    }

    public PartyMemberId getId() {
        return id;
    }

    public void setId(PartyMemberId id) {
        this.id = id;
    }

    public Integer getChatId() {
        return chatId;
    }

    public void setChatId(Integer chatId) {
        this.chatId = chatId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }
}
