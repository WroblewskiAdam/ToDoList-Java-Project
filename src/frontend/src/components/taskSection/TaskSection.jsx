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
import MemberSection from '../memberSection/MemberSection';

function TaskSection(props) {
    const [tasks, setTasks] = useState(null);
    const [open, setOpen] = React.useState(false);
    const [fillter, setFillter] = useState("All");
    const [givenTasks, setGivenTasks] = useState([]);

    const handleOpen = () => setOpen(true);

    const handleClose = () => setOpen(false);

    
    useEffect(() => {
        if(props.teamId){
            TeamService.getTeamTasks(props.teamId).then(res => {
                setTasks(res);
            });
        }
        if(props.teamId === 0){
            TaskService.getPrivateTasks().then(res => {
                setTasks(res);
                getAllPrivateTasks(props.teamId, res, setGivenTasks);
            });
        }
    }, [props.teamId]);

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
        // if(props.teamId === 0){
        //     if(fillter === "All"){
        //         TaskService.getPrivateTasks().then(res => {
        //             setTasks(res);
        //             getAllPrivateTasks(props.teamId, props.tasks, setGivenTasks);
        //         });
        //     } else if (fillter === "Today"){
        //         TaskService.getTodayTasks(props.teamId).then(res => {
        //             setTasks(res);
        //         });
        //     } else if (fillter === "Next 7 days"){
        //         TaskService.getSevenDaysTasks(props.teamId).then(res => {
        //             setTasks(res);
        //         });
        //     } else if (fillter === "Expired"){
        //         TaskService.getExpiredTasks(props.teamId).then(res => {
        //             setTasks(res);
        //         });
        //     } else if(fillter === "Done"){
        //         TaskService.getDoneTasks(props.teamId).then(res => {
        //             setTasks(res);
        //         });
        //     }
        // }

    }, [fillter])

    const handleCreateTaskBtn = () => {
        handleOpen();
    }

    const handleFillterChange = (e) => {
        setFillter(e.target.value);
    }

    const taskBlock = tasks && tasks.length > 0? <>
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
                                                setUpadate={props.setUpadate}
                                            />
                                        );
                                    })
                                }
                            </> : <NoTasksView/>;
    
    const givenTasksBlock = props.teamId === 0 && givenTasks && givenTasks.length > 0 ? <>
                                                                    {
                                                                        givenTasks.map((item) => {
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
                                                                                    setUpadate={props.setUpadate}
                                                                                />
                                                                            );
                                                                        })
                                                                    }
                                                                </> : null;

    const members = props.teamId !== 0 ? <div className="taskSection__members">
                                            <MemberSection teamId={props.teamId} update={props.update}/>
                                        </div> : null;
    return (
        <div className='taskSection'>
            <div className="taskSection__title">
                {props.title}
            </div>
            <div className="taskSection__wrapper">
                <div className="taskSection__filters">

                </div>
                <div className="taskSection__body">
                    {members}
                    <div className="taskSection__tasks" style={props.teamId === 0 ? {'width' : "100%"} : {'width' : "65%"}}>
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
                        <div className="taskSection__tasks-body">
                            {taskBlock}
                            {givenTasksBlock}
                        </div>
                        
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

const getAllPrivateTasks = (teamId, tasks, setGivenTasks) => {
    if(teamId === 0){
        setGivenTasks([]);
        TaskService.getGivenTasks().then((res) => {
            let array = [];
            res.forEach((givenTask) => {
                let good = true;
                tasks.forEach((task) => {
                    if(task.id === givenTask.id){
                        good = false;
                    }
                });

                if(good){
                    array = [...array, givenTask];
                }
            });
            setGivenTasks(array);
        });
    }
}
export default TaskSection;