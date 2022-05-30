import React, { useEffect, useRef, useState } from 'react';
import TextField from '@mui/material/TextField';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DateTimePicker } from '@mui/x-date-pickers/DateTimePicker';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import Checkbox from '@mui/material/Checkbox';
import Modal from '@mui/material/Modal';
import Box from "@mui/material/Box";
import TeamService from '../../services/teamService';
import OutlinedInput from '@mui/material/OutlinedInput';
import ListItemText from '@mui/material/ListItemText';
import TaskService from '../../services/tasksService';
import "./TaskModal.scss";
import AppUserService from '../../services/appUserService';

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
  PaperProps: {
    style: {
      maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
      width: 250,
    },
  },
};

function TaskModal(props) {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [dateTime, setDateTime] = useState(new Date());
    const [priority, setPriority] = useState("GREEN");
    const [members, setMembers] = useState([]);
    const [selectedMembers, setSelectedMembers] = useState([]);
    
    
    let modalRef = useRef();

    useEffect(() => {
        if(props.teamId){
            TeamService.getTeamMembers(props.teamId).then(res => {
                setMembers(res);
            });
        }
        if(props.teamId === 0){
            AppUserService.getAllUsers().then(res => {
                setMembers(res);
            });
        }
    }, [props.teamId]);

    const closeModal = () =>{
        props.handleClose();
    }

    const handleNameChange = (e) =>{
        setName(e.target.value);
    }

    const handleDescrChange = (e) => {
        setDescription(e.target.value);
    }

    const handleSelectMember = (e) => {
        const value = e.target.value;
        setSelectedMembers(
            value
        );
    }

    const handlePriorityChange = (e) => {
        setPriority(e.target.value);
    }
    
    const handleAddButton = () => {
        const id = JSON.parse(localStorage.getItem("accessToken")).id;
        let receiversIds = [];
        members.forEach(member => {
            const memberName = member.firstName + " " + member.lastName;
            if(selectedMembers.indexOf(memberName) > -1){
                receiversIds.push(member.id);
            }
        });
        
        TaskService.createTask(
            name,
            description,
            transformDateTime(dateTime),
            priority,
            id,
            receiversIds,
            props.teamId
        ).then(res => {
            props.setUpdate((update) => !update);
            props.setUpdateProgres((update) => !update);
        }).catch(e => console.log(e));

        setName("");
        setDescription("");
        setDateTime(new Date());
        setPriority("GREEN");
        setSelectedMembers([]);
        closeModal();
    }

    let colorLine = "#f26950";

    if(priority === "YELLOW"){
        colorLine = "#dfd70f";
    }
    else if(priority === "GREEN"){
        colorLine = "#2cc09c";
    }

    return (
        <Modal
            open={props.open}
            onClose={closeModal}
            aria-labelledby="modal-modal-title"
            aria-describedby="modal-modal-description"
            className="taskModal"
        >
            <Box
                sx={{"border":"none"}}
            >
                <div ref={modalRef} className="taskModal__container">
                    <div className="taskModal__title">
                        Add Task
                    </div>
                    <div className="taskModal__block">
                        <div className="taskModal__input">
                            <TextField label="Title" variant="standard" value={name} onChange={handleNameChange} sx={{"width":"100%", "marginBottom":"15px"}}
                            inputProps={{style: {fontSize: 20, marginLeft: 13}}}
                            InputLabelProps={{style: {fontSize: 20, marginLeft: 13}}}
                            />
                            <TextField
                                label="Description"
                                multiline
                                value={description}
                                onChange={handleDescrChange}
                                sx={{"width":"100%", "marginBottom":"20px"}}
                                rows={4}
                                inputProps={{style: {fontSize: 20}}}
                                InputLabelProps={{style: {fontSize: 20, backgroundColor: "#f9f9f9"}}}
                            />
                        </div>
                        <div className="taskModal__picker">
                            <div className="taskModal__picker-item">
                                <LocalizationProvider dateAdapter={AdapterDateFns} >
                                    <DateTimePicker
                                        renderInput={(props) => <TextField {...props} />}
                                        label="Deadline"
                                        value={dateTime}
                                        onChange={(newValue) => {
                                            setDateTime(newValue);
                                        }}
                                        sx = {{"width": "100%"}}
                                    />
                                </LocalizationProvider>
                            </div>
                            <div className="taskModal__picker-item">
                                <FormControl fullWidth>
                                    <InputLabel id="demo-simple-select-label">Priority</InputLabel>
                                    <Select
                                        sx={{color: colorLine}}
                                        labelId="demo-simple-select-label"
                                        id="demo-simple-select"
                                        value={priority}
                                        label="Priority"
                                        onChange={handlePriorityChange}
                                    >
                                        <MenuItem sx={{"color" : "#2cc09c"}} value={"GREEN"}>GREEN</MenuItem>
                                        <MenuItem sx={{"color" : "#dbdb23"}} value={"YELLOW"}>YELLOW</MenuItem>
                                        <MenuItem sx={{"color" : "red"}} value={"RED"}>RED</MenuItem>
                                    </Select>
                                </FormControl>
                            </div>
                        </div>
                        <div className="taskModal__users">
                            <FormControl sx={{width: "100%", "marginBottom" : "10px"}}>
                                <InputLabel sx={{"backgroundColor":"#f9f9f9"}}>Members</InputLabel>
                                <Select
                                    labelId="demo-multiple-checkbox-label"
                                    id="demo-multiple-checkbox"
                                    multiple
                                    value={selectedMembers}
                                    onChange={handleSelectMember}
                                    input={<OutlinedInput label="Tag" />}
                                    renderValue={(selected) => selected.join(', ')}
                                    MenuProps={MenuProps}
                                >
                                    { 
                                        
                                        members ? members.map((member) => {
                                        const name = member.firstName + " " + member.lastName;
                                        return(
                                            <MenuItem key={name} value={name}>
                                            <Checkbox checked={selectedMembers.indexOf(name) > -1} />
                                            <ListItemText primary={name} />
                                            </MenuItem>
                                        );
                                    }) : null
                                    }
                                </Select>
                            </FormControl>
                        </div>
                        
                        <div className="taskModal__buttons">
                            <div className="taskModal__btn add_btn" onClick={handleAddButton}>Add</div>
                            <div className="taskModal__btn" onClick={closeModal} >Cancel</div>
                        </div>
                    </div>
                </div>
            </Box>
        </Modal>
    );
}


const transformDateTime = (dateTime) => {
    return "" + dateTime.getFullYear() + "-" + padTime(dateTime.getMonth() === 12 ? 1 : dateTime.getMonth() + 1) + "-" + padTime(dateTime.getDate()) + "T" + padTime(dateTime.getHours()) + ":" + padTime(dateTime.getMinutes());
}

const padTime = (number) => {
    return (number + '').padStart(2, '0');
}

export default TaskModal;