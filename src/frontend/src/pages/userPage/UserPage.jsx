import React from 'react';
import SideBar from '../../components/sideBar/SideBar';
import UserInfo from '../../components/userInfo/UserInfo';
import './UserPage.scss';

const UserPage = (props) => {
    return (
        <div className="userPage">
            <SideBar/>
            <UserInfo/>
        </div>
    );
}

export default UserPage;