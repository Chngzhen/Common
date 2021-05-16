package pfu.common.base.entity;

public enum FileContentType {

    PNG("png", "image/png"),
    XLSX("xlsx", "application/vnd.*");

    private final String suffix;
    private final String contentType;
    FileContentType(String suffix, String contentType) {
        this.suffix = suffix;
        this.contentType = contentType;
    }

}
