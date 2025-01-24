package org.gycoding.accounts.domain.repository;

import org.gycoding.accounts.domain.model.user.PictureMO;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository {
    PictureMO save(PictureMO picture);
    PictureMO getPicture(String pictureName);
}
