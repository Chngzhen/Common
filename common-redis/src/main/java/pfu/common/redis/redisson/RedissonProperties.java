package pfu.common.redis.redisson;

import org.redisson.config.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "pfu.redisson")
@ConditionalOnProperty(name = "pfu.redisson.enable", havingValue = "true")
public class RedissonProperties {

    private String active;

    private ClusterServersConfig cluster;

    private ReplicatedServersConfig replicate;

    private SingleServerConfig single;

    private SentinelServersConfig sentinel;

    private MasterSlaveServersConfig masterSlave;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public ClusterServersConfig getCluster() {
        return cluster;
    }

    public void setCluster(ClusterServersConfig cluster) {
        this.cluster = cluster;
    }

    public ReplicatedServersConfig getReplicate() {
        return replicate;
    }

    public void setReplicate(ReplicatedServersConfig replicate) {
        this.replicate = replicate;
    }

    public SingleServerConfig getSingle() {
        return single;
    }

    public void setSingle(SingleServerConfig single) {
        this.single = single;
    }

    public SentinelServersConfig getSentinel() {
        return sentinel;
    }

    public void setSentinel(SentinelServersConfig sentinel) {
        this.sentinel = sentinel;
    }

    public MasterSlaveServersConfig getMasterSlave() {
        return masterSlave;
    }

    public void setMasterSlave(MasterSlaveServersConfig masterSlave) {
        this.masterSlave = masterSlave;
    }
}