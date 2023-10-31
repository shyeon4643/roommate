import axios from "axios";
import React, { useLocation , useState } from "react";
import "../../css/board.css";
import BoardTable from "./boardTable";

function BoardSearch(){
    const location = useLocation();
    const postData = location.state.postData;
    const keyword = location.state.keyword;

    return(
        <div>
            <h1 className="categoryName">{keyword}에 대한 검색 결과입니다.</h1>
            <BoardTable postData={postData} />
        </div>
    )
}

export default BoardSearch;