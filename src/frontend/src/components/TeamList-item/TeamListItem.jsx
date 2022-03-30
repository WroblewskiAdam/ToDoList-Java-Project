import React from 'react';
import "./TeamListItem.scss";


const TeamListItem = (props) => {
    return (
        <div className='teamItem'>
            <div className="teamItem__icon">
                <span>
                    {props.icon}
                </span>
            </div>
            <div className="teamItem__title">{props.title}</div>
            <div className="teamItem__count">{props.count}</div>
        </div>
    );
};

export default TeamListItem;