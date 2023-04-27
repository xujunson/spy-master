package com.atu.monitor.dto;

import com.atu.monitor.dto.enums.ResultStatusEnum;
import com.atu.monitor.exception.ParamsRuntimeException;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author: Tom
 * @create: 2023-04-25 13:22
 * @Description:
 */
public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = -7602280271453240278L;
    private String message;
    private String status;
    private T data;
    /**
     * 请求返回时间
     */
    private Long timestamp;

    private ResultBean(Builder builder) {
        this.message = builder.message;
        this.status = builder.status;
        this.data = (T) builder.data;
        this.timestamp = builder.timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "message='" + message + '\'' +
                ", status='" + status + '\'' +
                ", data=" + data +
                ", timestamp=" + timestamp +
                '}';
    }

    public static class Builder<T> {
        private String message = ResultStatusEnum.SUCCESS.getMessage();
        private String status = ResultStatusEnum.SUCCESS.getStatus();
        private T data;
        /**
         * 请求返回时间
         */
        private Long timestamp = System.currentTimeMillis();


        public ResultBean build() {
            if (Objects.isNull(status)) {
                throw new ParamsRuntimeException("status must be exist");
            }
            return new ResultBean(this);
        }

        public Builder statusEnum(ResultStatusEnum statusEnum) {
            this.init(statusEnum);
            return this;
        }

        public Builder fail(T data) {
            this.init(ResultStatusEnum.ERROR);
            this.data = data;
            return this;
        }

        public Builder success(T data) {
            this.init(ResultStatusEnum.SUCCESS);
            this.data = data;
            return this;
        }

        public Builder success() {
            this.init(ResultStatusEnum.SUCCESS);
            return this;
        }

        private void init(ResultStatusEnum statusEnum) {
            this.status = statusEnum.getStatus();
            this.message = statusEnum.getMessage();
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder data(T data) {
            this.data = data;
            return this;
        }
    }
}

