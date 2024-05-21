package tw.idv.frank.chatroom.common.service.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class JwtBlackListService {

    @Value("${jwt.expiration.refresh}")
    private Long expirationMillis = 1000l * 60;

    private final RedisTemplate<String, String> redisTemplate;

    private static final String BLACKLIST_PREFIX = "jwt:blacklist:";

    public void addJwtToBlackList(String jti) {
        String key = BLACKLIST_PREFIX + jti;
        Duration expirationDuration = Duration.ofMillis(expirationMillis);
        redisTemplate.opsForValue().set(key, "disable", expirationDuration);
    }

    public boolean isJwtInBlackList(String jti) {
        String key = BLACKLIST_PREFIX + jti;
        return redisTemplate.opsForValue().get(key) != null;
    }
}
