package web.bo;

import lombok.Data;

@Data
public class ResponseResult<T> {
    private int code = 0;
    private String msg;
    private boolean success;
    private T data;

    public static<T> ResponseResult<T> success(T data){
        ResponseResult result = new ResponseResult();
        result.setData(data);
        result.setSuccess(true);
        result.setCode(0);
        return result;

    }

}
