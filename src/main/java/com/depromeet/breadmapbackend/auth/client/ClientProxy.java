package com.depromeet.breadmapbackend.auth.client;

import com.depromeet.breadmapbackend.members.domain.Members;

public interface ClientProxy {

    Members getUserData(String accessToken);
}
