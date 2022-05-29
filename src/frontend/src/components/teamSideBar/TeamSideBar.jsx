import React from 'react';
import TeamItem from '../teamItem/TeamItem';
import "./TeamSideBar.scss"
import { useState, useEffect } from 'react';
import TeamModal from '../teamModal/TeamModal';
import image from "../../images/picture/team.png";
import AppUserService from "../../services/appUserService";
import TaskService from '../../services/tasksService';

function TeamSideBar(props) {
    const [teams, setTeam] = useState([]);
    const [modalState, setModalState] = useState(false);
    const [modalFunction, setModalFunction] = useState("Add");
    const [selectedTeam, setSelectedTeam] = useState(undefined);
    const [privateTasks, setPrivateTasks] = useState([]);
    const [doneTasks, setDoneTasks] = useState([]);

    useEffect(() => {
        AppUserService.getTeams().then(res => {
            setTeam(res);
            if(res && res.length > 0){
                props.setTeamId(res[0].id);
                props.setTitle(res[0].name)
            }
        });
        TaskService.getPrivateTasks().then(res => {
            setPrivateTasks(res);
        });
        TaskService.getUserDoneTasks().then(res => {
            setDoneTasks(res);
        })
    }, [props.updateTeam])

    const handleAddTeamButton = () => {
        setModalFunction("Add");
        setSelectedTeam(null);
        setModalState(true);
    }

    const updateTeam = () => {
        window.location.reload();
    }

    const handlePrivateBtnClick = () => {
        console.log("Private Click");
        props.setTitle("Private");
        props.setTeamId(0);
    }

    const btn = localStorage.getItem("accessToken") && JSON.parse(localStorage.getItem("accessToken")).role !== "[USER]"?  
    <>
        <div className="teamSideBar__body-addBtn" onClick={handleAddTeamButton}>Add Team</div>
        <div className="teamSideBar__body-line"></div>
    </> : null;
    
    let numberDoneTasks = 0;
    if(privateTasks && doneTasks){
        numberDoneTasks = privateTasks.length;
        privateTasks.forEach((task) => {
            doneTasks.forEach((done) => {
                if(task.id === done.id){
                    numberDoneTasks--;
                }
            });
        });
    }

    return (
        <div className='teamSideBar'>
            <div className="teamSideBar__header">
                <div className="teamSideBar__header-btn">
                    ---
                </div>
                <PrivateItem title="Private" path="M80 192V144C80 64.47 144.5 0 224 0C303.5 0 368 64.47 368 144V192H384C419.3 192 448 220.7 448 256V448C448 483.3 419.3 512 384 512H64C28.65 512 0 483.3 0 448V256C0 220.7 28.65 192 64 192H80zM144 192H304V144C304 99.82 268.2 64 224 64C179.8 64 144 99.82 144 144V192z" count={numberDoneTasks} handlePrivateBtnClick={handlePrivateBtnClick}/>
            </div>
            <div className="teamSideBar__body">
               {btn}
                <div className="teamSideBar__wrapper">
                    {
                        teams ? teams.map((item) => {
                            return (
                                <TeamItem key={item.id} id={item.id} title={item.name} image={image} setTasks={props.setTasks} setTitle={props.setTitle} setTeamId={props.setTeamId} reload={props.reload} setReload={props.setReload} setModalState={setModalState} setModalFunction={setModalFunction} setSelectedTeam={setSelectedTeam}/>
                            )
                        }) : null
                    }
                </div>
                <TeamModal
                    function={modalFunction}
                    modalState={modalState}
                    setModalState={setModalState}
                    updateTeam={updateTeam}
                    setTeam={setTeam}
                    teamId={selectedTeam}
                />
            </div>
        </div>
    );
}

const PrivateItem = (props) => {
    return(
        <div className="privateItem" onClick={props.handlePrivateBtnClick}>
            <div className="privateItem__block">
                <div className="privateItem__image">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d={props.path}/></svg>
                    <span>{props.iconText}</span>
                </div>
                <div className="privateItem__title">
                    {props.title}
                </div>
            </div>
            <div className="privateItem__count">
                {props.count}
            </div>
        </div>
    )
}
export default TeamSideBar;