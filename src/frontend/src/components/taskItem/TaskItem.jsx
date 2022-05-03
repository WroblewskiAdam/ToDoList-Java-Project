import React from 'react';
import "./TaskItem.scss";
import greenFlag from "../../images/picture/green-flag.png";
import yellowFlag from "../../images/picture/yellow-flag.png";
import redFlag from "../../images/picture/red-flag.png";

function TaskItem(props) {
    const priorityColor = props.priority.toLowerCase();

    let colorImg = redFlag;

    if(props.priority === "YELLOW"){
        colorImg = yellowFlag;
    }
    else if(props.priority === "GREEN"){
        colorImg = greenFlag;
    }

    return (
        <div className='taskItem'>
            <div className="taskItem__title">
                {props.title}
            </div>
            {/* <div className="taskItem__description">
                {props.description}
            </div> */}
            <div className={"taskItem__priority " + priorityColor}>
                <img src={colorImg} alt="flag" />
            </div>
            <div className="taskItem__deadline">
                {props.deadline}
            </div>
            <div className="taskItem__creationTime">
                {props.creationTime}
            </div>
        </div>
    );
}

export default TaskItem;