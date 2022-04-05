import React from 'react';
import { useState, useEffect } from 'react';
import './ListItem.scss'

const ListItem = (props) => {
    const [data, setData] = useState({});
    const [important, setImportant] = useState(props.important);
    const [done, setDone] = useState(props.done);

    useEffect(() => {
        setData(props.data);
    }, []);

    const importantClicked = () => {
        setImportant((important) => !important);
    }

    const doneClicked = () => {
        setDone((done) => !done);
    }

    const importantStyle = important? "item__block-important important-clicked": "item__block-important";
    const doneStyle = done? "item__block-done done-clicked": "item__block-done"
    return (
        <div className='item'>
            <div className="item__block">
                <div className="item__name">
                    {data.title}
                </div>
            </div>
            <div className="item__block">
                <div className={importantStyle} onClick={importantClicked}>
                    <i className='fa fa-star'></i>
                </div>
                <div className={doneStyle} onClick={doneClicked}>
                    <i className="fa fa-check"></i>
                </div>
            </div>
        </div>
    );
};

export default ListItem;