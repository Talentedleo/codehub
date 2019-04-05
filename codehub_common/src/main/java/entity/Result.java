package entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/17
 *
 * 返回结果
 */
@Getter
@Setter
public class Result implements Serializable{

    private Boolean flag; //返回结果状态

    private Integer code; //返回状态码

    private String message; //返回消息

    private Object data; //返回数据内容

    public Result() {
    }

    public Result(Boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }

    public Result(Boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
