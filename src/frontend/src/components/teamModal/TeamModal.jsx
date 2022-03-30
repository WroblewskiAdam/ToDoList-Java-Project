import React from 'react';
import Picker from 'emoji-picker-react';
import { useState } from 'react';
import "./TeamModal.scss"

const people = [
    "Tomek",
    "Bartek",
    "Barbara",
    "Ann",
    "Tomek",
    "Bartek",
    "Barbara",
    "Ann",
    "Tomek",
    "Bartek",
    "Barbara",
    "Ann"
]

function TeamModal({ modalState, setModalState, addTeam}) {
    const [title, setTitle] = useState("");
    const [emoji, setEmoji] = useState("😁");
    const [users, setUsers] = useState([]);
    const [emojiPicker, setEmojiPicker] = useState(false);
    const [selectedUsers, setSelectedUsers] = useState([]);

    const handleTitleChange = (e) =>{
        setTitle(e.target.value);
    }

    const onEmojiClick = (event, emojiObject) => {
        setEmoji(emojiObject.emoji);
        pickEmoji();
    }

    const pickEmoji = () => {
        setEmojiPicker((emojiPicker) => !emojiPicker);
    }

    const handleSelectUser = (event) => {
        const userId = event.target.value;
        console.log(userId);

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
        setEmoji("😁");
        setModalState((modalState) => !modalState);
    }

    const handleAddButton = () => {
        const data = {
            "icon": emoji,
            "title": title,
            "count": selectedUsers.length
        }

        addTeam(data);

        closeModal();
    }

    // let emojiIcon = emoji? emoji: "😁";
    let modalClass = "teamModal" + (modalState? "" : " hide");

    return (
        <div className={modalClass}>
            <div className="teamModal__container" onPressOut={closeModal}>
                <div className="teamModal__title">
                    Add Team
                </div>
                <div className="teamModal__block">
                    <div className="teamModal__input">
                        <div className="teamModal__emoji">
                            <span onClick={pickEmoji}>{emoji}</span>
                            <div className={"teamModal__emoji-picker" + (emojiPicker?"":" hide")}>
                                <Picker pickerStyle={{ width: '320px' }} disableSearchBar={true} disableSkinTonePicker={true} disableAutoFocus={true} onEmojiClick={onEmojiClick} />
                            </div>
                        </div>
                        <input type="text" value={title} placeholder='Team name' onChange={handleTitleChange}/>
                    </div>
                    <ul className="teamModal__users">
                        {people.map((user, i) => {
                            return(
                                <li key={i} className="teamModal__users-item">
                                    <input type="checkbox" value={i} onChange={handleSelectUser}/>
                                    <div className="teamModal__users-name">
                                        {user}
                                    </div>
                                    <div className="teamModal__users-name">
                                        {user}
                                    </div>
                                </li>
                            )
                        })}
                    </ul>
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