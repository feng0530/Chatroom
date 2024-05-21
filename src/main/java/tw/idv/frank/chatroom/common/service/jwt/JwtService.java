package tw.idv.frank.chatroom.common.service.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tw.idv.frank.chatroom.common.dto.LoginRes;
import tw.idv.frank.chatroom.manager.model.dto.ManagerRes;
import tw.idv.frank.chatroom.users.model.dto.UsersRes;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Service
public class JwtService {

    private Key secretKey;

    private JwtParser jwtParser;

    @Value("${jwt.expiration.access}")
    private Long accessExpirationMillis;

    @Value("${jwt.expiration.refresh}")
    private Long refreshExpirationMillis;

    @Autowired
    private JwtBlackListService jwtBlackListService;

    @PostConstruct
    private void init() {
        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        jwtParser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();
    }

    public LoginRes generalToken(ManagerRes managerRes) {
        String jti = String.valueOf(UUID.randomUUID());

        return new LoginRes(
                createToken(managerRes, jti, "Access"),
                createToken(managerRes, jti, "Refresh")
        );
    }

    public Claims parseToken(String jwt) throws JwtException {

        try {
            Claims claims = jwtParser.parseClaimsJws(jwt).getBody();

            if (jwtBlackListService.isJwtInBlackList(claims.getId())) {
                log.warn("JWT ID無效，表示已經登出或者已經被鎖定");
                return Jwts.claims();
            }
            return claims;
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

    private String createToken(ManagerRes managerRes, String jti, String subject) {
        // 有效時間 (毫秒)
        long expirationMillis = accessExpirationMillis;

        if ("Refresh".equals(subject)) {
            expirationMillis = refreshExpirationMillis;
        }

        return Jwts.builder()
                .setId(jti)
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
        long expirationMillis = accessExpirationMillis;

        if ("Refresh".equals(subject)) {
            expirationMillis = refreshExpirationMillis;
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
