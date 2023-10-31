import axios from "axios";
import React, { useEffect, useState } from "react";
import "../../css/board.css";
import BoardTable from "./boardTable";

function BoardLikedPosts(){
    const [postData, setPostData] = useState();

    useEffect(()=>{
        try{
            axios({
                headers: {
                    'JWT': localStorage.getItem("JWT"),
                },
                method : "GET",
                url : `/user/posts/likes`,
            }).then((response) => {
                console.log(response.data.data);
                setPostData(response.data.data);
            })
        }catch(error){
            console.log("게시글 불러오는 중 에러 발생  : ", error);
        }
    },[])
    return(
        <div>
            <h1 className="categoryName">좋아요한 게시글</h1>
            <BoardTable postData={postData} />
        </div>
    )
}

export default BoardLikedPosts;