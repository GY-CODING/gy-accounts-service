package org.gycoding.accountsv2.application.usecases;

import org.gycoding.accountsv2.domain.model.SessionMO;
import org.gycoding.accountsv2.domain.model.UserMO;

public interface AccountUsecases {
    Boolean checkLogin(UserMO user);
    UserMO signUp(UserMO user);
    SessionMO getSession(UserMO user);
}
