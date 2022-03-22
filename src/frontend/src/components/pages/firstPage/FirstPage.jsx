import React from 'react';
import './FirstPage.scss'

function FirstPage(props) {
    return (
        <div className='firstPage'>
            <div className="firstPage__wrapper">
                <div className="firstPage__header">
                    <div className="firstPage__header-item">
                        <h1 className="firstPage__title">
                            ToDoList
                        </h1>
                        <div className="firstPage__descr">Lorem ipsum dolor sit, amet consectetur adipisicing elit. Dolorem a odio minus fuga. Quas nihil molestiae ullam pariatur fugit dolore facilis, quasi reprehenderit nobis qui veniam consequuntur autem, rerum recusandae.</div>
                    </div>
                    <div className="firstPage__header-item">
                        <img src={require("../../../images/backgrounds/20944361.jpg")} className="firstPage__image" alt="" />
                    </div>
                </div>
            </div>
        </div>
    );
}

export default FirstPage;