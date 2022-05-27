import React, { useRef } from 'react';
import { useState, useEffect} from 'react';
import "./UserModal.scss"
import AppUserService from '../../services/appUserService';
import TeamService from '../../services/teamService';
import TextField from '@mui/material/TextField';
import Modal from '@mui/material/Modal';
import Box from "@mui/material/Box";
import Spinner from '../spinner/Spinner';

function UserModal(props) {
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [image, setImage] = useState(undefined);
    const [loading, setLoading] = useState(false);
    const [password, setPassword] = useState("");
    
    useEffect(() => {
        setFirstName(props.firstName);
        setLastName(props.lastName);
        setEmail(props.email);
        setImage(props.image);
    }, [props]);

    const handleFirstNameChange = (e) =>{
        setFirstName(e.target.value);
    }

    const handleLastNameChange = (e) => {
        setLastName(e.target.value);
    }

    const handleEmailChange = (e) => {
        setEmail(e.target.value);
    }

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    }

    const closeModal = () =>{
        props.setModalState(false);
    }

    const handleSubmitButton = () => {
        setLoading(true);
        AppUserService.editUser(firstName, lastName, image).then((res) => {
            console.log("Git");
            setLoading(false);
            props.setUpdateUserInfo((update) => !update);
        })
        closeModal();
    }

    const content = !loading? 
        <View
            modalState={props.modalState}
            closeModal={closeModal}
            firstName={firstName}
            handleFirstNameChange={handleFirstNameChange}
            lastName={lastName}
            handleLastNameChange={handleLastNameChange}
            handleSubmitButton={handleSubmitButton}
        /> : null;
    const load = loading ? <Spinner/> : null;

    return (
        <>
            {content}
            {load}
        </>
    );
}


const View = ({modalState, closeModal, firstName, handleFirstNameChange, lastName, handleLastNameChange, handleSubmitButton}) => {
    return (
        <Modal
            open={modalState}
            onClose={closeModal}
            aria-labelledby="modal-modal-title"
            aria-describedby="modal-modal-description"
            className="userModal"
        >
            <Box
                sx={{"border":"none"}}
            >
                <div className="userModal__container">
                    <div className="userModal__title">
                        Team
                    </div>
                    <div className="userModal__block">
                        <div className="userModal__input">
                            <TextField label="Frist name" variant="standard" value={firstName} onChange={handleFirstNameChange} sx={{"width":"100%", "marginBottom":"15px"}}
                            inputProps={{style: {fontSize: 20, marginLeft: 13}}}
                            InputLabelProps={{style: {fontSize: 20, marginLeft: 13}}}
                            />
                            <TextField label="Last name" variant="standard" value={lastName} onChange={handleLastNameChange} sx={{"width":"100%", "marginBottom":"15px"}}
                            inputProps={{style: {fontSize: 20, marginLeft: 13}}}
                            InputLabelProps={{style: {fontSize: 20, marginLeft: 13}}}
                            />
                            {/* <TextField label="Email" variant="standard" value={email} onChange={handleEmailChange} sx={{"width":"100%", "marginBottom":"15px"}}
                            inputProps={{style: {fontSize: 20, marginLeft: 13}}}
                            InputLabelProps={{style: {fontSize: 20, marginLeft: 13}}}
                            /> */}
                            {/* <TextField label="Password" variant="standard" type="password" value={password} onChange={handlePasswordChange} sx={{"width":"100%", "marginBottom":"15px"}}
                            inputProps={{style: {fontSize: 20, marginLeft: 13}}}
                            InputLabelProps={{style: {fontSize: 20, marginLeft: 13}}}
                            /> */}
                        </div>
                        <div className="userModal__buttons">
                            <div className="userModal__btn add_btn" onClick={handleSubmitButton}>Submit</div>
                            <div className="userModal__btn" onClick={closeModal} >Cancel</div>
                        </div>
                    </div>
                </div>
            </Box>
        </Modal>
    )
}
export default UserModal;