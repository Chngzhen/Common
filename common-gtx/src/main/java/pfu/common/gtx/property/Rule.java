package pfu.common.gtx.property;

public class Rule {

    private String[] readOnly = { "get*", "query*", "find*", "select*", "count*", "fetch*" };

    private String[] required = { "add*", "save*", "insert*","modify*", "update*", "remove*", "delete*", "batch*" };

    public String[] getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(String[] readOnly) {
        this.readOnly = readOnly;
    }

    public String[] getRequired() {
        return required;
    }

    public void setRequired(String[] required) {
        this.required = required;
    }
}
