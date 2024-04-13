package org.gycoding.accountsv2.domain.port;

import org.gycoding.accountsv2.domain.model.SessionMO;
import org.gycoding.accountsv2.domain.model.UserMO;
import org.gycoding.accountsv2.domain.model.ServerState;

public interface AccountUsecases {
    Boolean checkLogin(UserMO user);
    UserMO signUp(UserMO user);
    SessionMO getSession(UserMO user);
}
