package pfu.common.redis.redisson;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.BaseConfig;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;

@Configuration
@ConditionalOnClass(Config.class)
@ConditionalOnBean(RedissonProperties.class)
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonConfiguration {

    private final RedissonProperties redissonProperties;

    public RedissonConfiguration(RedissonProperties redissonProperties) {
        this.redissonProperties = redissonProperties;
    }

    @Bean
    @ConditionalOnProperty(name="pfu.redisson.master-name")
    RedissonClient redissonSentinel() {
        BaseConfig<?> serverConfig;
        switch (this.redissonProperties.getActive()) {
            case "cluster":
                serverConfig = redissonProperties.getCluster();
                break;
            case "replicate":
                serverConfig = redissonProperties.getReplicate();
                break;
            case "sentinel":
                serverConfig = redissonProperties.getSentinel();
                break;
            case "masterSlave":
                serverConfig = redissonProperties.getMasterSlave();
                break;
            case "single":
            default:
                serverConfig = redissonProperties.getSingle();
        }

        return Redisson.create(new Config());
    }

}
