package com.khoa.managementsystem.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.apache.bcel.classfile.Code;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BaseResponse {

    private Meta meta;

    private Integer code;

    private Object data;


//
//    public BaseResponse(Meta meta, Object data) {
//        this.meta = meta;
//        this.data = data;
//    }
//
//    public BaseResponse(Meta meta, Integer code, String message) {
//        this.meta = meta;
//        this.code = code;
//        this.data = message;
//
//    }

    public BaseResponse(Meta meta, Object data) {
        this.meta = meta;
        this.code = meta.getCode(); // Sử dụng code từ Meta nếu có
        this.data = data;
    }

    public BaseResponse(Meta meta, Integer code, String message) {
        this.meta = meta;
        this.code = (code != null) ? code : meta.getCode(); // Đảm bảo code không bị null
        this.data = message;
    }




//    public BaseResponse(Meta meta) {
//        this.meta = meta;
//    }
//
//    public BaseResponse(Meta meta, Object data) {
//        this.meta = meta;
//        this.data = data;
//    }
//
//    public BaseResponse() {
//    }

    //vd về non-arg constructor và all-arg constructor
}
