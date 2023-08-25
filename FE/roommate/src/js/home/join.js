import React from "react";
import "../../css/join.css";

const handleJoin=()=>{

}

function Join(){
    return (
        <div className="join_container">
            <div className="join_body">
            <h1 className="join_title">회원가입</h1>
                    <table className="join_table">
                        <tr>
                                <td className="join_field_name">이름</td>
                                <td>
                                    <input type="text"
                                className="join_input"
                            placeholder="이름"/>
                            </td>
                            </tr>
                            <tr>
                                <td className="join_field_name">아이디</td>
                                <td>
                                    <input type="text"
                                className="join_input"
                            placeholder="아이디"/>
                            </td>
                            </tr>
                            <tr>
                                <td className="join_field_name">비밀번호</td>
                                <td>
                                    <input type="text"
                                className="join_input"
                            placeholder="비밀번호"/>
                            </td>
                            </tr>
                            <tr>
                                <td className="join_field_name">생일</td>
                                <td>
                                    <input type="date"
                                className="join_input"
                            placeholder="생일"/>
                            </td>
                            </tr>
                            <tr>
                                <td className="join_field_name">이메일</td>
                                <td>
                                    <input type="text"
                                className="join_input"
                            placeholder="이메일"/>
                            </td>
                            </tr>
                            <tr>
                                <td className="join_field_name">닉네임</td>
                                <td>
                                    <input type="text"
                                className="join_input"
                            placeholder="닉네임"/>
                            </td>
                            </tr>
                            <tr>
                                <td className="join_field_name">전화번호</td>
                                <td>
                                    <input type="text"
                                className="join_input"
                            placeholder="전화번호"/>
                            </td>
                            </tr>
                    </table>
                    <button
                    className="join-submit-button"
                    onClick={handleJoin}
                >
                    회원가입
                </button>
                </div>

            </div>
    )
}

export default Join;