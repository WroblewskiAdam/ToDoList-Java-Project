import React from 'react';
import './FirstPage.scss'
import { useHistory } from "react-router-dom";
import { NavLink} from 'react-router-dom';
import homeImage from "../../../images/backgrounds/20944361.jpg";

function FirstPage(props) {
    let history = useHistory();

    return (
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
                    <img src={homeImage} className="firstPage__image" alt="" />
                </div>
            </div>
        </div>
    );
}

export default FirstPage;