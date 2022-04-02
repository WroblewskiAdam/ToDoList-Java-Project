import React, { useRef } from 'react';
import { useState } from 'react';
import "./TeamListItem.scss";
import useOuterClick from '../../hooks/useClickOutside';


const TeamListItem = (props) => {
    const [option, setOption] = useState(false);

    const handleOptionClick = () => {
        setOption((option) => !option);
    }

    const optionRef = useRef();

    useOuterClick(optionRef, () => {
        setOption(false);
    });

    return (
        <div className='teamItem'>
            <div className="teamItem__icon">
                <span>
                    {props.icon}
                </span>
            </div>
            <div className="teamItem__title">{props.title}</div>
            <div className="teamItem__count">{props.count}</div>
            <div className="teamItem__more" onClick={handleOptionClick}>...</div>
            {/* <div ref={optionRef} className={"teamItem__options" + (option ? "" : " hide")}> */}
            <div ref={optionRef} className={"teamItem__options" + (option ? "" : " hide")}>               
                <div className="teamItem__btn teamItem__edit">
                    Edit
                </div>
                <div className="teamItem__btn teamItem__delete" >
                    Delete
                </div>
            </div>
        </div>
    );
};

export default TeamListItem;