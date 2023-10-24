import React, { useState, useEffect } from "react";
import { useLocation} from "react-router-dom";
import axios from "axios";
import "../../css/writeDetailRoommate.css";

function DetailRoommate(){
    const [area, setArea] = useState("");
    const [fee, setFee] = useState("");
    const [category, setCategory] = useState("");
    const [lifeCycle, setLifeCycle] = useState("");
    const [pet, setPet] = useState("");
    const [smoking, setSmoking] = useState("");
    const [gender, setGender] = useState("");
    const [wishRoommate, setWishRoommate] = useState("");
    const [isUpdate, setIsUpdate] = useState(false);
    const location = useLocation();
    const data = location.state;
    useEffect(() => {
        const updateBtn = document.querySelector("#updateDetailRoommate_button");
        const wrtieBtn = document.querySelector("#writeDetailRoommate_button");
        if (data) {
            setArea(data.area);
            setGender(data.gender);
            setCategory(data.category);
            setPet(data.pet);
            setSmoking(data.smoking);
            setFee(data.fee);
            setWishRoommate(data.wishRoommate);
            setLifeCycle(data.lifeCycle);
            wrtieBtn.style.display = "none";
            updateBtn.style.display = "block";
        } else {
            wrtieBtn.style.display = "block";
            updateBtn.style.display = "none";
        }
    }, [data]);

    const handleWriteDetailRoommate = async(e) =>{
        e.preventDefault();
        try{
            const data = {
                area,
                category,
                lifeCycle,
                pet,
                smoking,
                gender,
                wishRoommate,
                fee
            };
            axios({
                headers: {
                    'Content-Type': 'application/json',
                    'JWT': localStorage.getItem('JWT'),
                },
                method : "POST",
                url : "/writeDetailRoommate",
                data : data,
            }).then((response) =>{
                console.log(response.data.data);
                window.location.href = `/mypage`;
            })
        }catch(error){
            console.log("글 등록 중 에러 발생  : ", error);
        }
    }

    const handleUpdateDetailRoommate = async() =>{
        try{
            const data = {
                area,
                category,
                lifeCycle,
                pet,
                smoking,
                gender,
                wishRoommate,
                fee
            };
            axios({
                headers: {
                    'Content-Type': 'application/json',
                    'JWT': localStorage.getItem('JWT'),
                },
                method : "PUT",
                url : "/updateDetailRoommate",
                data : data,
            }).then((response) =>{
                console.log(response.data.data);
                window.location.href = `/mypage`;
            })
        }catch(error){
            console.log("글 등록 중 에러 발생  : ", error);
        }
    }

    return(
        <div className="detailRoommate_container">
            <div className="detailRoommate_body">
                <h1 className="detailRoommate_title">원하는 룸메이트</h1>
                <form>
                <table className="detailRoommate_table">
                    <tr>
                        <td className="detailRoommate_field_name">전세&월세</td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value="charter" name="category"
                                   className="detailRoommate_input_radio"
                                   onChange={(e) => setCategory(e.target.value)}
                                   checked={category === "charter"}/> 전세
                        </td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value="monthly" name="category"
                                   className="detailRoommate_input_radio"
                                   onChange={(e) => setCategory(e.target.value)}
                                   checked={category === "monthly"}/> 월세
                        </td>
                    </tr>
                    <tr>
                        <td className="detailRoommate_field_name">지역</td>
                        <td className="detailRoommate_input_radio">
                            <select
                                id="writeDetailRoommate_input_category"
                                name="writeDetailRoommate_category"
                                className="writeDetailRoommate_input"
                                value={area}
                                onChange={(e) => setArea(e.target.value)}
                            >
                                <option value="">지역을 선택하세요</option>
                                <option value="강남구" >강남구</option>
                                <option value="강북구" >강북구</option>
                                <option value="강서구" >강서구</option>
                                <option value="관악구" >관악구</option>
                                <option value="구로구" >구로구</option>
                                <option value="금천구" >금천구</option>
                                <option value="노원구" >노원구</option>
                                <option value="도봉구" >도봉구</option>
                                <option value="동대문구" >동대문구</option>
                                <option value="동작구" >동작구</option>
                                <option value="마포구" >마포구</option>
                                <option value="서대문구" >서대문구</option>
                                <option value="서초구" >서초구</option>
                                <option value="성동구" >성동구</option>
                                <option value="송파구" >송파구</option>
                                <option value="양천구" >양천구</option>
                                <option value="영등포구" >영등포구</option>
                                <option value="용산구" >용산구</option>
                                <option value="온평구" >온평구</option>
                                <option value="종로구" >중구</option>
                                <option value="중랑구" >중랑구</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td className="detailRoommate_field_name">생활패턴</td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value="야행성" name="lifeCycle"
                           className="detailRoommate_input_radio"
                           onChange={(e) => setLifeCycle(e.target.value)}
                           checked={lifeCycle === "야행성"}/> 야행성
                        </td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value="주행성" name="lifeCycle"
                            className="detailRoommate_input_radio"
                            onChange={(e) => setLifeCycle(e.target.value)}
                            checked={lifeCycle === "주행성"}/> 주행성
                        </td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value="상관없어요" name="lifeCycle"
                            className="detailRoommate_input_radio"
                            onChange={(e) => setLifeCycle(e.target.value)}
                            checked={lifeCycle === "상관없어요"}/> 상관없어요
                        </td>
                    </tr>
                    <tr>
                        <td className="detailRoommate_field_name">반려동물</td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value="좋아요" name="pet"
                            className="detailRoommate_input_radio"
                            onChange={(e) => setPet(e.target.value)}
                            checked={pet === "좋아요"}/> 좋아요
                        </td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value="싫어요" name="pet"
                            className="detailRoommate_input_radio"
                            onChange={(e) => setPet(e.target.value)}
                            checked={pet === "싫어요"}/> 싫어요
                        </td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value="상관없어요" name="pet"
                            className="detailRoommate_input_radio"
                            onChange={(e) => setPet(e.target.value)}
                            checked={pet === "상관없어요"}/> 상관없어요
                        </td>
                    </tr>
                    <tr>
                        <td className="detailRoommate_field_name">담배</td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value="좋아요" name="smoking"
                            className="detailRoommate_input_radio"
                            onChange={(e) => setSmoking(e.target.value)}
                            checked={smoking === "좋아요"}/> 좋아요
                        </td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value="싫어요" name="smoking"
                            className="detailRoommate_input_radio"
                            onChange={(e) => setSmoking(e.target.value)}
                            checked={smoking === "싫어요"}/> 싫어요
                        </td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value="상관없어요" name="smoking"
                            className="detailRoommate_input_radio"
                            onChange={(e) => setSmoking(e.target.value)}
                            checked={smoking === "상관없어요"}/> 상관없어요
                        </td>
                    </tr>
                    <tr>
                        <td className="detailRoommate_field_name">성별</td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value="여" name="gender"
                            className="detailRoommate_input_radio"
                            onChange={(e) => setGender(e.target.value)}
                            checked={gender === "여"}/> 여자
                        </td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value="남" name="gender"
                            className="detailRoommate_input_radio"
                            onChange={(e) => setGender(e.target.value)}
                            checked={gender === "남"}/> 남자
                        </td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value="상관없어요" name="gender"
                            className="detailRoommate_input_radio"
                            onChange={(e) => setGender(e.target.value)}
                            checked={gender === "상관없어요"}/> 상관없어요
                        </td>
                    </tr>
                    <tr>
                        <td className="detailRoommate_field_name">원하는 룸메이트 상세설명</td>
                        <td colspan='3'>
                            <input type="text"
                            className="detailRoommate_input_text"
                            onChange={(e) => setWishRoommate(e.target.value)}/>
                        </td>
                    </tr>
                    <tr>
                        <td className="detailRoommate_field_name">금액</td>
                        <td colspan='3'>
                            <input type="text"
                            className="detailRoommate_input_fee"
                            onChange={(e) => setFee(e.target.value)}/>
                        </td>
                    </tr>
                </table>
                <button type="button"
                        id="writeDetailRoommate_button"
                        className="detailRoommate_button"
                onClick={handleWriteDetailRoommate}>
            저장하기
          </button>
                    <button type="button"
                            id="updateDetailRoommate_button"
                            className="detailRoommate_button"
                            style={{ display: "none" }}
                            onClick={handleUpdateDetailRoommate}>
                        수정하기
                    </button>
            </form>
            </div>
        </div>
    )
}

export default DetailRoommate;