import React, { useState, useEffect } from 'react';
import TaskItem from '../taskItem/TaskItem';
import "./TaskSection.scss";
import noTasksImage from "../../images/picture/no-task.png";
import TaskModal from '../taskModal/TaskModal';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import TaskService from '../../services/tasksService';
import TeamService from '../../services/teamService';

function TaskSection(props) {
    const [tasks, setTasks] = useState(null);
    const [open, setOpen] = React.useState(false);
    const [fillter, setFillter] = useState("All");

    const handleOpen = () => setOpen(true);

    const handleClose = () => setOpen(false);

    useEffect(() => {
        setTasks(props.tasks);
    }, [props]);

    useEffect(() => {
        if(props.teamId){
            if(fillter === "All"){
                TeamService.getTeamTasks(props.teamId).then(res => {
                    setTasks(res);
                });
            } else if (fillter === "Today"){
                TaskService.getTodayTasks(props.teamId).then(res => {
                    setTasks(res);
                });
            } else if (fillter === "Next 7 days"){
                TaskService.getSevenDaysTasks(props.teamId).then(res => {
                    setTasks(res);
                });
            } else if (fillter === "Expired"){
                TaskService.getExpiredTasks(props.teamId).then(res => {
                    setTasks(res);
                });
            } else if(fillter === "Done"){
                TaskService.getDoneTasks(props.teamId).then(res => {
                    setTasks(res);
                });
            }
        }

    }, [fillter])

    const handleCreateTaskBtn = () => {
        handleOpen();
    }

    const handleFillterChange = (e) => {
        setFillter(e.target.value);
    }

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
                                                done={item.isDone}
                                                teamId={props.teamId}
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
                        <div className="taskSection__header">
                            <div className="taskSection__tasks-fillters">
                                <FormControl 
                                    className="taskSection__tasks-fillters-item"
                                >
                                    <InputLabel className="taskSection__tasks-fillters-label">Fillter</InputLabel>
                                    <Select

                                        value={fillter}
                                        label="Fillter"
                                        onChange={handleFillterChange}
                                        className="taskSection__tasks-fillters-select"
                                    >
                                        <MenuItem value={"All"}>All</MenuItem>
                                        <MenuItem value={"Today"}>Today</MenuItem>
                                        <MenuItem value={"Next 7 days"}>Next 7 days</MenuItem>expired
                                        <MenuItem value={"Expired"}>Expired</MenuItem>
                                        <MenuItem value={"Done"}>Done</MenuItem>
                                    </Select>
                                </FormControl>
                            </div>
                            <div className="taskSection__createBtn" onClick={handleCreateTaskBtn}>Create Task</div>
                        </div>
                        {taskBlock}
                    </div>
                </div>
            </div>
            <TaskModal open={open} handleOpen={handleOpen} handleClose={handleClose} teamId={props.teamId} setTasks={props.setTasks} />
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