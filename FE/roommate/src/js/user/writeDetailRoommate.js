import React from "react";
import "../../css/writeDetailRoommate.css";

function WriteDetailRoommate(){
    return(
        <div className="detailRoommate_container">
            <div className="detailRoommate_body">
                <h1 className="detailRoommate_title">원하는 룸메이트</h1>
                <table className="detailRoommate_table">
                    <tr>
                        <td className="detailRoommate_field_name">생활패턴</td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value={"야행성"} name="lifeCycle"
                           className="detailRoommate_input_radio"/> 야행성
                        </td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value={"주행성"} name="lifeCycle"
                            className="detailRoommate_input_radio"/> 주행성
                        </td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value={"상관없어요"} name="lifeCycle"
                            className="detailRoommate_input_radio"/> 상관없어요
                        </td>
                    </tr>
                    <tr>
                        <td className="detailRoommate_field_name">반려동물</td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value={"좋아요"} name="pet"
                            
                            className="detailRoommate_input_radio"/> 좋아요
                        </td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value={"싫어요"} name="pet"
                            className="detailRoommate_input_radio"/> 싫어요
                        </td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value={"상관없어요"} name="pet"
                            className="detailRoommate_input_radio"/> 상관없어요
                        </td>
                    </tr>
                    <tr>
                        <td className="detailRoommate_field_name">담배</td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value={"좋아요"} name="smoking"
                            className="detailRoommate_input_radio"/> 좋아요
                        </td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value={"싫어요"} name="smoking"
                            className="detailRoommate_input_radio"/> 싫어요
                        </td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value={"상관없어요"} name="smoking"
                            className="detailRoommate_input_radio"/> 상관없어요
                        </td>
                    </tr>
                    <tr>
                        <td className="detailRoommate_field_name">성별</td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value={"여"} name="gender"
                            className="detailRoommate_input_radio"/> 여자
                        </td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value={"남"} name="gender"
                            className="detailRoommate_input_radio"/> 남자
                        </td>
                        <td className="detailRoommate_input_radio">
                            <input type="radio" value={"상관없어요"} name="gender"
                            className="detailRoommate_input_radio"/> 상관없어요
                        </td>
                    </tr>
                    <tr>
                        <td className="detailRoommate_field_name">원하는 룸메이트 상세설명</td>
                        <td colspan='3'>
                            <input type="text"
                            className="detailRoommate_input_text"/>
                        </td>
                    </tr>
                    <tr>
                        <td className="detailRoommate_field_name">금액</td>
                        <td colspan='3'>
                            <input type="text"
                            className="detailRoommate_input_fee"/>
                        </td>
                    </tr>
                </table>
                <button type="submit" className="writeDetailRoommate_button">
            저장하기
          </button>
            </div>
        </div>
    )
}

export default WriteDetailRoommate;