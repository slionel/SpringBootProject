package com.zsj.model;

/**
 * @author zsj55
 */
public class AjaxResponse {
    /**
     * isOk 返回给前端，是否成功
     * code 成功后的状态码200，失败后对应的状态码
     * message 成功后返回信息为success，失败返回fail
     * data 如果是查询，将查询结果放在data中
     */
    private boolean isOk;
    private int code;
    private String message;
    private Object data;

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


    public static AjaxResponse success(){
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setOk(true);
        ajaxResponse.setCode(200);
        ajaxResponse.setMessage("success");
        return ajaxResponse;
    }

    public static AjaxResponse success(Object data){
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setOk(true);
        ajaxResponse.setCode(200);
        ajaxResponse.setMessage("success");
        ajaxResponse.setData(data);
        return ajaxResponse;
    }
}
