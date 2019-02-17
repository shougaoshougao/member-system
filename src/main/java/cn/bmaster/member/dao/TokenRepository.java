package cn.bmaster.member.dao;

import cn.bmaster.member.entity.system.Token;
import com.renaissance.core.jpa.BaseRepository;

public interface TokenRepository extends BaseRepository<Token, Long> {

    public Token findTopByAccessTokenAndDeleted(String accessToken, boolean deleted);

    public Token findTopByUserIdAndClientIdAndDeleted(Long userId, String clientId, boolean deleted);

}
