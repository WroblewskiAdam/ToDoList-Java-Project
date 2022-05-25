import React, { useEffect, useState } from 'react';
import AppUserService from '../../services/appUserService';

const UserInfo = (props) => {
    const [firstName, setFirstName] = useState("");
    const [lastName, setLastName] = useState("");
    const [email, setEmail] = useState("");
    const [image, setImage] = useState(undefined);

    useEffect(() => {
        AppUserService.getUser().then((res) => {
            setFirstName(res.data.firstName);
            setLastName(res.data.lastName);
            setEmail(res.data.email);
            setImage(res.data.image);

            console.log(res.data.image);
        });
    }, []);

    return (
        <div className="userInfo">
            <div className="userInfo__block">
                <img src="" alt="" className="userInfo__block-image" />
            </div>
            <div className="userInfo__block">
                <div className="userInfo__block-text">{firstName}</div>
                <div className="userInfo__block-text">{lastName}</div>
                <div className="userInfo__block-text">{email}</div>
            </div>
        </div>
    );
}

export default UserInfo;