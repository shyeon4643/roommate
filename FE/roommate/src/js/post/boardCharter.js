import axios from "axios";
import React, { useEffect, useState } from "react";
import "../../css/board.css";
import BoardTable from "./boardTable";

function BoardCharter(){
    const [postData, setPostData] = useState();
    const category ="charter";

    useEffect(()=>{
        try{
            axios({
                method: "GET",
                url: `/${category}/posts`
            }).then((response) =>{
                console.log(response.data.data);
                setPostData(response.data.data);
            });
        }catch(error){
            console.error("게시물 데이터를 가져오는 중 에러가 발생했습니다.", error);
        }
    },[category])
    return(
        <div>
            <h1 className="categoryName">전세</h1>
            <BoardTable postData={postData} />
        </div>
    )
}

export default BoardCharter;