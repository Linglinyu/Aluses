package cn.ruihe.aluses.common;


import cn.ruihe.aluses.common.message.MessageId;

public class MessageException extends RuntimeException {
    Integer status;
    MessageId id;

    public MessageException(MessageId id) {
        super(id.getCode());
        this.id = id;
    }

    public MessageException(MessageId id, Integer status) {
        super(id.getCode());
        this.id = id;
        this.status = status;
    }

    public MessageException(String message, Integer status) {
        super(message);
        this.id = MessageId.R_CODE_500;
        this.status = status;
    }

    public MessageId getId() {
        return this.id;
    }

    public Integer getStatus() {
        return this.status;
    }
}
