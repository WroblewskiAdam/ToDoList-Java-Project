import { useEffect, useState } from "react";
import SideBar from "../../components/sideBar/SideBar";
import TeamSideBar from "../../components/teamSideBar/TeamSideBar"
import "./mainPage.scss"
import AuthService from "../../services/authService";
import { useHistory } from "react-router-dom";
import AppUserService from "../../services/appUserService";
import TaskSection from "../../components/taskSection/TaskSection";
import TeamService from "../../services/teamService";

const MainPage = () => {
    const [, setUserData] = useState(null);
    const [tasks, setTasks] = useState(null);
    const [title, setTitle] = useState("Choose Team");
    const [teamId, setTeamId] = useState(null);

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
            <TeamSideBar setTitle={setTitle} setTasks={setTasks} setTeamId={setTeamId}/>
            <TaskSection title={title} tasks={tasks} teamId={teamId} setTasks={setTasks}/>
        </div>
    )
}

export default MainPage;
