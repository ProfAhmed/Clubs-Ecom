package com.ecom.clubs.events;

import com.ecom.clubs.data.models.ClubModel;

public class EventClub {
    private ClubModel clubModel;

    public EventClub(ClubModel clubModel) {
        this.clubModel = clubModel;
    }

    public ClubModel getClubModel() {
        return clubModel;
    }
}
