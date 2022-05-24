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

    const handleDeleteClick = () => {
        console.log("Delete: ", props.id);
        TeamService.deleteTeam(props.id);
        window.location.reload();
    }

    const handleEditClick = () => {
        console.log("Edit: ", props.id);
        props.setSelectedTeam(props.id);
        props.setModalFunction("Edit");
        props.setModalState(true);
    }

    
    useOuterClick(optionRef, () => {
        setOption(false);
    });

    const btn = JSON.parse(localStorage.getItem("accessToken")).role !== "[USER]"?  
    <>
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
    </> : null;

    const handleTeamItemClick = (e) => {
        if(btn){
            console.log("btn")
            if((!optionRef.current.contains(e.target) && !moreButtonRef.current.contains(e.target)) || props.reload){
                if(props.id){
                    props.setTitle(props.title);
                    props.setTeamId(props.id);
                }
            }
        }
        else{
            if(props.id){
                props.setTitle(props.title);
                props.setTeamId(props.id);
            }
        }
    }

    return (
        <div className='teamItem' onClick={handleTeamItemClick}>
            <div className="teamItem__image">
                <img src={logo} alt="" />
            </div>
            <div className="teamItem__title">
                {props.title}
            </div>
            {btn}
            
        </div>
    );
}
export default TeamItem;