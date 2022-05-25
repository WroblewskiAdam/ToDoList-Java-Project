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
import MemberSection from '../memberSection/MemberSection';
import Checkbox from '@mui/material/Checkbox';

const label = { inputProps: { 'aria-label': 'Checkbox demo' } };

function TaskSection(props) {
    const [tasks, setTasks] = useState(null);
    const [open, setOpen] = React.useState(false);
    const [fillter, setFillter] = useState("All");
    const [givenTasks, setGivenTasks] = useState([]);
    const [updateProgres, setUpdateProgres] = useState(false);
    const [showGivenTasks, setShowGivenTasks] = useState(false);

    const handleOpen = () => setOpen(true);

    const handleClose = () => setOpen(false);


    useEffect(() => {
        if(props.teamId){
            TaskService.getAllTeamTasks(props.teamId).then(res => {
                setTasks(res);
            })
        }
        if(props.teamId === 0){
            TaskService.getPrivateTasks().then(res => {
                setTasks(res);
            });
        }
    }, [props.teamId, props.update]);

    useEffect(() => {
        if(props.teamId){
            if(fillter === "All"){
                if(!showGivenTasks){
                    TaskService.getAllTeamTasks(props.teamId).then(res => {
                        setTasks(res);
                    });
                }
                else{
                    TaskService.getAllGivenTeamTasks(props.teamId).then(res => {
                        setTasks(res);
                    });
                }
            } else if (fillter === "Today"){
                if(!showGivenTasks){
                    TaskService.getTodayTasks(props.teamId).then(res => {
                        setTasks(res);
                    });
                }
                else{
                    TaskService.getTodayGivenTasks(props.teamID).then(res => {
                        setTasks(res);
                    });
                }
            } else if (fillter === "Next 7 days"){
                if(!showGivenTasks){
                    TaskService.getSevenDaysTasks(props.teamId).then(res => {
                        setTasks(res);
                    });
                }
                else{
                    TaskService.getSevenDaysGivenTasks(props.teamId).then(res => {
                        setTasks(res);
                    });
                }
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
        if(props.teamId === 0){
            if(fillter === "All"){
                TaskService.getPrivateTasks().then(res => {
                    setTasks(res);
                    getAllPrivateTasks(props.teamId, res, setGivenTasks);
                });
            } else if (fillter === "Today"){
                TaskService.getPrivateTodayTasks().then(res => {
                    setTasks(res);
                })
            } else if (fillter === "Next 7 days"){
                TaskService.getPrivateSevenDaysTasks(props.teamId).then(res => {
                    setTasks(res);
                });
            } else if (fillter === "Expired"){
                TaskService.getPrivateExpiredTasks(props.teamId).then(res => {
                    setTasks(res);
                });
                TaskService.getPrivateGivenExpiredTasks(props.teamId).then(res => {
                    setGivenTasks(res);
                });
            } else if(fillter === "Done"){
                TaskService.getDoneTasks(props.teamId).then(res => {
                    setTasks(res);
                });
            }
        }

    }, [fillter, showGivenTasks])

    const handleCreateTaskBtn = () => {
        handleOpen();
    }

    const handleFillterChange = (e) => {
        setFillter(e.target.value);
    }

    const handleCheckboxClick = () => {
        setShowGivenTasks((state) => !state);
    }

    const taskBlock = tasks && tasks.length > 0? 
    <>
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
                        setUpdateProgres={setUpdateProgres}
                        setUpdateTeam={props.setUpdateTeam}
                    />
                );
            })
        }
    </> : null;
    
    const givenTasksBlock = props.teamId === 0 && givenTasks && givenTasks.length > 0 ? 
    <>
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
                        setUpdateProgres={setUpdateProgres}
                        setUpdateTeam={props.setUpdateTeam}
                    />
                );
            })
        }
    </> : null;

    const members = props.teamId !== 0 ? <div className="taskSection__members">
                                            <MemberSection teamId={props.teamId} updateProgres={updateProgres}/>
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
                                <div className="taskSection__tasks-fillters-checkbox">
                                    <span>Show given tasks:</span>
                                    <Checkbox checked={showGivenTasks} {...label} onChange={handleCheckboxClick} />
                                </div>
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
            <TaskModal setUpdate={props.setUpdate} open={open} handleOpen={handleOpen} handleClose={handleClose} teamId={props.teamId} setTasks={props.setTasks} />
        </div>
    );
}

// const NoTasksView = () => {
//     return (
//         <div className="taskSection__noTasks">
//             <div className="taskSection__noTasks-title">No tasks :) </div>
//             <img src={noTasksImage} alt='https://www.flaticon.com/free-icons/no-task' />
//         </div>
//     )
// }

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