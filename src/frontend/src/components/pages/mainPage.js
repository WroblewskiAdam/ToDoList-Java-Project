import SideBar from "../sideBar/SideBar";
import TeamSideBar from "../teamSideBar/TeamSideBar"
import "./mainPage.scss"

const MainPage = () => {
    return (
        <div className="mainPage">
            <SideBar/>
            <TeamSideBar/>
        </div>
    )
}

export default MainPage;
