import React from 'react';
import { useState, useEffect } from 'react';
import ListItem from '../list-item/ListItem';
import './List.scss'

let mathData = [
    {
        "id": 1,
        "title": "Math",
        "important": true,
        "done": false
    },
    {
        "id": 2,
        "title": "WF",
        "important": false,
        "done": false
    },
]

let id = 3;

const List = () => {
    const [taskName, setTaskName] = useState("");
    const [data, setData] = useState([]);
    

    useEffect(() => {
        setData(mathData);
    }, [])

    const handleChange = (e) => {
        setTaskName(e.target.value);
    }

    const onSubmit = (e) => {
        mathData = [...mathData, {"id": id, "title": taskName, "important": false, "done": false}];
        setData(mathData);
        setTaskName("");
        id += 1;
    }

    const onEnter = (e) => {
        if(e.key === "Enter"){
            onSubmit();
        }
    }

    return (
        <div className="list">
            <div className="list__input">
                <input
                    type="text"
                    placeholder='Write task: '
                    className='list__input-search'
                    value={taskName}
                    onChange={handleChange}
                    onKeyPress={onEnter}
                />
                <button 
                    className='list__input-btn'
                    onClick={onSubmit}
                >
                    Add
                </button>
            </div>
            {
                data.map((oneItemData) => {
                    return(
                        <ListItem data={oneItemData} />
                    )
                })
            }
        </div>
    );
};


export default List;