package com.viksy.mobilele.service.impl;

import com.viksy.mobilele.model.view.StatsView;
import com.viksy.mobilele.service.StatsService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService {
    private int anonRequests, authRequests;
    @Override
    public void onRequest() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if(authentication != null && (authentication.getPrincipal() instanceof UserDetails)) {
            authRequests++;
        } else {
            anonRequests++;
        }
    }

    @Override
    public StatsView getStats() {
        return new StatsView(anonRequests, authRequests);
    }
}
