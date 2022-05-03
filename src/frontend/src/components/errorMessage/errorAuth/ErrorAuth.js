import React from 'react';
import "./ErrorAuth.scss";

const ErrorAuth = (props) => {
    return (
        <div>
            <div className="errorAuth">{props.msg}</div>
        </div>
    );
};

export default ErrorAuth;