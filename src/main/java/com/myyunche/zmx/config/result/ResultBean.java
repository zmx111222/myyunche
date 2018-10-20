package com.myyunche.zmx.config.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.myyunche.zmx.config.constant.BaseExceptionEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Collection;

@Data
public class ResultBean<T> implements Serializable {

    private static final long serialVersionUID = -2361820086956983473L;

    // 数据明细
    private T data;

    private Boolean success;

    private String code;

    private String msg;

    /**
     * 为null时,不参与序列化
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalNum;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer pageIndex;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer pageSize;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer totalPage;

    public static <T> ResultBean of(T data, boolean success, BaseExceptionEnum baseExceptionEnum) {
        if (null != baseExceptionEnum) {
            return of(data, success, baseExceptionEnum.getCode(), baseExceptionEnum.getMessage());
        } else {
            return of(data, success, null, null);
        }
    }

    public static <T> ResultBean of(T data, boolean success, String code, String msg) {
        ResultBean resultBean = new ResultBean<>();
        resultBean.setData(data);
        resultBean.setSuccess(success);
        resultBean.setCode(code);
        resultBean.setMsg(msg);
        if (data instanceof Collection) {
            resultBean.setTotalNum(((Collection) data).size());
        }
        return resultBean;
    }

    public static <T> ResultBean<T> of(T data, boolean success, BaseExceptionEnum baseExceptionEnum, Integer totalNum, Integer pageIndex, Integer pageSize) {
        return of(data, success, baseExceptionEnum, totalNum, pageIndex, pageSize, null);
    }

    public static <T> ResultBean<T> of(T data, boolean success, BaseExceptionEnum baseExceptionEnum, Integer totalNum, Integer pageIndex, Integer pageSize, String msg) {
        ResultBean resultBean = new ResultBean();
        resultBean.setData(data);
        resultBean.setSuccess(success);
        if (null != baseExceptionEnum) {
            resultBean.setCode(baseExceptionEnum.getCode());
            resultBean.setMsg(baseExceptionEnum.getMessage());
        }
        if (data instanceof Collection && null == totalNum) {
            resultBean.setTotalNum(((Collection) data).size());
        }
        resultBean.setTotalNum(totalNum);
        resultBean.setPageIndex(pageIndex);
        resultBean.setPageSize(pageSize);
        resultBean.setTotalPage((pageSize == null || pageSize == 0) ? null : (totalNum % pageSize == 0 ? totalNum / pageSize : (totalNum / pageSize + 1)));
        resultBean.setMsg(msg);
        return resultBean;
    }

    public static <T> ResultBean<T> ofSuccess(T data) {
        return of(data, true, BaseExceptionEnum.EC00000200);
    }

    public static <T> ResultBean<T> ofSuccess(T data, Integer totalNum, Integer pageIndex, Integer pageSize, String msg) {
        return of(data, true, BaseExceptionEnum.EC00000200, totalNum, pageIndex, pageSize, msg);
    }

    public static <T> ResultBean<T> ofSuccess(T data, Integer totalNum, Integer pageIndex, Integer pageSize) {
        return of(data, true, BaseExceptionEnum.EC00000200, totalNum, pageIndex, pageSize);
    }

    public static <T> ResultBean<T> ofSuccess(T data, BaseExceptionEnum baseExceptionEnum) {
        return of(data, true, baseExceptionEnum);
    }

    public static <T> ResultBean<T> ofSuccess(T data, String msg) {
        return of(data, true, BaseExceptionEnum.EC00000200.getCode(), msg);
    }

    public static ResultBean ofError(BaseExceptionEnum baseExceptionEnum) {
        ResultBean resultBean = new ResultBean();
        resultBean.setSuccess(false);
        if (null != baseExceptionEnum) {
            resultBean.setCode(baseExceptionEnum.getCode());
            resultBean.setMsg(baseExceptionEnum.getMessage());
        }
        resultBean.setData(null);
        return resultBean;
    }

    public static ResultBean ofError(String msg) {
        ResultBean resultBean = new ResultBean();
        resultBean.setSuccess(false);
        resultBean.setCode(BaseExceptionEnum.EC00000500.getCode());
        resultBean.setMsg(msg);
        resultBean.setData(null);
        return resultBean;
    }
}

