package pfu.common.swagger.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "pfu.swagger")
public class Swagger {

    /**
     * 文档标题。
     */
    private String title;

    /**
     * 文档描述。
     */
    private String description;

    /**
     * 联系人。
     */
    private String contact;

    /**
     * 联系人地址。
     */
    private String contactUrl;

    /**
     * 联系人邮箱。
     */
    private String contactEmail;

    /**
     * 版本。
     */
    private String version;

    /**
     * 扫描的包路径。
     */
    private String basePackage;

    /**
     * 分组。
     */
    private String groupName;

    /**
     * 公共参数列表。
     */
    private List<Param> parameters;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContactUrl() {
        return contactUrl;
    }

    public void setContactUrl(String contactUrl) {
        this.contactUrl = contactUrl;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public List<Param> getParameters() {
        return parameters;
    }

    public void setParameters(List<Param> params) {
        this.parameters = params;
    }
}
