package com.roommate.roommate.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SliceResponse<T> {

    private int cuurentPage;
    private int size;
    private Boolean hasNext;
    private List<T> data;

    public SliceResponse (Slice<T> slice){
        this.cuurentPage=slice.getNumber();
        this.size=slice.getNumberOfElements();
        this.hasNext=slice.hasNext();
        this.data=slice.getContent();
    }
}
