package cn.ruihe.aluses.common.message;


public enum MessageId {
    R_CODE_000("r.code.000"),
    R_CODE_200("r.code.200"),
    R_CODE_404("r.code.404"),
    R_CODE_500("r.code.500"),
    E_CODE_201("e.code.201"),
    E_CODE_202("e.code.202"),
    E_CODE_203("e.code.203"),
    E_CODE_204("e.code.204");

    private String code;

    private MessageId(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
