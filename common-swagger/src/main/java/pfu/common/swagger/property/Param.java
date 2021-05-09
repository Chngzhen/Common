package pfu.common.swagger.property;

/**
 * Swagger公共参数配置类。<p></p>
 *
 * 在利用Swagger接口文档页面的调试功能时，有时候除了接口参数，还需要提供诸如Token的公共参数或头信息。
 *
 * @author Chngzhen
 * @since 2021-05-09
 */
public class Param {

    /**
     * 参数名称。
     */
    private String name;

    /**
     * 参数描述。
     */
    private String description;

    /**
     * 数据类型。
     */
    private String dataType;

    /**
     * 参数类型。取值：header、cookie、body、query等。
     */
    private String paramType;

    /**
     * 是否必填。默认：false。
     */
    private Boolean required = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }
}
