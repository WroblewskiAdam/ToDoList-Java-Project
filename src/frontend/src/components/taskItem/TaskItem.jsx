import React, { useState } from 'react';
import "./TaskItem.scss";
import greenFlag from "../../images/picture/green-flag.png";
import yellowFlag from "../../images/picture/yellow-flag.png";
import redFlag from "../../images/picture/red-flag.png";

function TaskItem(props) {
    const [descr, setDescr] = useState(false);
    

    const handleTaskItemClick = () => {
        setDescr((descr) => !descr);
    }

    let colorImg = redFlag;

    if(props.priority === "YELLOW"){
        colorImg = yellowFlag;
    }
    else if(props.priority === "GREEN"){
        colorImg = greenFlag;
    }

    return (
        <>
            <div className='taskItem' onClick={handleTaskItemClick}>
                <div className="taskItem__title">
                    {props.title}
                </div>
                <div className="taskItem__deadline">
                    {props.deadline}
                </div>
                <div className="taskItem__creationTime">
                    {props.creationTime}
                </div>
                <div className={"taskItem__priority"}>
                    <img src={colorImg} alt="flag" />
                </div>
            </div>
            <div className="taskItem__description" style={{"display": descr ? "block" : "none"}}>{props.description}</div>
        </>
    );
}

export default TaskItem;