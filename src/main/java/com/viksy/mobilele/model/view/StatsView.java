package com.viksy.mobilele.model.view;

public class StatsView {
    private final int anonRequests;
    private final int authRequests;

    public StatsView(int anonRequests, int authRequests) {
        this.anonRequests = anonRequests;
        this.authRequests = authRequests;
    }

    public int getAnonRequests() {
        return anonRequests;
    }

    public int getAuthRequests() {
        return authRequests;
    }

    public int getTotalRequests() {
        return anonRequests + authRequests;
    }
}
