import React, { useRef } from 'react';
import { useState, useEffect} from 'react';
import "./TeamModal.scss"
import useOuterClick from '../../hooks/useClickOutside';
import User from '../user/User';
import AppUserService from '../../services/appUserService';
import TeamService from '../../services/teamService';
// const people = [
//     {
//         "id": 1,
//         "firstName": "Tom",
//         "lastName": "Fire",
//     },
//     {
//         "id": 2,
//         "firstName": "Bob",
//         "lastName": "Marley",
//     },
//     {
//         "id": 3,
//         "firstName": "Megan",
//         "lastName": "Williams",
//     },
//     {
//         "id": 4,
//         "firstName": "Stephanie",
//         "lastName": "Brooks",
//     },
//     {
//         "id": 5,
//         "firstName": "John",
//         "lastName": "Hickman",
//     },
//     {
//         "id": 6,
//         "firstName": "Amanda",
//         "lastName": "Gardner",
//     },
//     {
//         "id": 7,
//         "firstName": "Stephanie",
//         "lastName": "Brooks",
//     },
//     {
//         "id": 8,
//         "firstName": "John",
//         "lastName": "Hickman",
//     },
//     {
//         "id": 9,
//         "firstName": "Amanda",
//         "lastName": "Gardner",
//     },
// ]

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
                                <User key={user.id} value={user.id} firstName={user.firstName} lastName={user.lastName} handleSelectUser={handleSelectUser}/>
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