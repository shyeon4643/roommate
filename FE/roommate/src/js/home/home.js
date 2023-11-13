import { SearchOutlined } from '@ant-design/icons';
import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../../css/home.css";

function Home(){
    const[mbtiPosts, setMbtiPosts] = useState("");
    const[keyword, setKeyword] = useState("");
    const[postData, setPostData] = useState([]);
    const navigate = useNavigate();
    useEffect(() => {
        try {
            axios({
                method: "GET",
                url: `/mbtiPosts`,
                headers: {
                    'JWT': localStorage.getItem('JWT'),
                },
            }).then((response) => {
                console.log(response.data.data);
                setMbtiPosts(response.data.data);
            });
        } catch (error) {
            console.log("게시글 불러오는 중에 오류 : ", error);
        }
    }, []);

    const handleSearch = () =>{
        try{
            const data ={
                keyword : keyword,
            }
            axios({
                headers: {
                    'JWT': localStorage.getItem('JWT'),
                },
                method:"POST",
                url : "/searchPosts",
                data : data,
            }).then((response) =>{
                console.log(response.data);
                setPostData(response.data.data);
                navigate('/searchPosts', {
                    state: { postData: response.data.data, keyword: keyword },
                });
            });
        }catch(error){
            console.log("검색 중 에러 발생",error);
        }
    }
    return (
        <div className="home_container">
            <div className="home">
            <div className="search">
                <h2>어느 지역을 찾으세요?</h2>
                <div className="home_search">
                <input
                    type="text"
                    id="search_input"
                    value={keyword}
                    onChange={(e) => setKeyword(e.target.value)}
                />
                <SearchOutlined  className="home_search_icon" style={{ fontSize: '35px', color: 'black' }} onClick={handleSearch} />
                </div>
            </div>
            </div>
            <div className="post">
                <div className="mbti_posts">
                    {mbtiPosts && mbtiPosts.map((response)=>(
                    <li>
                        <div className="mbti_post">
                            {response.image &&(
                                <img src={image} alt="게시물 이미지" className="post_image"/>
                                )}
                            <p>{response.title}</p>
                            <p>{response.writer}</p>
                        </div>
                    </li>
                    ))}
                </div>
            </div>
        </div>
    )

}

export default Home;