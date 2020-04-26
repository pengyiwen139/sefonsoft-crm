package com.sefonsoft.oa.service.common;

import com.sefonsoft.oa.system.utils.PageResponse;

import java.util.Collections;
import java.util.List;

public class PageableResult<T> {
  
    public static final PageableResult<?> EMPTY_RESULT = new PageableResult<>(0L, Collections.emptyList());
  
  
  
    public Long totalCount;
    public List<T> resultList;

    public PageableResult(Long count, List<T> list) {
        if (count == null) {
            this.totalCount = 0L;
        } else {
            this.totalCount = count;
        }
        this.resultList = list;
    }

    public PageResponse PageResponse() {
        if (resultList != null) {
            return new PageResponse<>(totalCount.intValue(), resultList);
        } else {
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    public static <T> PageableResult<T> emptyResult() {
      return (PageableResult<T>) EMPTY_RESULT;
    }
    
    
    
}
