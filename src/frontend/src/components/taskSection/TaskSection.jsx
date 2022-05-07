import React, { useState, useEffect } from 'react';
import TaskItem from '../taskItem/TaskItem';
import "./TaskSection.scss";
import {Dropdown, DropdownButton} from "react-bootstrap";
import noTasksImage from "../../images/picture/no-task.png";

function TaskSection(props) {
    const [tasks, setTasks] = useState(null);

    useEffect(() => {
        setTasks(props.tasks);
    }, [props]);


    const taskBlock = tasks && tasks.length > 0? <div className="taskSection__tasks-body">
                                {
                                    tasks.map((item) => {
                                        return (
                                            <TaskItem 
                                                key={item.id}
                                                id={item.id}
                                                title={item.title}
                                                description={item.description}
                                                deadline={item.deadline}
                                                creationTime={item.creationTime}
                                                priority={item.priority}
                                            />
                                        );
                                    })
                                }
                            </div> : <NoTasksView/>;
    return (
        <div className='taskSection'>
            <div className="taskSection__title">
                {props.title}
            </div>
            <div className="taskSection__wrapper">
                <div className="taskSection__filters">

                </div>
                <div className="taskSection__body">
                    <div className="taskSection__team">

                    </div>
                    <div className="taskSection__tasks">
                        <div className="taskSection__tasks-fillters">
                            {/* <div className="taskSection__tasks-fillters-item"></div>
                            <div className="taskSection__tasks-fillters-item"></div> */}
                            <DropdownButton 
                                className="taskSection__tasks-fillters-item"
                                title="Sorting"
                            >
                                <Dropdown.Item>By title</Dropdown.Item>
                                <Dropdown.Item>By time</Dropdown.Item>
                            </DropdownButton>
                            <DropdownButton 
                                className="taskSection__tasks-fillters-item"
                                title="Fillters"
                            >
                                <Dropdown.Item>All</Dropdown.Item>
                                <Dropdown.Item>Today</Dropdown.Item>
                                <Dropdown.Item>Next 7 days</Dropdown.Item>
                            </DropdownButton>
                        </div>
                        {taskBlock}
                    </div>
                </div>
            </div>
        </div>
    );
}

const NoTasksView = () => {
    return (
        <div className="taskSection__noTasks">
            <div className="taskSection__noTasks-title">No tasks :) </div>
            <img src={noTasksImage} alt='https://www.flaticon.com/free-icons/no-task' />
        </div>
    )
}
export default TaskSection;