import { Route, Routes } from "react-router-dom";
import './App.css';
import Home from "./js/home/home";
import Join from "./js/home/join";
import Login from "./js/home/login";
import Footer from "./js/layout/footer";
import Header from "./js/layout/header";
import Sidebar from "./js/layout/sidebar";
import BoardCharter from "./js/post/boardCharter";
import BoardMonthly from "./js/post/boardMonthly";
import BoardTable from "./js/post/boardTable";
import Comment from "./js/post/comment";
import DetailPost from "./js/post/detailPost";
import MyComments from "./js/post/myComments";
import MyPosts from "./js/post/myPosts";
import WritePost from "./js/post/writePost";
import MyPage from "./js/user/mypage";
import DetailRoommate from "./js/user/detailRoommate";

function App() {
  return (
    <div className="wrapper">
      <Header />
      <div className="contentWrapper">
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/boardTable" element={<BoardTable />} />
        <Route path="/comment" element={<Comment />} />
        <Route path="/detailPost" element={<DetailPost />} />
        <Route path="/mypage" element={<MyPage />} />
        <Route path="/login" element={<Login />} />
        <Route path="/join" element={<Join />} />
        <Route path="/updateDetailRoommate" element={<DetailRoommate />} />
        <Route path="/writeDetailRoommate" element={<DetailRoommate />} />
        <Route path="/writePost" element={<WritePost />} />
        <Route path="/updatePost" element={<WritePost />} />
        <Route path="/monthly/posts" element={<BoardMonthly />} />
        <Route path="/charter/posts" element={<BoardCharter />} />
        <Route path="/user/posts" element={<MyPosts />}/>
        <Route path="/post/:category/:postId" element={<DetailPost />} />
        <Route path="/user/comments" element={<MyComments />} />
        <Route path="/sidebar" element={<Sidebar />} />
          </Routes>
        </div>
        <Footer />
      </div>
  );
}

export default App;
