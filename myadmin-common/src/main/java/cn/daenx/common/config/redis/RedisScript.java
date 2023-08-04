package cn.daenx.common.config.redis;

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
     * 接口限流脚本
     *
     * @return
     */
    @Bean
    public DefaultRedisScript<Long> apiLimitScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(apiLimitScriptText());
        redisScript.setResultType(Long.class);
        return redisScript;
    }

    private String apiLimitScriptText() {
        return "redis.replicate_commands();\n" +
                "local singleKey = KEYS[1]\n" +
                "local singleUserKey = KEYS[2]\n" +
                "local wholeKey = KEYS[3]\n" +
                "local wholeLimiterKey = KEYS[4]\n" +
                "local currentTime = redis.call('TIME')[1]\n" +
                "local currentTimeMiss = redis.call('TIME')[1]..redis.call('TIME')[2]\n" +
                "if redis.call('EXISTS',singleKey)==1 then\n" +
                "\tlocal maxSize = tonumber(redis.call('hget',singleKey,'max'))\n" +
                "\tlocal outTime = tonumber(redis.call('hget',singleKey,'outTime'))\n" +
                "\tlocal currentLen = redis.call('ZCARD',singleUserKey)\n" +
                "\tif  currentLen < maxSize then\n" +
                "\t\tredis.call('ZADD',singleUserKey,currentTime,currentTimeMiss)\n" +
                "\t\tredis.call('expire',singleUserKey,outTime)\n" +
                "\telse\n" +
                "\t\tlocal minTime = currentTime - outTime\n" +
                "\t\tlocal effectiveNum = redis.call('ZCOUNT',singleUserKey,minTime,currentTime)\n" +
                "\t\tif  effectiveNum < maxSize and redis.call('ZREMRANGEBYSCORE',singleUserKey,0,minTime) > 0 then\n" +
                "\t\t\tredis.call('ZADD',singleUserKey,currentTime,currentTimeMiss)\n" +
                "\t\t\tredis.call('expire',singleUserKey,outTime)\n" +
                "\t\telse\n" +
                "\t\t\treturn -1\n" +
                "\t\tend\n" +
                "\tend\n" +
                "end\n" +
                "if redis.call('EXISTS',wholeKey)==1 then\n" +
                "\tlocal maxSizeWhole = tonumber(redis.call('hget',wholeKey,'max'))\n" +
                "\tlocal outTimeWhole = tonumber(redis.call('hget',wholeKey,'outTime'))\n" +
                "\tlocal currentLenWhole = redis.call('ZCARD',wholeLimiterKey)\n" +
                "\tif currentLenWhole < maxSizeWhole then\n" +
                "\t\tredis.call('ZADD',wholeLimiterKey,currentTime,currentTimeMiss)\n" +
                "\t\tredis.call('expire',wholeLimiterKey,outTimeWhole)\n" +
                "\telse\n" +
                "\t\tlocal minTimeWhole = currentTime - outTimeWhole\n" +
                "\t\tlocal effectiveNumWhole = redis.call('ZCOUNT',wholeLimiterKey,minTimeWhole,currentTime)\n" +
                "\t\tif  effectiveNumWhole < maxSizeWhole and redis.call('ZREMRANGEBYSCORE',wholeLimiterKey,0,minTimeWhole) > 0 then\n" +
                "\t\t\tredis.call('ZADD',wholeLimiterKey,currentTime,currentTimeMiss)\n" +
                "\t\t\tredis.call('expire',wholeLimiterKey,outTimeWhole)\n" +
                "\t\telse\n" +
                "\t\t\treturn -2\n" +
                "\t\tend\n" +
                "\tend\n" +
                "end\n" +
                "return 0\n";
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
