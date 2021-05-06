package pfu.common.jwt.bean;

public class JwtUser {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 行政区划
     */
    private String adcd;

    /**
     * 角色
     */
    private Integer[] roleId;

    /**
     * 组织机构
     */
    private Integer[] orgId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAdcd() {
        return adcd;
    }

    public void setAdcd(String adcd) {
        this.adcd = adcd;
    }

    public Integer[] getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer[] roleId) {
        this.roleId = roleId;
    }

    public Integer[] getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer[] orgId) {
        this.orgId = orgId;
    }
}
