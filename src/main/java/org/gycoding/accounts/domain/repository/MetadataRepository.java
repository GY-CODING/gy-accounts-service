package org.gycoding.accounts.domain.repository;

import org.gycoding.accounts.domain.model.user.PictureMO;
import org.gycoding.accounts.domain.model.user.metadata.MetadataMO;
import org.gycoding.exceptions.model.APIException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MetadataRepository {
    MetadataMO save(MetadataMO metadata);
    MetadataMO update(MetadataMO metadata) throws APIException;
    MetadataMO refresh(MetadataMO metadata) throws APIException;

    Optional<MetadataMO> get(String userId);
    Optional<MetadataMO> get(UUID profileId);
    Optional<MetadataMO> getByApiKey(String apiKey);

    List<MetadataMO> list(String query);
}
