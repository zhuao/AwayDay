package com.thoughtworks.mobile.awayday.service;

import com.thoughtworks.mobile.awayday.domain.Footprint;

import java.util.List;

public abstract interface FetchTrackService {
    public abstract List<Footprint> fetchAllFootprints();
}