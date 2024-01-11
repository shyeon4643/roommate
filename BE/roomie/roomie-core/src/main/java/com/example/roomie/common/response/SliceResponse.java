package com.example.roomie.common.response;

import com.roommate.roommate.post.dto.response.PostInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SliceResponse<T> {

    private int cuurentPage;
    private int size;
    private Boolean hasNext;
    private List<T> data;

    public SliceResponse(Slice<T> slice){
        this.cuurentPage=slice.getNumber();
        this.size=slice.getNumberOfElements();
        this.hasNext=slice.hasNext();
        this.data=slice.getContent();
    }

}
