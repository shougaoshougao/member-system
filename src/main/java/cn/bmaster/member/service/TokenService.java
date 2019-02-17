package cn.bmaster.member.service;

import cn.bmaster.member.dao.TokenRepository;
import cn.bmaster.member.entity.system.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("tokenService")
@Transactional
public class TokenService {

    private static final String ACCESS_TOKEN_CACHE_PRIFIX = "ACCESS_TOKEN:";

    @Value("${project.token.expire:180}")
    private int expireDuration;
    
	@Autowired
	private TokenRepository tokenRepository;

	@Transactional(readOnly = true)
	public Token load(Long userId, String clientId) {
		return tokenRepository.findTopByUserIdAndClientIdAndDeleted(userId, clientId, false);
	}

	@Transactional(readOnly = true)
	public Token load(String accessToken) {
	    return tokenRepository.findTopByAccessTokenAndDeleted(accessToken, false);
	}

	@Transactional(readOnly = true)
    public boolean verifyToken(String accessToken) {

	    Token token = load(accessToken);

	    return token != null && !token.isExpire();
    }

	@Transactional(readOnly = true)
    public Long getUserId(String accessToken) {

		Token token = load(accessToken);
            
        return token != null ? token.getUserId() : null;
    }

	public Token create(Token token) {
	    return tokenRepository.save(token.initialize(token.getUserId()));
	}

	public void refresh(Token token) {
        tokenRepository.save(token.refresh(expireDuration).recordEdit(token.getUserId()));
	}

	public void delete(Token token) {
        tokenRepository.save(token.delete(token.getUserId()));
	}

 }
