import React from "react";
import { Link } from "react-router-dom";
import "../../css/boardTable.css";

function BoardTable({postData}){
    const movepage = (response) => {
        window.location.href = `/post/${response.category}/${response.postId}`;
    };
    return(
        <div>
            <div className="board-table-container">
                <table className="board-table">
                    <thead>
                    <tr>
                        <th>번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>날짜</th>
                        <th>조회수</th>
                    </tr>
                    </thead>
                    <tbody className="board-table-body">
                    {postData&&postData.map((response, index) => (
                        <tr key={response.postId} onClick={() => movepage(response)}>
                            <td>{index + 1}</td>
                            <td>{response.title}</td>
                            <td>{response.writer==null?"알수없음" : response.writer}</td>
                            <td>{response.updateAt}</td>
                            <td>{response.viewCount}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                <Link to="/writePost" className="board-write-button">
                    글쓰기
                </Link>
            </div>
        </div>
    )
}

export default BoardTable;