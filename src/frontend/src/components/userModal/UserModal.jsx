import React from 'react';
import { useState, useEffect} from 'react';
import "./UserModal.scss"
import AppUserService from '../../services/appUserService';
import TextField from '@mui/material/TextField';
import Modal from '@mui/material/Modal';
import Box from "@mui/material/Box";
import Spinner from '../spinner/Spinner';
import ErrorAuth from '../errorMessage/errorAuth/ErrorAuth';

function UserModal(props) {
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [, setEmail] = useState("");
    const [image, setImage] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(false);
    const [errorMsg, setErrorMsg] = useState("");

    useEffect(() => {
        setFirstName(props.firstName);
        setLastName(props.lastName);
        setEmail(props.email);
    }, [props]);

    const handleFirstNameChange = (e) =>{
        setFirstName(e.target.value);
    }

    const handleLastNameChange = (e) => {
        setLastName(e.target.value);
    }

    const handleImageChange = (e) => {
        setImage(e.target.files[0]);
    }

    const closeModal = () =>{
        props.setModalState(false);
    }

    const handleSubmitButton = () => {
        setLoading(true);
        if(image){
            AppUserService.editWithImageUser(firstName, lastName, image).then((res) => {
                setLoading(false);
                setError(false);
                setImage(null);
                props.setUpdateUserInfo((update) => !update);
            }).catch((error) => {
                setLoading(false);
                setImage(null);
                console.log(error.response);
                if(error.response.status === 500){
                    setErrorMsg("Image is too large");
                }
                else{
                    setErrorMsg("Enknown error :(((((");
                }
                setError(true);
            });
        }
        else{
            AppUserService.editUser(firstName, lastName).then((res) => {
                setLoading(false);
                setError(false);
                props.setUpdateUserInfo((update) => !update);
            })
        }
        closeModal();
    }
    
    const load = loading ? <Spinner/> : null;
    const errorMessage = error ? <ErrorAuth msg={errorMsg}/> : null;
    const content = !loading? 
        <View
            modalState={props.modalState}
            closeModal={closeModal}
            firstName={firstName}
            handleFirstNameChange={handleFirstNameChange}
            lastName={lastName}
            handleLastNameChange={handleLastNameChange}
            handleSubmitButton={handleSubmitButton}
            handleImageChange={handleImageChange}
            errorMessage={errorMessage}
        /> : null;

    return (
        <>
            {content}
            {load}
        </>
    );
}


const View = ({errorMessage, modalState, closeModal, firstName, handleFirstNameChange, lastName, handleLastNameChange, handleSubmitButton, handleImageChange}) => {
    return (
        <>
            {errorMessage}
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
                            Edit User
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
                                <div className="userModal__file-input">
                                    <div className="userModal__file-input-icon">
                                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d="M447.1 32h-384C28.64 32-.0091 60.65-.0091 96v320c0 35.35 28.65 64 63.1 64h384c35.35 0 64-28.65 64-64V96C511.1 60.65 483.3 32 447.1 32zM111.1 96c26.51 0 48 21.49 48 48S138.5 192 111.1 192s-48-21.49-48-48S85.48 96 111.1 96zM446.1 407.6C443.3 412.8 437.9 416 432 416H82.01c-6.021 0-11.53-3.379-14.26-8.75c-2.73-5.367-2.215-11.81 1.334-16.68l70-96C142.1 290.4 146.9 288 152 288s9.916 2.441 12.93 6.574l32.46 44.51l93.3-139.1C293.7 194.7 298.7 192 304 192s10.35 2.672 13.31 7.125l128 192C448.6 396 448.9 402.3 446.1 407.6z"/></svg>
                                    </div>
                                    <input type="file" id="file" onChange={handleImageChange} accept='image/*'/>
                                </div>
                            </div>
                            <div className="userModal__buttons">
                                <div className="userModal__btn add_btn" onClick={handleSubmitButton}>Submit</div>
                                <div className="userModal__btn" onClick={closeModal} >Cancel</div>
                            </div>
                        </div>
                    </div>
                </Box>
            </Modal>
        </>
    )
}
export default UserModal;