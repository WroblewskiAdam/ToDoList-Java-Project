import React, { useState, useEffect } from 'react';
import icon from "../../images/icons/user-solid.svg"
import "./MemberItem.scss";
import AppUserService from '../../services/appUserService';
import TeamService from '../../services/teamService';
import { CircularProgressbar } from 'react-circular-progressbar';
import 'react-circular-progressbar/dist/styles.css';
import TaskService from '../../services/tasksService';

function MemberItem(props) {
    const [, setAllTasks] = useState([]);
    const [, setAllTeamTasks] = useState([]);
    const [tasks, setTasks] = useState([]);
    const [doneTasks, setDoneTasks] = useState([]);
    const [imageData, setImageData] = useState(undefined);

    useEffect(() => {
        TaskService.getTeamReceivedTasks(props.teamId, props.id).then((res) => {
            setTasks(res);
            // console.log("T", res);
        });

        TaskService.getTeamDoneTasks(props.teamId, props.id).then((res) => {
            setDoneTasks(res);
            // console.log("Done: ", res);
        });

        
        
    }, [props.teamId, props.updateProgres]);


    useEffect(() => {
        console.log(1);
        AppUserService.getImage(props.id).then((res) => {
            
            setImageData(res.data)
        });
    }, []);

    let percentage = 0;
    if(tasks.length){
        if(doneTasks.length){
            let lenDoneTasks= doneTasks.length;
            let lenTasks = tasks.length;

            percentage = Math.round(lenDoneTasks/(lenTasks+lenDoneTasks)*100);
        }
    }
    else{
        percentage = 100;
    }

    const memberImage = imageData && imageData.length > 6?  `data:image/png;base64,`+imageData: icon;
    const memberImageClass = imageData && imageData.length > 6? "memberItem__image-color":"memberItem__image";
    return (
        <div className="memberItem">
            <div className={memberImageClass}>
                <img src={memberImage} alt="icon" />
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