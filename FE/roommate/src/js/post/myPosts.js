import axios from "axios";
import React, { useEffect, useState } from "react";
import "../../css/board.css";
import BoardTable from "./boardTable";

function MyPosts(){
    const [postData, setPostData] = useState();
    const category ="monthly";

    useEffect(()=>{
        try{
            axios({
                method: "GET",
                url: `/user/posts`
            }).then((response) =>{
                setPostData(response.data);
            });
        }catch(error){
            console.error("게시물 데이터를 가져오는 중 에러가 발생했습니다.", error);
        }
    },[category])
    return(
        <div>
            <h1 className="categoryName">내가 쓴 게시글</h1>
            <BoardTable postData={postData} />
        </div>
    )
}

export default MyPosts;