package cn.daenx.framework.common.constant.enums;

public enum MaskedType {
    NAME(0),
    PHONE(1),
    ID_CARD(2),
    BANK_CARD(3),
    EMAIL(4),
    ADDRESS(5),
    IP(6);

    private final int type;

    private MaskedType(int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }
}
