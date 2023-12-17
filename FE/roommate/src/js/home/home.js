import { SearchOutlined } from '@ant-design/icons';
import axios from "axios";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import "../../css/home.css";

function Home(){
    const[homePosts, setHomePosts] = useState([]);
    const[keyword, setKeyword] = useState("");
    const[postData, setPostData] = useState([]);
    const [page, setPage] = useState(0);
    const navigate = useNavigate();
    const size = 5;


    useEffect(() => {
        try {
            axios({
                method: "GET",
                url: `/home/posts/popular?page=${page}&size=${size}`,
            }).then((response) => {
                console.log(response.data.data);
                setHomePosts(response.data.data.data);
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
                method:"GET",
                url : "/search",
                params : data,
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

    const getPopularPosts = () =>{
        try{
            axios({
                method: "GET",
                url: `/home/posts/popular?page=${page}&size=${size}`,
                headers: {
                    'JWT': localStorage.getItem('JWT'),
                },
            }).then((response) => {
                console.log(response.data.data);
                setHomePosts(response.data.data.data);
            });
        } catch (error) {
            console.log("게시글 불러오는 중에 오류 : ", error);
        }
    }

    const getMbtiPosts = () =>{
        try {
            const jwtToken = localStorage.getItem('JWT');
            if (!jwtToken) {
                alert("로그인이 필요합니다.");
                window.location.href = "/login";
            }
            axios({
                method: "GET",
                url: `/home/posts/mbti?page=${page}&size=${size}`,
                headers: {
                    'JWT': localStorage.getItem('JWT'),
                },
            }).then((response) => {
                console.log(response.data.data);
                setHomePosts(response.data.data.data);
            });
        } catch (error) {
            console.log("게시글 불러오는 중에 오류 : ", error);
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
                <div className="home_posts">
                <div className='home_posts_sort'>
                <button type= "button"
                    className="home_post_button"
                            onClick={getPopularPosts}>
                        <p className="home_button_title">인기 게시글</p>
                    </button>
                <button type= "button"
                    className="home_post_button"
                            onClick={getMbtiPosts}>
                        <p className="home_button_title">|   MBTI 게시글</p>
                    </button>
                    </div>
                    <div className='home_posts_list'>
                    {homePosts && homePosts.map((response, index)=>(
                    <li key={index}>
                        <div className="home_post">
                            {response.path && response.path.map((path, pathIndex)=>(
                                <img key={pathIndex} src={`/static/photos/postPhoto/${path}`} alt="게시물 이미지" className="post_image"/>
                                ))}
            
                            <p className='home_posts_title'>{response.title}</p>
                            <p className='home_posts_writer'>{response.writer}</p>
                        </div>
                    </li>
                    ))}
                </div>
                </div>
            </div>
        </div>
    )

}

export default Home;