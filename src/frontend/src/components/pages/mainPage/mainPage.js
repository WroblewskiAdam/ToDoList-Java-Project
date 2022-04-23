import { useEffect, useState } from "react";
import SideBar from "../../sideBar/SideBar";
import TeamSideBar from "../../teamSideBar/TeamSideBar"
import "./mainPage.scss"
import AuthService from "../../../services/authService";
import { useHistory } from "react-router-dom";

const MainPage = () => {
    const [user, setUser] = useState(null);

    let history = useHistory();
    
    useEffect(() =>{
        const user = AuthService.getCurrentUser();
        if(user){
            setUser(user);
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
