package pfu.common.base.entity;

public enum OperationSystem {

    WINDOWS("Windows"),
    LINUX("Linux");

    private final String name;

    OperationSystem(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
