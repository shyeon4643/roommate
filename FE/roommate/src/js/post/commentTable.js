import React from "react";
import { Link } from "react-router-dom";

function CommentTable({ commentData }) {

    if (commentData === null || commentData === undefined) {
        return <div>적상한 글이 없습니다.</div>;
    }
    if(!Array.isArray(commentData)){
        commentData=[];
    }
    const movePage = (response) => {
        window.location.href = `/post/${response.category}/${response.postId}`;
    };
    return (
        <div>
            <div className="board-table-container">
                <table className="board-table">
                    <thead>
                    <tr>
                        <th>번호</th>
                        <th>내용</th>
                        <th>날짜</th>
                    </tr>
                    </thead>
                    <tbody className="board-table-body">
                    {commentData &&
                        commentData.map((response, index) => (
                            <tr key={response.id} onClick={() => movePage(response)}>
                                <td>{index + 1}</td>
                                <td>{response.body}</td>
                                <td>{response.updateAt[0]}년 {response.updateAt[1]}월 {response.updateAt[2]}일</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
        </div>
    );
}

export default CommentTable;