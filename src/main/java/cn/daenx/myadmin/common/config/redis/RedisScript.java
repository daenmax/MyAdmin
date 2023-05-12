package cn.daenx.myadmin.common.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.script.DefaultRedisScript;


/**
 * Redis LUA脚本
 *
 * @author DaenMax
 */
@Configuration
public class RedisScript {

    /**
     * 限流脚本
     *
     * @return
     */
    @Bean
    public DefaultRedisScript<Long> limitScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(limitScriptText());
        redisScript.setResultType(Long.class);
        return redisScript;
    }

    private String limitScriptText() {
        return "local key = KEYS[1]\n" +
                "local count = tonumber(ARGV[1])\n" +
                "local time = tonumber(ARGV[2])\n" +
                "local current = redis.call('get', key);\n" +
                "if current and tonumber(current) > count then\n" +
                "    return tonumber(current);\n" +
                "end\n" +
                "current = redis.call('incr', key)\n" +
                "if tonumber(current) == 1 then\n" +
                "    redis.call('expire', key, time)\n" +
                "end\n" +
                "return tonumber(current);";
    }

    /**
     * 邮箱轮询队列脚本
     *
     * @return
     */
    @Bean
    public DefaultRedisScript<String> nextEmailScript() {
        DefaultRedisScript<String> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(nextEmailScriptText());
        redisScript.setResultType(String.class);
        return redisScript;
    }

    private String nextEmailScriptText() {
        return "local key = KEYS[1];\n" +
                "local email= redis.call('rpop',key);\n" +
                "redis.call('lpush',key,email);\n" +
                "return email;";
    }
}
