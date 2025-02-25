package com.emailer.service;

import com.emailer.model.dto.ReleaseDTO;
import com.emailer.model.dto.ReleaseQueryDTO;
import java.util.List;

public interface RodService {
    List<ReleaseDTO> getReleases(ReleaseQueryDTO queryDTO);
} 