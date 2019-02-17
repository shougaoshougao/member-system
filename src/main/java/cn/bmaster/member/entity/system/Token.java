/**
 * 
 */
package cn.bmaster.member.entity.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.renaissance.core.entity.BaseEntity;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Wilson
 */
@Entity
@Table(name = "token")
public class Token extends BaseEntity<Token> implements Serializable {

    /** 认证用户标识  */
    @JsonIgnore
    private Long userId;
    
    /** 客户端标识  */
    private String clientId;
    
    /** 钥匙  - 用于访问认证   */
    private String accessToken;
    
    /** 刷新钥匙  - 用于刷新钥匙    */
    private String refreshToken;
    
    /** 钥匙过期时间    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireTime;
    
    /** 钥匙最后刷新时间    */
    @JsonIgnore
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime refreshTime;
    
    /**
     * The default constructor
     */
    public Token() {
        
    }

    /**
     * 
     * @param userId
     * @param clientId
     */
    public Token(Long userId, String clientId) {
        this(userId, clientId, 180);
    }
    
    /**
     * 
     * @param userId
     * @param clientId
     * @param expireDuration
     */
    public Token(Long userId, String clientId, int expireDuration) {
        this.userId = userId;
        this.clientId = clientId;
        this.accessToken = UUID.randomUUID().toString();
        this.refreshToken = UUID.randomUUID().toString();
        this.createTime = LocalDateTime.now();
        this.expireTime = LocalDateTime.now().plusMinutes(expireDuration > 0 ? expireDuration : 180L);  // set expire time in expireDuration
    }
    
    /**
     * 
     * @return
     */
    public Token refresh() {
        return refresh(180);
    }
    
    /**
     * 
     * @param expireDuration
     * @return
     */
    public Token refresh(int expireDuration) {

        this.accessToken = UUID.randomUUID().toString();
        this.refreshToken = UUID.randomUUID().toString();
        this.refreshTime = LocalDateTime.now();
        this.expireTime =  this.refreshTime.plusMinutes(expireDuration > 0 ? expireDuration : 180); // set expire time in 180 minutes
        return this;
    }
    
    /**
     * 
     * @param accessToken
     * @param refreshToken
     * @return
     */
    public boolean checkToken(String accessToken, String refreshToken) {
        return this.checkAccessToken(accessToken) && this.checkRefreshToken(refreshToken);
    }
    
    /**
     * check whether access token matches
     * 
     * @param accessToken
     * @return
     */
    public boolean checkAccessToken(String accessToken) {
        return StringUtils.equals(this.accessToken, accessToken) && !isExpire();
    }
    
    /**
     * check whether refresh token matches
     * 
     * @param refreshToken
     * @return
     */
    public boolean checkRefreshToken(String refreshToken) {
        return StringUtils.equals(this.refreshToken, refreshToken);
    }
    
    /**
     * check userId
     * 
     * @param userId
     * @return
     */
    public boolean checkUserId(Long userId) {
        return userId != null && this.userId.equals(userId);
    }
    
    /**
     * 
     * @return
     */
    @JsonIgnore
    public boolean isExpire() {
        return this.expireTime.isBefore(LocalDateTime.now());
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }

    public LocalDateTime getRefreshTime() {
        return refreshTime;
    }

    public void setRefreshTime(LocalDateTime refreshTime) {
        this.refreshTime = refreshTime;
    }
}
