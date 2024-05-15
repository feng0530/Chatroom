package tw.idv.frank.chatroom.common.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tw.idv.frank.chatroom.common.dto.LoginRes;
import tw.idv.frank.chatroom.manager.model.dto.ManagerRes;
import tw.idv.frank.chatroom.users.model.dto.UsersRes;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class TokenService {

    private Key secretKey;

    private JwtParser jwtParser;

    private final Long ACCESS_EXPIRATION_MILLIS = 1000l * 24;

    private final Long REFRESH_EXPIRATION_MILLIS = 1000l * 60;

    @PostConstruct
    private void init() {
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        jwtParser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();
    }

    public LoginRes generalToken(ManagerRes managerRes) {
        String tokenId = String.valueOf(UUID.randomUUID());

        return new LoginRes(
                createToken(managerRes, tokenId, "Access"),
                createToken(managerRes,tokenId, "Refresh")
        );
    }

    public Claims parseToken(String jwt) throws JwtException {

        try {
            return jwtParser.parseClaimsJws(jwt).getBody();
        }catch (SignatureException e) {
            log.warn("JWT 的簽名無效，表示JWT 被竄改或者使用了錯誤的密鑰驗證");
        }
        catch (MalformedJwtException e) {
            log.warn("JWT 的格式錯誤，可能是JWT 字符串被修改或者製作 JWT 的過程中發生了錯誤");
        }
        catch (ExpiredJwtException e) {
            log.warn("JWT 已過期");
        }
        catch (UnsupportedJwtException e) {
            log.warn("不支持的JWT，表示header 中指定的加密算法不被支持");
        }
        catch (IllegalArgumentException e) {
            log.warn("表示提供了無效的JWT 字串");
        }
        return Jwts.claims();
    }

    private String createToken(ManagerRes managerRes, String tokenId, String subject) {
        // 有效時間 (毫秒)
        long expirationMillis = ACCESS_EXPIRATION_MILLIS;

        if ("Refresh".equals(subject)) {
            expirationMillis = REFRESH_EXPIRATION_MILLIS;
        }

        return Jwts.builder()
                .setId(tokenId)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .claim("detail", managerRes)
                .claim("type", "Manager")
                .signWith(secretKey)
                .compact();
    }

    // UserToken
    public LoginRes generalToken(UsersRes usersRes) {
        String jti = String.valueOf(UUID.randomUUID());

        return new LoginRes(
                createToken(usersRes, jti, "Access"),
                createToken(usersRes, jti,  "Refresh")
        );
    }

    private String createToken(UsersRes usersRes, String jti, String subject) {
        long expirationMillis = ACCESS_EXPIRATION_MILLIS;

        if ("Refresh".equals(subject)) {
            expirationMillis = REFRESH_EXPIRATION_MILLIS;
        }

        return Jwts.builder()
                .setId(jti)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .claim("detail", usersRes)
                .claim("type", "Users")
                .signWith(secretKey)
                .compact();
    }
}
