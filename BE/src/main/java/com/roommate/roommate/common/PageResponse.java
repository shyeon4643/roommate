package com.roommate.roommate.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    private int totalPage;
    private Long totalElements;
    private int pagingSize;
    private int currentPage;
    private Boolean isFirst;
    private Boolean isLast;
    private Boolean isEmpty;
    private List<T> data;


    public PageResponse (Page<T> page) {
        this.totalPage=page.getTotalPages();
        this.totalElements=page.getTotalElements();
        this.pagingSize=page.getSize();
        this.currentPage=page.getNumber()+1;
        this.isFirst=page.isFirst();
        this.isLast=page.isLast();
        this.isEmpty=page.isEmpty();
        this.data=page.getContent();
    }
}
