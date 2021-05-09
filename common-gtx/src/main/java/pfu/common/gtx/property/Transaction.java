package pfu.common.gtx.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

@ConfigurationProperties(prefix = Transaction.PREFIX)
public class Transaction {

    public static final String PREFIX = "pfu.transaction";

    private String pointcut;

    private int timeout;

    private Class<?>[] rollback = { Throwable.class };

    @NestedConfigurationProperty
    private Rule rule = new Rule();

    public String getPointcut() {
        return pointcut;
    }

    public void setPointcut(String pointcut) {
        this.pointcut = pointcut;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public Class<?>[] getRollback() {
        return rollback;
    }

    public void setRollback(Class<?>[] rollback) {
        this.rollback = rollback;
    }
}
