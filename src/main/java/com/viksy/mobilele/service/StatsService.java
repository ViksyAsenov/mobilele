package com.viksy.mobilele.service;

import com.viksy.mobilele.model.view.StatsView;

public interface StatsService {
    void onRequest();
    StatsView getStats();
}
