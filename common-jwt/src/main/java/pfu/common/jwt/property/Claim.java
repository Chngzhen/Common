package pfu.common.jwt.property;

public class Claim {

    private Integer expiredInDays = 0;

    public Integer getExpiredInDays() {
        return expiredInDays;
    }

    public void setExpiredInDays(Integer expiredInDays) {
        this.expiredInDays = expiredInDays;
    }
}
