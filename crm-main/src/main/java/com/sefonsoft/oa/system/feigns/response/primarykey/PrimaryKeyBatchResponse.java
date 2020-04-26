package com.sefonsoft.oa.system.feigns.response.primarykey;

/**
 * @author Aron
 * @version 1.0.0
 */
public class PrimaryKeyBatchResponse {
    private Long begin;
    private Long end;

    public PrimaryKeyBatchResponse() {
    }

    public Long getBegin() {
        return begin;
    }

    public void setBegin(Long begin) {
        this.begin = begin;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }
}
