import React, { useState, useEffect } from 'react';
import icon from "../../images/icons/user-solid.svg"
import "./MemberItem.scss";
import AppUserService from '../../services/appUserService';
import TeamService from '../../services/teamService';
import { CircularProgressbar } from 'react-circular-progressbar';
import 'react-circular-progressbar/dist/styles.css';

function MemberItem(props) {
    const [, setAllTasks] = useState([]);
    const [, setAllTeamTasks] = useState([]);
    const [tasks, setTasks] = useState([]);
    const [doneTasks, setDoneTasks] = useState([]);
    const [image, setImage] = useState(undefined);

    useEffect(() => {
        AppUserService.getTasks(props.id).then((res1) => {
            setAllTasks(res1);

            TeamService.getTeamTasks(props.teamId).then((res2) => {
                setAllTeamTasks(res2);

                let teamTasksIds = res2.map((item) => item.id);
                
                setDoneTasks([]);
                setTasks([]);

                res1.forEach((task) => {
                    if(teamTasksIds.indexOf(task.id) > -1){
                        if(task.isDone){
                            setDoneTasks((doneTasks) => [...doneTasks, task]);
                        }
                        setTasks((tasks) => [...tasks, task]);
                    }
                });
            });
        });
    }, [props.teamId, props.updateProgres]);


    // useEffect(() => {
    //     AppUserService.getImage(props.id).then((res) => {
    //         // console.log("Blob: ", window.URL.createObjectURL(res));
    //         console.log(res);
  
    //         // let blob = new Blob([res], {type: props.image.type});

    //         // var reader = new FileReader();
    //         // reader.readAsDataURL(blob);
    //         // reader.onloadend = function () {
    //         //     var base64String = reader.result;
    //         //     // console.log('Base64 String - ', base64String);
    //         //     setImage(base64String);
    //         //     // console.log('Base64 String without Tags- ', base64String.substr(base64String.indexOf(', ') + 1));
    //         // }
    //     })
    // }, []);

    let percentage = 0;
    if(tasks.length){
        if(doneTasks.length){
            let lenDoneTasks= doneTasks.length;
            let lenTasks = tasks.length;

            percentage = Math.round(lenDoneTasks/lenTasks*100);
        }
    }
    else{
        percentage = 100;
    }
    
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