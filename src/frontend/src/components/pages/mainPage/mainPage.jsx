import { useEffect, useState } from "react";
import SideBar from "../../sideBar/SideBar";
import TeamSideBar from "../../teamSideBar/TeamSideBar"
import "./mainPage.scss"
import AuthService from "../../../services/authService";
import { useHistory } from "react-router-dom";
import AppUserService from "../../../services/appUserService";

const MainPage = () => {
    const [userData, setUserData] = useState(null);

    let history = useHistory();
    
    useEffect(() =>{
        const user = AuthService.getCurrentUser();
        if(user){
            const data = AppUserService.getUser();
            setUserData(data);
        }
        else{
            history.push("/");
        }
    }, [])

    return (
        <div className="mainPage">
            <SideBar/>
            <TeamSideBar/>
        </div>
    )
}

export default MainPage;
