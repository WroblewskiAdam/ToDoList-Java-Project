import React from 'react';
import './Member.scss'
import { useState } from 'react';


function Member(props) {
    const [userState, setUserState] = useState(props.state);

    const handleStateClick = () => {
        setUserState((userState) => !userState);
        props.handleSelectUser(props.value);
    }

    let userStateClass = "user__state" + (userState ? "" : " check_mark");

    return (
        <div className="user">
            <div className={userStateClass} onClick={handleStateClick}></div>
            <div className="user__name">{props.firstName}</div>
            <div className="user__name">{props.lastName}</div>
        </div>
    );
}

export default Member;