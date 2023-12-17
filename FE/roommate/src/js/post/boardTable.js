import React from "react";
import { Link } from "react-router-dom";
import "../../css/boardTable.css";
import { FaHeart } from 'react-icons/fa';

function BoardTable({postData}){
    const posts = postData || [];

    const movepage = (response) => {
        window.location.href = `/posts/${response.category}/${response.postId}`;
    };
    return(
        <div>
            <div className="board_table_container">
                <ul className="board_wrapper">
                    {postData && postData.map((response) => (
                        <div className="board_table" key={response.postId} onClick={() => movepage(response)}>
                            <div className="board_table_img">
                                <img src={`/static/photos/postPhoto/${response.path}`} alt="게시물 이미지" className="post_image"/>
                            </div>
                            <div className="board_table_inform">
                            <li id="board_title">{response.title}</li>
                            <li id="board_body">{response.body}</li>
                            <li id="board_write">{response.writer==null?"알수없음" : response.writer}</li>
                            <li id="board_likeCount">
                                 조회수 : {response.viewCount}
                                 <FaHeart id="heart_icon"/>
                                {response.likeCount}
                                </li>
                            </div>
                        </div>
                    ))}
                    <div className="board_table_footer">
            <Link to="/writePost" className="board_write_button">
                    글쓰기
                </Link>
            <div className="board_table_button">
            <button className="styled_button previous_button">
                Previous
            </button>
            <button className="styled_button next_button">
                Next
            </button>
            </div>
            </div>
                </ul>
            
            </div>
        
        </div>
    );
}

export default BoardTable;