package com.mysticalducks.rest.finance.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class PartyMemberId implements Serializable {

    private static final long serialVersionUID = -4041338921768348111L;
	private Integer userId;
    private Integer partyId;

    public PartyMemberId() {}

    public PartyMemberId(Integer userId, Integer partyId) {
        this.userId = userId;
        this.partyId = partyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPartyId() {
        return partyId;
    }

    public void partyId(Integer partyId) {
        this.partyId = partyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartyMemberId that = (PartyMemberId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(partyId, that.partyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, partyId);
    }
}
