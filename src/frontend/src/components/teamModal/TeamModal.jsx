import React, { useRef } from 'react';
import { useState} from 'react';
import "./TeamModal.scss"
import useOuterClick from '../../hooks/useClickOutside';
import User from '../user/User';

const people = [
    {
        "id": 1,
        "firstName": "Tom",
        "lastName": "Fire",
    },
    {
        "id": 2,
        "firstName": "Bob",
        "lastName": "Marley",
    },
    {
        "id": 3,
        "firstName": "Megan",
        "lastName": "Williams",
    },
    {
        "id": 4,
        "firstName": "Stephanie",
        "lastName": "Brooks",
    },
    {
        "id": 5,
        "firstName": "John",
        "lastName": "Hickman",
    },
    {
        "id": 6,
        "firstName": "Amanda",
        "lastName": "Gardner",
    },
    {
        "id": 7,
        "firstName": "Stephanie",
        "lastName": "Brooks",
    },
    {
        "id": 8,
        "firstName": "John",
        "lastName": "Hickman",
    },
    {
        "id": 9,
        "firstName": "Amanda",
        "lastName": "Gardner",
    },
]

function TeamModal(props) {
    const [title, setTitle] = useState("");
    const [users, setUsers] = useState([]);
    const [selectedUsers, setSelectedUsers] = useState([]);

    let modalRef = useRef();

    const handleTitleChange = (e) =>{
        setTitle(e.target.value);
    }

    const handleSelectUser = (event) => {
        // const userId = event.target.value;

        // if (!selectedUsers.includes(userId)) {
        //     setSelectedUsers([...selectedUsers, userId]);
        // } else {
        //     setSelectedUsers(
        //         selectedUsers.filter((selectedUserId) => {
        //             return selectedUserId !== userId;
        //         })
        //     );
        // }
    }

    const closeModal = () =>{
        setTitle("");
        props.setModalState(false);
    }

    const handleAddButton = () => {
        const data = {
            "id": 13,
            "title": title,
        }

        props.addTeam(data);

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
                        {people.map((user, i) => {
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