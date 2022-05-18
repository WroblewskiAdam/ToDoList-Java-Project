import { useState, useRef } from 'react';
import '../logIn/LogIn.scss';
import AuthService from '../../services/authService';
import { useHistory } from "react-router-dom";
import Spinner from '../spinner/Spinner';
import ErrorAuth from '../errorMessage/errorAuth/ErrorAuth';

const ResetPassword = (props) => {
    const [email, setEmail] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(false);
    const [errorMsg, setErrorMsg] = useState("");
    
    const checkBtn = useRef();

    let history = useHistory();
    
    const handleEmailChange = (e) => {
        setEmail(e.target.value);
    }

    const handleSubmitClick = (e) => {
        if(!email){
            setErrorMsg("Enter your email");
            setError(true);
            return;
        }

        e.preventDefault();
        setLoading(true);

        AuthService.resetPassword(email).then(
        () => {
            history.push("/login");
        }).catch((error) => {
            console.log(error.response.status);
            if(error.response.status === 403){
                setErrorMsg("Email is incorrect");
            }
            else{
                setErrorMsg("Enknown error :(((((");
            }
            setError(true);
        });

        setLoading(false);
    }

    const spinner = loading ? <Spinner/> : null;
    const errorMessage = error ? <ErrorAuth msg={errorMsg}/> : null;
    const content = !(loading) ? <View 
                                            email={email} 
                                            handleEmailChange={handleEmailChange} 
                                            checkBtn={checkBtn}
                                            handleSubmitClick={handleSubmitClick}
                                            errorMessage={errorMessage}/> : null;

    return (
        <>
            {spinner}
            {content}
        </>
    );
}

const View = ({email, handleEmailChange, checkBtn, handleSubmitClick, errorMessage}) => {
    return(
        <>
            {errorMessage}
            <div className='logIn'>
                <div className="logIn__title">Enter your email</div>
                <div className="logIn__form">
                    <div className="logIn__form-item">
                        <div className="logIn__form-icon">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d="M207.8 20.73c-93.45 18.32-168.7 93.66-187 187.1c-27.64 140.9 68.65 266.2 199.1 285.1c19.01 2.888 36.17-12.26 36.17-31.49l.0001-.6631c0-15.74-11.44-28.88-26.84-31.24c-84.35-12.98-149.2-86.13-149.2-174.2c0-102.9 88.61-185.5 193.4-175.4c91.54 8.869 158.6 91.25 158.6 183.2l0 16.16c0 22.09-17.94 40.05-40 40.05s-40.01-17.96-40.01-40.05v-120.1c0-8.847-7.161-16.02-16.01-16.02l-31.98 .0036c-7.299 0-13.2 4.992-15.12 11.68c-24.85-12.15-54.24-16.38-86.06-5.106c-38.75 13.73-68.12 48.91-73.72 89.64c-9.483 69.01 43.81 128 110.9 128c26.44 0 50.43-9.544 69.59-24.88c24 31.3 65.23 48.69 109.4 37.49C465.2 369.3 496 324.1 495.1 277.2V256.3C495.1 107.1 361.2-9.332 207.8 20.73zM239.1 304.3c-26.47 0-48-21.56-48-48.05s21.53-48.05 48-48.05s48 21.56 48 48.05S266.5 304.3 239.1 304.3z"/></svg>

                        </div>
                        <input type="email" name="email" placeholder="Your Email" value={email} className="logIn__form-input" onChange={handleEmailChange}/>
                    </div>
                    <div className="logIn__form-submit" ref={checkBtn} onClick={handleSubmitClick}>Send</div>
                </div>
            </div>
        </>
    )
}
export default ResetPassword;