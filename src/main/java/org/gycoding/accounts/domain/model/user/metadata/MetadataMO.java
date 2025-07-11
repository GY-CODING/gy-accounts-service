package org.gycoding.accounts.domain.model.user.metadata;

import lombok.*;
import org.gycoding.accounts.domain.model.user.metadata.books.BooksMetadataMO;
import org.gycoding.accounts.domain.model.user.metadata.messages.MessagesMetadataMO;

@Builder
public record MetadataMO (
        String userId,
        BooksMetadataMO books,
        MessagesMetadataMO messages,
        ProfileMO profile
) { }