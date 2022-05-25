import React, { useState } from 'react';
import './FirstPage.scss'
import { NavLink} from 'react-router-dom';
import homeImage from "../../images/backgrounds/20944361.jpg";
import Spinner from '../../components/spinner/Spinner';

function FirstPage(props) {
    const [loading, setLoading] = useState(false);

    const handleImageLoading = () => {
        setLoading(true);
    }

    const spinner = loading ? <Spinner/> : null;
    const content = !loading ? <View homeImage={homeImage} handleImageLoading={handleImageLoading} />: null;

    return (
        <>
            {spinner}
            {content}
        </>
    );
}


const View = ({homeImage, handleImageLoading}) => {
    return(
        <div className='firstPage'>
            <div className="firstPage__wrapper">
                <div className="firstPage__item firstPage__item-border">
                    <h1 className="firstPage__title">
                        ToDoList
                    </h1>
                    <div className="firstPage__descr">Welcome to mega, super fantastic inovation application 2022. Before you start you need: </div>
                    <div className="firstPage__buttons">
                        <NavLink exact className="firstPage__buttons-item" to="/login">Login</NavLink>
                        <NavLink exact className="firstPage__buttons-item"  to="/registration">SignUp</NavLink>
                    </div>
                </div>
                <div className="firstPage__item">
                    <img src={homeImage} className="firstPage__image" alt="" onLoad={handleImageLoading}/>
                </div>
            </div>
        </div>
    )
}

export default FirstPage;