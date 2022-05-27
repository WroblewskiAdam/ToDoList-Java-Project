import { useEffect } from "react";
import { useHistory } from "react-router-dom";
import SideBar from '../../components/sideBar/SideBar';
import UserInfo from '../../components/userInfo/UserInfo';
import AuthService from "../../services/authService";
import './UserPage.scss';

const UserPage = (props) => {
    let history = useHistory();
    
    useEffect(() =>{
        const user = AuthService.getCurrentUser();
        if(!user){
            history.push("/");
        }
    }, []);

    return (
        <div className="userPage">
            <SideBar/>
            <UserInfo/>
        </div>
    );
}

export default UserPage;