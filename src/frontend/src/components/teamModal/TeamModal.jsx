import React, { useRef } from 'react';
import { useState, useEffect} from 'react';
import "./TeamModal.scss"
import AppUserService from '../../services/appUserService';
import TeamService from '../../services/teamService';
import TextField from '@mui/material/TextField';
import InputLabel from '@mui/material/InputLabel';
import MenuItem from '@mui/material/MenuItem';
import FormControl from '@mui/material/FormControl';
import Select from '@mui/material/Select';
import Checkbox from '@mui/material/Checkbox';
import Modal from '@mui/material/Modal';
import Box from "@mui/material/Box";
import OutlinedInput from '@mui/material/OutlinedInput';
import ListItemText from '@mui/material/ListItemText';


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


function TeamModal(props) {
    const [title, setTitle] = useState("");
    const [members, setMembers] = useState([]);
    const [selectedMembers, setSelectedMembers] = useState([]);

    let modalRef = useRef();

    useEffect(() => {
        AppUserService.getAllUsers().then(data=>{
            data.forEach((item) => {
                if(item.id !== JSON.parse(localStorage.getItem("accessToken")).id){
                    setMembers((members) => [...members, item]);
                }
            })
        });
    }, []);

    useEffect(() => {
        console.log(props.function, " ", props.teamId)
        if(props.function === "Edit" && props.teamId){
            TeamService.getTeamName(props.teamId).then(res => {
                setTitle(res);
            });

            TeamService.getTeamMembers(props.teamId).then(res => {
                let selected = [];
                res.forEach((member) => {
                    if(member.id !== JSON.parse(localStorage.getItem("accessToken")).id){
                        selected = [...selected, member.firstName + " " + member.lastName];
                    }
                })

                setSelectedMembers(selected);
            });
        }
    }, [props.teamId, props.function])

    const handleTitleChange = (e) =>{
        setTitle(e.target.value);
    }

    const handleSelectMember = (e) => {
        const value = e.target.value;

        setSelectedMembers(
            value
        );
    }

    const closeModal = () =>{
        props.setModalState(false);
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

        if(props.function === "Add"){
            TeamService.createTeam(
                title,
                id,
                receiversIds
            ).then(res => {
                AppUserService.getTeams().then(res => {
                    props.setTeam(res);
                });
            }).catch(e => console.log(e));

            setTitle("");
            setSelectedMembers([]);
        } else if(props.function === "Edit"){
            if(props.teamId){
                console.log(receiversIds);
                TeamService.editTeam(
                    props.teamId,
                    title,
                    id,
                    receiversIds
                ).then(res => {
                    AppUserService.getTeams().then(res => {
                        props.setTeam(res);
                    });
                }).catch(e => console.log(e));
            }
        }

        closeModal();
    }

    return (
        <Modal
            open={props.modalState}
            onClose={closeModal}
            aria-labelledby="modal-modal-title"
            aria-describedby="modal-modal-description"
            className="teamModal"
        >
            <Box
                sx={{"border":"none"}}
            >
                <div ref={modalRef} className="teamModal__container">
                    <div className="teamModal__title">
                        {props.function} Team
                    </div>
                    <div className="teamModal__block">
                        <div className="teamModal__input">
                            <TextField label="Title" variant="standard" value={title} onChange={handleTitleChange} sx={{"width":"100%", "marginBottom":"15px"}}
                            inputProps={{style: {fontSize: 20, marginLeft: 13}}}
                            InputLabelProps={{style: {fontSize: 20, marginLeft: 13}}}
                            />
                        </div>
                        <div className="teamModal__users">
                            <FormControl sx={{width: "100%", "marginBottom" : "15px"}}>
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
                        <div className="teamModal__buttons">
                            <div className="teamModal__btn add_btn" onClick={handleAddButton}>{props.function}</div>
                            <div className="teamModal__btn" onClick={closeModal} >Cancel</div>
                        </div>
                    </div>
                </div>
            </Box>
        </Modal>
    );
}

export default TeamModal;