package org.gycoding.accounts.domain.repository;

import org.gycoding.accounts.domain.model.user.PictureMO;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeyRepository {
    String save(String userId, String apiKey);
    String getUserId(String apiKey);
}
