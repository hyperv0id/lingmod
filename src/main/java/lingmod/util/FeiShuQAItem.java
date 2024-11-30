package lingmod.util;

public enum FeiShuQAItem {
    REPORT_TYPE("反馈类型"),
    DESCRIPTION("问题描述"),
    MOD_ENABLED("MOD启用"),
    ERR_CAUSE("报错信息"),
    ;

    private final String name;

    FeiShuQAItem(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}