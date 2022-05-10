import React from 'react';
import { useState, useRef } from 'react';
import "./TeamItem.scss"
import logo from '../../images/picture/team.png'
import useOuterClick from '../../hooks/useClickOutside';
import TeamService from '../../services/teamService';


function TeamItem(props) {
    const [option, setOption] = useState(false);
    const optionRef = useRef();
    const moreButtonRef = useRef()

    const handleOptionClick = () => {
        setOption((option) => !option);
    }

    const handleTeamItemClick = (e) => {
        if((!optionRef.current.contains(e.target) && !moreButtonRef.current.contains(e.target)) || props.reload){
            if(props.id){
                TeamService.getTeamTasks(props.id).then(res => {
                    props.setTasks(res);
                    props.setTitle(props.title);
                    props.setTeamId(props.id);
                });
            }
        }
    }

    const handleDeleteClick = () => {
        console.log("Delete: ", props.id);
    }

    const handleEditClick = () => {
        console.log("Edit: ", props.id);
    }

    
    useOuterClick(optionRef, () => {
        setOption(false);
    });

    return (
        <div className='teamItem' onClick={handleTeamItemClick}>
            <div className="teamItem__image">
                <img src={logo} alt="" />
            </div>
            <div className="teamItem__title">
                {props.title}
            </div>
            <div ref={moreButtonRef} className="teamItem__more" onClick={handleOptionClick}>
                ...
            </div>
            <div ref={optionRef} className={"teamItem__options" + (option ? "" : " hide")}>               
                <div className="teamItem__btn teamItem__edit" onClick={handleEditClick}>
                    Edit
                </div>
                <div className="teamItem__btn teamItem__delete" onClick={handleDeleteClick}>
                    Delete
                </div>
            </div>
        </div>
    );
}
export default TeamItem;