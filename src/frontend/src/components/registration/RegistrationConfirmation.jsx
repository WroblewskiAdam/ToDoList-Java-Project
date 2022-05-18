import { useState, useRef, useEffect } from 'react';
import '../logIn/LogIn.scss';
import AuthService from '../../services/authService';
import { useHistory } from "react-router-dom";
import Spinner from '../spinner/Spinner';
import ErrorAuth from '../errorMessage/errorAuth/ErrorAuth';

const RegistrationConfirmation = (props) => {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(false);
    const [errorMsg, setErrorMsg] = useState("");
    
    const checkBtn = useRef();

    let history = useHistory();

    useEffect(() => {
        const queryParams = new URLSearchParams(window.location.search);

        const token = queryParams.get('token');
        
        setLoading(true);

        AuthService.confirmUser(token)
            .catch((error) => {
                console.log(error.response.status);
                if(error.response.status === 403){
                    setErrorMsg(error.response.data);
                }
                else{
                    setErrorMsg("Enknown error :(((((");
                }
                setError(true);
            });
            
        setLoading(false);
        
    }, [])

    const handleSubmitClick = (e) => {
        e.preventDefault();
        setLoading(true);

        history.push("/login");
    }

    const spinner = loading ? <Spinner/> : null;
    const errorMessage = error ? <ErrorAuth msg={errorMsg}/> : null;
    const content = !(loading) ? <View 
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

const View = ({checkBtn, handleSubmitClick, errorMessage}) => {
    return(
        <>
            {errorMessage}
            <div className='logIn'>
                <div className="logIn__title">Your account is confirmed</div>
                <div className="logIn__form">
                    <div className="logIn__form-submit" ref={checkBtn} onClick={handleSubmitClick}>Log In</div>
                </div>
            </div>
        </>
    )
}

export default RegistrationConfirmation;

