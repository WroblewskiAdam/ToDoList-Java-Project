import { useState, useRef, useEffect } from 'react';
import '../logIn/LogIn.scss';
import AuthService from '../../services/authService';
import { useHistory } from "react-router-dom";
import Spinner from '../spinner/Spinner';
import ErrorAuth from '../errorMessage/errorAuth/ErrorAuth';

const ResetPasswordEditor = (props) => {
    const [password, setNewPassword] = useState("");
    const [repeatedPassword, setRepeatedNewPassword] = useState("");
    const [userId, setUserId] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(false);
    const [errorMsg, setErrorMsg] = useState("");
    
    const checkBtn = useRef();

    let history = useHistory();

    useEffect(() => {
        const queryParams = new URLSearchParams(window.location.search);

        const token = queryParams.get('token');
        
        AuthService.getUserByToken(token).then(
            res => {setUserId(res)}
        )
    }, [])

    const handlePasswordChange = (e) => {
        setNewPassword(e.target.value);
    }
    
    const handleRepeatedPasswordChange = (e) => {
        setRepeatedNewPassword(e.target.value);
    }

    const handleSubmitClick = (e) => {
        if(!password){
            setErrorMsg("Enter a new password");
            setError(true);
            return;
        }

        if(!repeatedPassword){
            setErrorMsg("Repeat new password");
            setError(true);
            return;
        }

        if(password !== repeatedPassword){
            setErrorMsg("Passwords must be the same");
            setError(true);
            return;
        }

        e.preventDefault();
        setLoading(true);

        AuthService.resetPasswordEditor(password, userId).then(
        () => {
            history.push("/login");
        }).catch((error) => {
            console.log(error.response.status);
            if(error.response.status === 403){
                setErrorMsg("????");
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
                                            password={password} 
                                            handlePasswordChange={handlePasswordChange} 
                                            repeatedPassword={repeatedPassword}
                                            handleRepeatedPasswordChange={handleRepeatedPasswordChange}
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

const View = ({password, handlePasswordChange, repeatedPassword, handleRepeatedPasswordChange, checkBtn, handleSubmitClick, errorMessage}) => {
    return(
        <>
            {errorMessage}
            <div className='logIn'>
                <div className="logIn__title">Reset Password</div>
                <div className="logIn__form">
                    <div className="logIn__form-item">
                        <div className="logIn__form-icon">
                        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d="M282.3 343.7L248.1 376.1C244.5 381.5 238.4 384 232 384H192V424C192 437.3 181.3 448 168 448H128V488C128 501.3 117.3 512 104 512H24C10.75 512 0 501.3 0 488V408C0 401.6 2.529 395.5 7.029 391L168.3 229.7C162.9 212.8 160 194.7 160 176C160 78.8 238.8 0 336 0C433.2 0 512 78.8 512 176C512 273.2 433.2 352 336 352C317.3 352 299.2 349.1 282.3 343.7zM376 176C398.1 176 416 158.1 416 136C416 113.9 398.1 96 376 96C353.9 96 336 113.9 336 136C336 158.1 353.9 176 376 176z"/></svg>
                        </div>
                        <input type="password" name="password" placeholder="New Password" value={password} className="logIn__form-input" onChange={handlePasswordChange}/>
                    </div>
                    <div className="logIn__form-item">
                        <div className="logIn__form-icon">
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 512 512"><path d="M282.3 343.7L248.1 376.1C244.5 381.5 238.4 384 232 384H192V424C192 437.3 181.3 448 168 448H128V488C128 501.3 117.3 512 104 512H24C10.75 512 0 501.3 0 488V408C0 401.6 2.529 395.5 7.029 391L168.3 229.7C162.9 212.8 160 194.7 160 176C160 78.8 238.8 0 336 0C433.2 0 512 78.8 512 176C512 273.2 433.2 352 336 352C317.3 352 299.2 349.1 282.3 343.7zM376 176C398.1 176 416 158.1 416 136C416 113.9 398.1 96 376 96C353.9 96 336 113.9 336 136C336 158.1 353.9 176 376 176z"/></svg>
                        </div>
                        <input type="password" name="repeatedPassword" value={repeatedPassword} className="logIn__form-input" placeholder="Repeat New Password" onChange={handleRepeatedPasswordChange}/>
                    </div>
                    <div className="logIn__form-submit" ref={checkBtn} onClick={handleSubmitClick}>Submit</div>
                </div>
            </div>
        </>
    )
}
export default ResetPasswordEditor;