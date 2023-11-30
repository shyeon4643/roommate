import axios from "axios";
import React, { useEffect, useState } from "react";
import "../../css/board.css";
import BoardTable from "./boardTable";

function BoardMonthly(){
    const [postData, setPostData] = useState();
    const category ="monthly";

    useEffect(()=>{
        try{
            axios({
                headers: {
                    'JWT': localStorage.getItem('JWT'),
                },
                method: "GET",
                url: `/posts/${category}`
            }).then((response) =>{
                setPostData(response.data.data.data);
            });
        }catch(error){
            console.error("게시물 데이터를 가져오는 중 에러가 발생했습니다.", error);
        }
    },[category])
    return(
        <div>
            <h1 className="categoryName">월세</h1>
            <BoardTable postData={postData} />
        </div>
    )
}

export default BoardMonthly;