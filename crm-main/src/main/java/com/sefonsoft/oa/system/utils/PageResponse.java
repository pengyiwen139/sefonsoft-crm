package com.sefonsoft.oa.system.utils;

import java.util.List;

/**
 * 分页对象封装
 *
 * @author Aron
 * @version 0.0.1
 */
public class PageResponse<T> {

    /**
     * 分页信息
     */
    private Page page;

    /**
     * 数据行
     */
    private List<T> rows;

    public PageResponse() {
    }

    public PageResponse(int totalRows, List<T> rows) {
        page = new Page(totalRows);
        this.rows = rows;
    }

    public PageResponse(int totalRows, int attachInfo, List<T> rows) {
        page = new Page(totalRows, attachInfo);
        this.rows = rows;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public class Page {
        // 附加信息（可选参数），客户端提交数据，服务端原样返回
        private int attachInfo;
        // 总行数
        private int totalRows;

        public Page() {
        }

        public int getAttachInfo() {
            return attachInfo;
        }

        public void setAttachInfo(int attachInfo) {
            this.attachInfo = attachInfo;
        }

        public Page(int totalRows) {
            this.totalRows = totalRows;
        }

        public Page(int totalRows, int attachInfo) {
            this.attachInfo = attachInfo;
            this.totalRows = totalRows;
        }

        public int getTotalRows() {
            return totalRows;
        }

        public void setTotalRows(int totalRows) {
            this.totalRows = totalRows;
        }
    }

    @Override
    public String toString() {
        return "PageResponse{" +
            "page=" + page +
            ", rows=" + rows +
            '}';
    }
}
