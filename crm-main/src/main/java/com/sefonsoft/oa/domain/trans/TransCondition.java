package com.sefonsoft.oa.domain.trans;
import java.util.Date;

/**
 * Trans查询条件
 *
 * @author Aron
 * @version 1.0.0
 */
public class TransCondition {
    private String transName;
    private Date startTime;
    private Date endTime;
    private Integer page;
    private Integer rows;
    // private Integer offset;

    public TransCondition() {
    }


    public Integer getOffset() {
        return page != null && rows != null ? (page - 1) * rows : null;
    }

    public String getTransName() {
        return transName;
    }

    public void setTransName(String transName) {
        this.transName = transName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}