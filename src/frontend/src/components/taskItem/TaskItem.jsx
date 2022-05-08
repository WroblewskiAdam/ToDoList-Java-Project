import React, { useState } from 'react';
import "./TaskItem.scss";

function TaskItem(props) {
    const [descr, setDescr] = useState(false);
    

    const handleTaskItemClick = () => {
        setDescr((descr) => !descr);
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
    const days = (sub[0] === 0 && sub[2] !== 0) ? <div className="taskItem__time-item">{sub[2]} {addS(sub[2], "day")}</div> : null;
    const hours = (sub[0] === 0 && sub[1] === 0 && sub[3] !== 0) ? <div className="taskItem__time-item">{sub[3]} {addS(sub[3], "hour")}</div> : null;
    const minutes = (sub[0] === 0 && sub[1] === 0 && sub[2] === 0 && sub[4] !== 0) ? <div className="taskItem__time-item">{sub[4]} {addS(sub[3], "minute")}</div> : null;
    const seconds = sub[0] === 0 && sub[1] === 0 && sub[2] === 0 && sub[3] === 0 && sub[5] !== 0 ? <div className="taskItem__time-item">{sub[5]} {addS(sub[3], "second")}</div> : null;

    const descrClass = descr ? "taskItem__description" : "taskItem__description-active";

    const timeText = sub === 0 ? "Time is gone" : "Time Left: ";
    const timeTime = sub === 0 ? null : <>{years}{months}{days}{hours}{minutes}{seconds}</>
    return (
        <>
            <div className='taskItem' onClick={handleTaskItemClick}>
                <div className="taskItem__block">
                    <div className="taskItem__priority" style={{"backgroundColor" : colorLine}}>
                    </div>
                    <div className="taskItem__title">
                        {props.title}
                    </div>
                </div>
                <div className="taskItem__line"></div>
                <div className={descrClass}>
                    <div className="taskItem__description-item">
                        <div className="taskItem__description-time">
                            Creation time: {creationTimeItems[0]} {creationTimeItems[1]}
                        </div>
                        <div className="taskItem__description-time">
                            Deadline: {deadlineItems[0]} {deadlineItems[1]}
                        </div>
                    </div>
                    <div className="taskItem__line"></div>
                    <div className="taskItem__description-item">
                        {props.description}
                    </div>
                </div>
                <div className="taskItem__line"></div>
                <div className="taskItem__block">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 384 512"><path d="M352 0C369.7 0 384 14.33 384 32C384 49.67 369.7 64 352 64V74.98C352 117.4 335.1 158.1 305.1 188.1L237.3 256L305.1 323.9C335.1 353.9 352 394.6 352 437V448C369.7 448 384 462.3 384 480C384 497.7 369.7 512 352 512H32C14.33 512 0 497.7 0 480C0 462.3 14.33 448 32 448V437C32 394.6 48.86 353.9 78.86 323.9L146.7 256L78.86 188.1C48.86 158.1 32 117.4 32 74.98V64C14.33 64 0 49.67 0 32C0 14.33 14.33 0 32 0H352zM111.1 128H272C282.4 112.4 288 93.98 288 74.98V64H96V74.98C96 93.98 101.6 112.4 111.1 128zM111.1 384H272C268.5 378.7 264.5 373.7 259.9 369.1L192 301.3L124.1 369.1C119.5 373.7 115.5 378.7 111.1 384V384z"/></svg>
                    <div className="taskItem__time">
                       {timeText}
                       {timeTime}
                    </div>
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 384 512"><path d="M186.1 .1032c-105.1 3.126-186.1 94.75-186.1 199.9v264c0 14.25 17.3 21.38 27.3 11.25l24.95-18.5c6.625-5.001 16-4.001 21.5 2.25l43 48.31c6.25 6.251 16.37 6.251 22.62 0l40.62-45.81c6.375-7.251 17.62-7.251 24 0l40.63 45.81c6.25 6.251 16.38 6.251 22.62 0l43-48.31c5.5-6.251 14.88-7.251 21.5-2.25l24.95 18.5c10 10.13 27.3 3.002 27.3-11.25V192C384 83.98 294.9-3.147 186.1 .1032zM128 224c-17.62 0-31.1-14.38-31.1-32.01s14.38-32.01 31.1-32.01s32 14.38 32 32.01S145.6 224 128 224zM256 224c-17.62 0-32-14.38-32-32.01s14.38-32.01 32-32.01c17.62 0 32 14.38 32 32.01S273.6 224 256 224z"/></svg>
                    <div className="taskItem__time">
                        People:
                        5
                    </div>
                </div>
            </div>
        </>
    );
}

const timeConverter = (date, time) => {
    let dateArray = date.split("-");
    let timeArray = time.split(":");

    const dataObj = new Date(dateArray[0], dateArray[1], dateArray[2], timeArray[0], timeArray[1], timeArray[2], 0);

    return dataObj;
}

const subtractTime = (date2, time2) => {
    const nowXD = new Date();
    const now = new Date(nowXD.getFullYear(), nowXD.getMonth() + 1, nowXD.getDate(), nowXD.getHours(), nowXD.getMinutes(), nowXD.getSeconds(), 0);
    const dTime = timeConverter(date2, time2);

    let ans = dTime.getTime() - (now.getTime() );

    // console.log("d" + dTime.getTime());
    // console.log("n" + now.getFullYear() + " " + now.getMonth() + " " + now.getDate() + " " + now.getHours() + " " + now.getMinutes());

    if(ans <= 0){
        return 0;
    }

    ans /= 1000; 

    let ansArray = new Array(6).fill(0);

    ansArray[5] = ans % 60;
    ans = ~~(ans / 60);
    ansArray[4] = ans % 60;
    ans = ~~(ans / 60);
    ansArray[3] = ans % 24;
    ans = ~~(ans / 24);
    
    if(Math.abs(dTime.getFullYear() - now.getFullYear()) > 0){
        if(ansArray[5] === 0){
            ansArray[0] = dTime.getFullYear() - now.getFullYear();
        }
        else{
            ansArray[0] = dTime.getFullYear() - now.getFullYear() - 1;
        }
    }

    if(Math.abs(dTime.getMonth() - now.getMonth()) > 0){
        if(ansArray[5] === 0){
            ansArray[1] = dTime.getMonth() - now.getMonth();
        }
        else{
            ansArray[1] = dTime.getMonth() - now.getMonth() - 1;
        }
    }

    if(Math.abs(dTime.getDate() - now.getDate()) > 0){
        if(ansArray[5] === 0){
            ansArray[2] = dTime.getDate() - now.getDate();
        }
        else{
            ansArray[2] = dTime.getDate() - now.getDate() - 1;
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