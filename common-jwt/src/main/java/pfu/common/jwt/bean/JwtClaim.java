package pfu.common.jwt.bean;

import java.time.LocalDateTime;

public class JwtClaim {

    private LocalDateTime issueAt;

    private String audience;

    public LocalDateTime getIssueAt() {
        return issueAt;
    }

    public void setIssueAt(LocalDateTime issueAt) {
        this.issueAt = issueAt;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }
}
