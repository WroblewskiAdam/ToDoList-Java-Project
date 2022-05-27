import React from 'react';
import { useState, useEffect} from 'react';
import "./RoleEditModal.scss"
import AppUserService from '../../services/appUserService';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import Modal from '@mui/material/Modal';
import Box from "@mui/material/Box";



const RoleEditModal = (props) => {
    const [members, setMembers] = useState([]);
    const [selectedMember, setSelectedMember] = useState("");
    const [role, setRole] = useState("");

    useEffect(() => {
        AppUserService.getAllUsers().then(data=>{
            data.forEach((item) => {
                if(item.id !== JSON.parse(localStorage.getItem("accessToken")).id){
                    setMembers((members) => [...members, item]);
                }
            })
        });

        let r = JSON.parse(localStorage.getItem("accessToken")).role;
        setRole(r.substring(1, r.length - 1));
    }, []);

    const closeModal = () =>{
        props.setModalState(false);
    }

    const handleSelectedMember = (e) => {
        setSelectedMember(e.target.value);
    }

    const handleRoleSelection = (e) => {
        setRole(e.target.value);
    }

    const handleEditButton = () => {
        let selectedMemberId = -1;

        members.forEach(member => {
            const memberName = member.firstName + " " + member.lastName;
            if(selectedMember === memberName){
                selectedMemberId = member.id;
            }
        });

        console.log("id ", selectedMemberId, " role: ", role, " selectedMember: ", selectedMember);
        AppUserService.changeUserRole(selectedMemberId, role);

        closeModal();
    }

    return (
        <Modal
            open={props.modalState}
            onClose={closeModal}
            aria-labelledby="modal-modal-title"
            aria-describedby="modal-modal-description"
            className="roleEditModal"
        >
            <Box
                sx={{"border":"none"}}
            >
                <div className="roleEditModal__container">
                    <div className="roleEditModal__title">
                        Edit Roles
                    </div>
                    <div className="roleEditModal__block">
                        <div className="roleEditModal__picker">
                            <div className="roleEditModal__users">
                                <FormControl sx={{width: "100%", "marginBottom" : "15px"}}>
                                    <InputLabel sx={{"backgroundColor":"#f9f9f9"}}>Members</InputLabel>
                                    <Select
                                        labelId="demo-simple-select-label"
                                        id="demo-simple-select"
                                        value={selectedMember}
                                        onChange={handleSelectedMember}
                                        label="Role"
                                    >
                                        { 
                                            
                                            members ? members.map((member) => {
                                            const name = member.firstName + " " + member.lastName;
                                                return(
                                                    <MenuItem key={member.id} value={name} id={member.id}>
                                                        {name}
                                                    </MenuItem>
                                                );
                                            }) : null
                                        }
                                    </Select>
                                </FormControl>
                            </div>
                            <div className="taskModal__role">
                                <FormControl fullWidth>
                                    <InputLabel id="demo-simple-select-label">Priority</InputLabel>
                                    <Select
                                        labelId="demo-simple-select-label"
                                        id="demo-simple-select"
                                        value={role}
                                        label="Priority"
                                        onChange={handleRoleSelection}
                                    >
                                        <MenuItem value={"USER"}>USER</MenuItem>
                                        <MenuItem value={"TEAM_LEADER"}>TEAM_LEADER</MenuItem>
                                        <MenuItem value={"ADMIN"}>ADMIN</MenuItem>
                                    </Select>
                                </FormControl>
                            </div>
                        </div>
                        <div className="roleEditModal__buttons">
                            <div className="roleEditModal__btn add_btn" onClick={handleEditButton}>Edit</div>
                            <div className="roleEditModal__btn" onClick={closeModal} >Cancel</div>
                        </div>
                    </div>
                </div>
            </Box>
        </Modal>
    );
}

export default RoleEditModal;