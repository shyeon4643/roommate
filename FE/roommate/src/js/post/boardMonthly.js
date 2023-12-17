import axios from "axios";
import React, { useEffect, useState } from "react";
import "../../css/board.css";
import BoardTable from "./boardTable";

function BoardMonthly(){
    const [postData, setPostData] = useState();
    const [page, setPage] = useState(0);
    const category ="monthly";
    const size = 10;

    useEffect(()=>{
        try{
            const jwtToken = localStorage.getItem('JWT');
            if (!jwtToken) {
                alert("로그인이 필요합니다.");
                window.location.href = "/login";
            }
            axios({
                headers: {
                    'JWT': localStorage.getItem('JWT'),
                },
                method: "GET",
                url: `/posts/${category}?page=${page}&size=${size}`
            }).then((response) =>{
                console.log(response.data.data);
                setPostData(response.data.data.data);
            });
        }catch(error){
            console.error("게시물 데이터를 가져오는 중 에러가 발생했습니다.", error);
        }
    },[category, page])
    return(
        <div>
            <h1 className="categoryName">월세</h1>
            <BoardTable postData={postData} />
        </div>
    )
}

export default BoardMonthly;