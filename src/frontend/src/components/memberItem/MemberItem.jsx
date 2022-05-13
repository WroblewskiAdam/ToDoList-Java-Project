import React, { useState, useEffect } from 'react';
import icon from "../../images/icons/user-solid.svg"
import "./MemberItem.scss";
import AppUserService from '../../services/appUserService';
import TeamService from '../../services/teamService';
import { CircularProgressbar } from 'react-circular-progressbar';
import 'react-circular-progressbar/dist/styles.css';

function MemberItem(props) {
    const [allTasks, setAllTasks] = useState([]);
    const [allTeamTasks, setAllTeamTasks] = useState([]);
    const [tasks, setTasks] = useState([]);
    const [doneTasks, setDoneTasks] = useState([]);

    useEffect(() => {
        AppUserService.getTasks(props.id).then((res) => {
            setAllTasks(res);
        });

        TeamService.getTeamTasks(props.teamId).then((res) => {
            setAllTeamTasks(res);
        });
    }, [props.id, props.teamId]);

    if(allTasks && allTeamTasks){
        let teamTasksTitle = allTeamTasks.map((item) => item.title);

        allTasks.forEach((task) => {
            console.log(task.title);
            if(teamTasksTitle.indexOf(task.title) > -1){
                if(task.isDone){
                    setDoneTasks((doneTasks) => [...doneTasks, task]);
                }
                setTasks((tasks) => [...tasks, task]);
            }
        });
    }

    let lenDoneTasks= doneTasks.length;
    let lenTasks = tasks.length;
    
    let percentage = Math.round(lenDoneTasks/lenTasks) * 100;
    
    return (
        <div className="memberItem">
            <div className="memberItem__image">
                <img src={icon} alt="icon" />
            </div>
            <div className="memberItem__info">
                <div className="memberItem__info-item">
                    {props.firstName}
                </div>
                <div className="memberItem__info-item">
                    {props.lastName}
                </div>
            </div>
            <div className="memberItem__percentage">
                <CircularProgressbar 
                    value={percentage}
                    text={`${percentage}%`}
                    styles={{
                        path: {
                            stroke: `rgba(0, 152, 199, ${percentage / 100})`,
                        },
                        trail: {
                            stroke: '#d6d6d6',
                        },
                }}
                />
            </div>
        </div>
    );
}

export default MemberItem;