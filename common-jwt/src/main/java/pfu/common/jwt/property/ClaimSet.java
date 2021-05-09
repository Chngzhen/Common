package pfu.common.jwt.property;

public class ClaimSet {

    private Integer expiredInDays = 0;

    private String issuer;

    private String subject;

    public Integer getExpiredInDays() {
        return expiredInDays;
    }

    public void setExpiredInDays(Integer expiredInDays) {
        this.expiredInDays = expiredInDays;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
