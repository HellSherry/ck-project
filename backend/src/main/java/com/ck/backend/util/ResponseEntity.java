package com.ck.backend.util;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author HellSherrry
 * &#064;date 2023/12/12-14:23
 * @version 1.0
 * &#064;description
 */
@Data
@NoArgsConstructor
public class ResponseEntity<T> {
    private Integer code = 200;
    private String msg= "success";
    private T data;

    public ResponseEntity(T data) {
        this.data = data;
    }
    public static  ResponseEntity ok(Object data) {
        ResponseEntity<Object> response = new ResponseEntity<>();
        response.setData(data);
        response.setCode(200);
        return response;
    }
    public static  ResponseEntity error(String msg) {
        ResponseEntity<Object> response = new ResponseEntity<>();
        response.setCode(502);
        response.setMsg( msg==null|| msg.isEmpty() ? "操作失败":msg);
        return response;
    }
}
