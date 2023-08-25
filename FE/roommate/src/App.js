import { Route, Routes } from "react-router-dom";
import './App.css';
import Home from "./js/home/home";
import Join from "./js/home/join";
import Login from "./js/home/login";
import Footer from "./js/layout/footer";
import Header from "./js/layout/header";
import BoardTable from "./js/post/boardTable";
import Comment from "./js/post/comment";
import DetailPost from "./js/post/detailPost";
import MyPage from "./js/user/mypage";

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
      </Routes>
        </div>
        <Footer />
      </div>
  );
}

export default App;
