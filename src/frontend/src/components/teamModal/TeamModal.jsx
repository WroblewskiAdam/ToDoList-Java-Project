import React, { useRef } from 'react';
import { useState, useEffect} from 'react';
import "./TeamModal.scss"
import useOuterClick from '../../hooks/useClickOutside';
import Member from '../member/Member';
import AppUserService from '../../services/appUserService';
import TeamService from '../../services/teamService';


function TeamModal(props) {
    const [title, setTitle] = useState("");
    const [users, setUsers] = useState([]);
    const [selectedUsers, setSelectedUsers] = useState([]);

    let modalRef = useRef();

    useEffect(() => {
        AppUserService.getAllUsers().then(data=>{
            setUsers(data)
        });
    }, []);

    const handleTitleChange = (e) =>{
        setTitle(e.target.value);
    }

    const handleSelectUser = (userId) => {
        if (!selectedUsers.includes(userId)) {
            setSelectedUsers([...selectedUsers, userId]);
        } else {
            setSelectedUsers(
                selectedUsers.filter((selectedUserId) => {
                    return selectedUserId !== userId;
                })
            );
        }
    }

    const closeModal = () =>{
        setTitle("");
        props.setModalState(false);
    }

    const handleAddButton = () => {
        TeamService.createTeam(title, selectedUsers[0], selectedUsers);

        props.updateTeam();

        closeModal();
    }
    
    useOuterClick(modalRef, closeModal);

    let modalClass = "teamModal" + (props.modalState? "" : " hide");

    return (
        <div className={modalClass}>
            <div ref={modalRef} className="teamModal__container" onPressOut={closeModal}>
                <div className="teamModal__title">
                    Add Team
                </div>
                <div className="teamModal__block">
                    <div className="teamModal__input">
                        <input type="text" value={title} placeholder='Team name' onChange={handleTitleChange}/>
                    </div>
                    <div className="teamModal__users">
                        {users.map((user, i) => {
                            return(
                                <Member key={user.id} value={user.id} firstName={user.firstName} lastName={user.lastName} handleSelectUser={handleSelectUser}/>
                            )
                        })}
                    </div>
                    <div className="teamModal__buttons">
                        <div className="teamModal__btn add_btn" onClick={handleAddButton}>Add</div>
                        <div className="teamModal__btn" onClick={closeModal} >Cancel</div>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default TeamModal;