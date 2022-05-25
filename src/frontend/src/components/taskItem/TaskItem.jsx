import React, { useEffect, useState } from 'react';
import TaskService from '../../services/tasksService';
import "./TaskItem.scss";
import Checkbox from '@mui/material/Checkbox';
import AppUserService from '../../services/appUserService';

const label = { inputProps: { 'aria-label': 'Checkbox demo' } };

function TaskItem(props) {
    const [descr, setDescr] = useState(false);
    const [receivers, setReceivers] = useState(undefined);
    const [receiversWhoDone, setReceiversWhoDone] = useState(undefined);
    const [checked, setChecked] = useState(false);
    const [tasksId, setTasksId] = useState(undefined);
    const [doneTask, setDoneTask] = useState(false);
    const [givenTasks, setGivenTasks] = useState(undefined);

    useEffect(() => {
        TaskService.getReceivers(props.id).then(res => {
            setReceivers(res);
        });

        TaskService.getReceiversWhoDone(props.id).then(res => {
            setReceiversWhoDone(res);
        });
        
        AppUserService.getTasks().then(res => {
            let response = res.map((item => item.id));
            setTasksId(response);
            

            if(response.indexOf(props.id) >= 0){
                TaskService.checkIfTaskDoneByUser(props.id).then((res) => {
                    setChecked(res);
                });
            }
            else{
                setChecked(props.done);
            }
        });

        TaskService.getGivenTasks().then((res) => {
            setGivenTasks(res);
        });

        // setDoneTask(props.done);
    }, [props.id]);

    const handleTaskItemClick = (e) => {
        if(!e.target.type && !e.target.dataset.type){
            setDescr((descr) => !descr);
        }
    }

    const handleCheckboxClick = () =>{
        setChecked((checked) => !checked);
        TaskService.tickTask(props.id).then(() => {
            TaskService.getReceiversWhoDone(props.id).then(res => {
                setReceiversWhoDone(res);
            });
            props.setUpdateProgres((update) => !update);
            props.setUpdateTeam((update) => !update);
        });
    }

    const handleDeleteClick = () => {
        TaskService.deleteTask(props.id).then(() => {
            props.setUpdateProgres((update) => !update);
            props.setUpdateTeam((update) => !update);
            props.setUpdate((update) => !update);
        });
    }

    let colorLine = "#f26950";

    if(props.priority === "YELLOW"){
        colorLine = "#dfd70f";
    }
    else if(props.priority === "GREEN"){
        colorLine = "#2cc09c";
    }

    const deadlineItems = props.deadline.split("T");
    const creationTimeItems = props.creationTime.split("T");
    
    const sub = subtractTime(deadlineItems[0], deadlineItems[1]);

    const years = sub[0] ? <div className="taskItem__time-item">{sub[0]} {addS(sub[0], "year")}</div> : null;
    const months = sub[1] ? <div className="taskItem__time-item">{sub[1]} {addS(sub[1], "month")}</div> : null;
    const days = (sub[0] === 0 && sub[1] === 0 && sub[2] !== 0) ? <div className="taskItem__time-item">{sub[2]} {addS(sub[2], "day")}</div> : null;
    const hours = (sub[0] === 0 && sub[1] === 0 && sub[3] !== 0) ? <div className="taskItem__time-item">{sub[3]} {addS(sub[3], "hour")}</div> : null;
    const minutes = (sub[0] === 0 && sub[1] === 0 && sub[2] === 0 && sub[4] !== 0) ? <div className="taskItem__time-item">{sub[4]} {addS(sub[3], "minute")}</div> : null;
    const seconds = sub[0] === 0 && sub[1] === 0 && sub[2] === 0 && sub[3] === 0 && sub[5] !== 0 ? <div className="taskItem__time-item">{sub[5]} {addS(sub[3], "second")}</div> : null;

    const descrClass = descr ? "taskItem__description" : "taskItem__description-active";

    const timeText = sub === 0 ? "Time is gone" : "Time Left: ";
    const timeTime = sub === 0 ? null : <>{years}{months}{days}{hours}{minutes}{seconds}</>

    const taskItemClass = checked ? "taskItem checked" : "taskItem";

    const checkbox = tasksId && tasksId.length > 0 && tasksId.indexOf(props.id) >= 0? <Checkbox checked={checked} {...label} onChange={handleCheckboxClick} /> : null;
    
    const trash = givenTasks && givenTasks.length > 0 && givenTasks.map((item) => item.id).indexOf(props.id) >= 0 ? 

    <div className="taskItem__trash" data-type="trash" onClick={handleDeleteClick}>
        <svg data-type="trash" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><path data-type="trash" d="M135.2 17.69C140.6 6.848 151.7 0 163.8 0H284.2C296.3 0 307.4 6.848 312.8 17.69L320 32H416C433.7 32 448 46.33 448 64C448 81.67 433.7 96 416 96H32C14.33 96 0 81.67 0 64C0 46.33 14.33 32 32 32H128L135.2 17.69zM31.1 128H416V448C416 483.3 387.3 512 352 512H95.1C60.65 512 31.1 483.3 31.1 448V128zM111.1 208V432C111.1 440.8 119.2 448 127.1 448C136.8 448 143.1 440.8 143.1 432V208C143.1 199.2 136.8 192 127.1 192C119.2 192 111.1 199.2 111.1 208zM207.1 208V432C207.1 440.8 215.2 448 223.1 448C232.8 448 240 440.8 240 432V208C240 199.2 232.8 192 223.1 192C215.2 192 207.1 199.2 207.1 208zM304 208V432C304 440.8 311.2 448 320 448C328.8 448 336 440.8 336 432V208C336 199.2 328.8 192 320 192C311.2 192 304 199.2 304 208z"/></svg>
    </div> : null;

    const receiversBlock = receivers && receivers.length > 0 ? <>
                                                                    {
                                                                        receivers.map((receiver) => {
                                                                            return(
                                                                                <div className="taskItem__receivers-item">
                                                                                    {receiver.firstName} {receiver.lastName} 
                                                                                </div>
                                                                            )
                                                                        })
                                                                    }
                                                                </> : null;
    return (
        <>
            <div className={taskItemClass} onClick={handleTaskItemClick}>
                <div className="taskItem__header">
                    <div className="taskItem__header-item">
                        <div className="taskItem__priority" style={{"backgroundColor" : colorLine}}>
                        </div>
                        <div className="taskItem__title">
                            {props.title}
                        </div>
                    </div>
                    {checkbox}
                </div>
                <div className="taskItem__line"></div>
                <div className={descrClass}>
                    <div className="taskItem__description-item">
                        <div className="taskItem__description-time">
                            <span>Creation time:</span> {creationTimeItems[0]} {creationTimeItems[1]}
                        </div>
                        <div className="taskItem__description-time">
                        <span>Deadline:</span> {deadlineItems[0]} {deadlineItems[1]}
                        </div>
                    </div>
                    <div className="taskItem__line"></div>
                    <div className="taskItem__receivers">
                        <span>Receivers: </span>
                        {receiversBlock}
                    </div>
                    <div className="taskItem__line"></div>
                    <div className="taskItem__description-item">
                        {props.description}
                    </div>
                </div>
                <div className="taskItem__line"></div>
                <div className="taskItem__block">
                    <div className="taskItem__block-item">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 384 512"><path d="M352 0C369.7 0 384 14.33 384 32C384 49.67 369.7 64 352 64V74.98C352 117.4 335.1 158.1 305.1 188.1L237.3 256L305.1 323.9C335.1 353.9 352 394.6 352 437V448C369.7 448 384 462.3 384 480C384 497.7 369.7 512 352 512H32C14.33 512 0 497.7 0 480C0 462.3 14.33 448 32 448V437C32 394.6 48.86 353.9 78.86 323.9L146.7 256L78.86 188.1C48.86 158.1 32 117.4 32 74.98V64C14.33 64 0 49.67 0 32C0 14.33 14.33 0 32 0H352zM111.1 128H272C282.4 112.4 288 93.98 288 74.98V64H96V74.98C96 93.98 101.6 112.4 111.1 128zM111.1 384H272C268.5 378.7 264.5 373.7 259.9 369.1L192 301.3L124.1 369.1C119.5 373.7 115.5 378.7 111.1 384V384z"/></svg>
                        <div className="taskItem__time">
                        {timeText}
                        {timeTime}
                        </div>
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 384 512"><path d="M186.1 .1032c-105.1 3.126-186.1 94.75-186.1 199.9v264c0 14.25 17.3 21.38 27.3 11.25l24.95-18.5c6.625-5.001 16-4.001 21.5 2.25l43 48.31c6.25 6.251 16.37 6.251 22.62 0l40.62-45.81c6.375-7.251 17.62-7.251 24 0l40.63 45.81c6.25 6.251 16.38 6.251 22.62 0l43-48.31c5.5-6.251 14.88-7.251 21.5-2.25l24.95 18.5c10 10.13 27.3 3.002 27.3-11.25V192C384 83.98 294.9-3.147 186.1 .1032zM128 224c-17.62 0-31.1-14.38-31.1-32.01s14.38-32.01 31.1-32.01s32 14.38 32 32.01S145.6 224 128 224zM256 224c-17.62 0-32-14.38-32-32.01s14.38-32.01 32-32.01c17.62 0 32 14.38 32 32.01S273.6 224 256 224z"/></svg>
                        <div className="taskItem__time">
                            People: {receivers ? receivers.length : 0}
                        </div>
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 448 512"><path d="M160 32V64H288V32C288 14.33 302.3 0 320 0C337.7 0 352 14.33 352 32V64H400C426.5 64 448 85.49 448 112V160H0V112C0 85.49 21.49 64 48 64H96V32C96 14.33 110.3 0 128 0C145.7 0 160 14.33 160 32zM0 192H448V464C448 490.5 426.5 512 400 512H48C21.49 512 0 490.5 0 464V192zM328.1 304.1C338.3 295.6 338.3 280.4 328.1 271C319.6 261.7 304.4 261.7 295 271L200 366.1L152.1 319C143.6 309.7 128.4 309.7 119 319C109.7 328.4 109.7 343.6 119 352.1L183 416.1C192.4 426.3 207.6 426.3 216.1 416.1L328.1 304.1z"/></svg>
                        <div className="taskItem__time">
                            Done: {receiversWhoDone ? receiversWhoDone.length : 0}
                        </div>
                    </div>
                    {trash}
                </div>
            </div>
        </>
    );
}

const timeConverter = (date, time) => {
    let dateArray = date.split("-");
    let timeArray = time.split(":");

    const dataObj = new Date(dateArray[0], dateArray[1]-1, dateArray[2], timeArray[0], timeArray[1], timeArray[2], 0);
    return dataObj;
}

const subtractTime = (date2, time2) => {
    const now = new Date();
    const dTime = timeConverter(date2, time2);

    let ans = dTime.getTime() - (now.getTime());

    if(ans <= 0){
        return 0;
    }

    ans = ~~(ans/1000);

    let ansArray = new Array(6).fill(0);

    ansArray[5] = ans % 60;
    ans = ~~(ans / 60);
    ansArray[4] = ans % 60;
    ans = ~~(ans / 60);
    ansArray[3] = ans % 24;
    ans = ~~(ans / 24);
    
    if(Math.abs(dTime.getFullYear() - now.getFullYear()) > 0){
        if(ans >=  365){
            ansArray[0] = ~~(ans / 365);
        }
    }

    if(Math.abs(dTime.getMonth() - now.getMonth()) > 0){
        if(ans >= 30){
            ansArray[1] = ~~(ans / 30);
        }
    }

    if(Math.abs(dTime.getDate() - now.getDate()) > 0){
        if(ans >= 1){
            ansArray[2] = ans;
        }
    }



    return ansArray;
}

const addS = (time, word) => {
    if(time === 1){
        return word;
    }
    return word + "s";
}
export default TaskItem;