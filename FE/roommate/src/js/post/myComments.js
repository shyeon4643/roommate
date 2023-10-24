import axios from "axios";
import React, { useEffect, useState } from "react";
import "../../css/board.css";
import CommentTable from "./commentTable";

function MyComments(){
    const [commentData, setCommentData] = useState();

    useEffect(()=>{
        try{
            axios({
                headers: {
                    'JWT': localStorage.getItem('JWT'),
                },
                method: "GET",
                url: `/user/comments`,
            }).then((response) =>{
                setCommentData(response.data.data);
            });
        }catch(error){
            console.error("게시물 데이터를 가져오는 중 에러가 발생했습니다.", error);
        }
    },[])
    return(
        <div>
            <h1 className="categoryName">내가 쓴 댓글</h1>
            <CommentTable commentData={commentData} />
        </div>
    )
}

export default MyComments;