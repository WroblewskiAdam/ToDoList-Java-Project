import React, { useEffect, useState } from 'react';
import AppUserService from '../../services/appUserService';
import "./UserInfo.scss"
import basicBg from "../../images/backgrounds/Checklist.jpg"
import UserModal from '../userModal/UserModal';
import RoleEditModal from '../roleEditModal/RoleEditModal';

const UserInfo = (props) => {
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [image, setImage] = useState(undefined);
    const [imageFile, setImageFile] = useState(undefined);
    const [password, setPassword] = useState("");
    const [modalState, setModalState] = useState(false);
    const [updateUserInfo, setUpdateUserInfo] = useState(false);
    const [roleModalState, setRoleModalState] = useState(false);

    useEffect(() => {
        AppUserService.getUser().then((res) => {
            setFirstName(res.data.firstName);
            setLastName(res.data.lastName);
            setEmail(res.data.email);
            setImage(res.data.image.image);
            setPassword(res.data.password);
            setImageFile(res.data.image);
        });
    }, [updateUserInfo]);

    const handleEditClick = () => {
        setModalState(true);
    }

    const handleRoleEdit = () => {
        setRoleModalState(true);
    }


    const roleEditor = localStorage.getItem("accessToken") && JSON.parse(localStorage.getItem("accessToken")).role  === "[ADMIN]"?
        <div className="userInfo__role" onClick={handleRoleEdit}>
            Change Users Roles
        </div> : null;
    
    const userImage = image && image.length > 6?  `data:image/png;base64,`+image: basicBg;

    return (
        <div className="userInfo">
            <div className="userInfo__title">
                User Info
            </div>
                {/* <div className="userInfo__block">
                    <img src={basicBg} alt="userBG" className="userInfo__block-image" />
                </div> */}
            <div className="userInfo__block">
                {roleEditor}
                <div className="userInfo__block-info">
                    <img src={userImage} alt="userImg" />
                    <div className="userInfo__block-info-text">
                        <div className="userInfo__block-text"><span>First name:</span> {firstName}</div>
                        <div className="userInfo__block-text"><span>Last name:</span> {lastName}</div>
                        <div className="userInfo__block-text"><span>Email:</span> {email}</div>
                    </div>
                </div>
                {/* <div className="userInfo__block-text"><span>First name:</span> {firstName}</div>
                <div className="userInfo__block-text"><span>Last name:</span> {lastName}</div>
                <div className="userInfo__block-text"><span>Email:</span> {email}</div> */}
                <div className="userInfo__edit" onClick={handleEditClick}>
                    Edit
                </div>
           </div>
           <UserModal
                modalState={modalState}
                setModalState={setModalState}
                firstName={firstName}
                lastName={lastName}
                email={email}
                imageFile={imageFile}
                password={password}
                setUpdateUserInfo={setUpdateUserInfo}
           />
           <RoleEditModal
                modalState={roleModalState}
                setModalState={setRoleModalState}
            />
        </div>
    );
}

export default UserInfo;