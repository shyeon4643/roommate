import axios from "axios";
import React, { useEffect, useState } from "react";
import "../../css/board.css";
import BoardTable from "./boardTable";

function MyPost(){
    const [postData, setPostData] = useState();

    useEffect(()=>{
        try{
            axios({
                headers: {
                    'JWT': localStorage.getItem('JWT'),
                },
                method: "GET",
                url: `/user/posts`,
            }).then((response) =>{
                console.log(response.data.data);
                window.location.href = `/post/${response.data.data.category}/${response.data.data.postId}`;
            });
        }catch(error){
            console.error("게시물 데이터를 가져오는 중 에러가 발생했습니다.", error);
        }
    },[])
    return(
        <div>
            <h1 className="categoryName">내가 쓴 게시글</h1>
            <BoardTable postData={postData} />
        </div>
    )
}

export default MyPost;