import React from 'react';
import './BasicGroupListItem.scss'

const BasicGroupListItem = (props) => {
    return (
        <div className='basicGroupItem'>
            <div className="basicGroupItem__block">
                <div className="basicGroupItem__image">
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d={props.path}/></svg>
                    <span>{props.iconText}</span>
                </div>
                <div className="basicGroupItem__title">
                    {props.title}
                </div>
            </div>
            <div className="basicGroupItem__count">
                {props.count}
            </div>
        </div>
    );
};

export default BasicGroupListItem;