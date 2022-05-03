import { useState, useRef } from 'react';
import './LogIn.scss';
import AuthService from '../../services/authService';
import { useHistory } from "react-router-dom";
import Spinner from '../spinner/Spinner';
import ErrorAuth from '../errorMessage/errorAuth/ErrorAuth';

const LogIn = (props) => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(false);
    const [errorMsg, setErrorMsg] = useState("");
    
    const checkBtn = useRef();

    let history = useHistory();

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    }
    
    const handleEmailChange = (e) => {
        setEmail(e.target.value);
    }

    const handleSubmitClick = (e) => {
        if(!password){
            setErrorMsg("Give me a password");
            setError(true);
            return;
        }

        if(!email){
            setErrorMsg("Give me your email");
            setError(true);
            return;
        }

        e.preventDefault();
        setLoading(true);

        AuthService.login(email, password).then(
        () => {
            history.push("/home");
        }).catch((error) => {
            console.log(error.response.status);
            if(error.response.status === 403){
                setErrorMsg("Password or email is incorrect :(");
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
                                            password={password}
                                            handlePasswordChange={handlePasswordChange}
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

const View = ({email, handleEmailChange, password, handlePasswordChange, checkBtn, handleSubmitClick, errorMessage}) => {
    return(
        <>
            {errorMessage}
            <div className='logIn'>
                <div className="logIn__title">Log In</div>
                <div className="logIn__form">
                    <div className="logIn__form-item">
                        <div className="logIn__form-icon">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d="M207.8 20.73c-93.45 18.32-168.7 93.66-187 187.1c-27.64 140.9 68.65 266.2 199.1 285.1c19.01 2.888 36.17-12.26 36.17-31.49l.0001-.6631c0-15.74-11.44-28.88-26.84-31.24c-84.35-12.98-149.2-86.13-149.2-174.2c0-102.9 88.61-185.5 193.4-175.4c91.54 8.869 158.6 91.25 158.6 183.2l0 16.16c0 22.09-17.94 40.05-40 40.05s-40.01-17.96-40.01-40.05v-120.1c0-8.847-7.161-16.02-16.01-16.02l-31.98 .0036c-7.299 0-13.2 4.992-15.12 11.68c-24.85-12.15-54.24-16.38-86.06-5.106c-38.75 13.73-68.12 48.91-73.72 89.64c-9.483 69.01 43.81 128 110.9 128c26.44 0 50.43-9.544 69.59-24.88c24 31.3 65.23 48.69 109.4 37.49C465.2 369.3 496 324.1 495.1 277.2V256.3C495.1 107.1 361.2-9.332 207.8 20.73zM239.1 304.3c-26.47 0-48-21.56-48-48.05s21.53-48.05 48-48.05s48 21.56 48 48.05S266.5 304.3 239.1 304.3z"/></svg>

                        </div>
                        <input type="email" name="email" placeholder="Your Email" value={email} className="logIn__form-input" onChange={handleEmailChange}/>
                    </div>
                    <div className="logIn__form-item">
                        <div className="logIn__form-icon">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d="M282.3 343.7L248.1 376.1C244.5 381.5 238.4 384 232 384H192V424C192 437.3 181.3 448 168 448H128V488C128 501.3 117.3 512 104 512H24C10.75 512 0 501.3 0 488V408C0 401.6 2.529 395.5 7.029 391L168.3 229.7C162.9 212.8 160 194.7 160 176C160 78.8 238.8 0 336 0C433.2 0 512 78.8 512 176C512 273.2 433.2 352 336 352C317.3 352 299.2 349.1 282.3 343.7zM376 176C398.1 176 416 158.1 416 136C416 113.9 398.1 96 376 96C353.9 96 336 113.9 336 136C336 158.1 353.9 176 376 176z"/></svg>
                        </div>
                        <input type="password" name="password" value={password} className="logIn__form-input" placeholder="Your Password" onChange={handlePasswordChange}/>
                    </div>
                    <div className="logIn__form-submit" ref={checkBtn} onClick={handleSubmitClick}>Submit</div>
                    <div className="logIn__form-reset">Forgot your password?</div>
                </div>
            </div>
        </>
    )
}
export default LogIn;