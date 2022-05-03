import React, { useState, useEffect } from 'react';
import TaskItem from '../taskItem/TaskItem';
import "./TaskSection.scss";

const Tasks = [
    {
        "id": 19,
        "title": "zadanie1",
        "description": "easy",
        "deadline": "2022-05-02T10:55:00",
        "creationTime": "2022-05-02T22:48:39",
        "ticked": false,
        "priority": "GREEN"
    },
    {
        "id": 20,
        "title": "zadanie2",
        "description": "medium",
        "deadline": "2022-06-02T10:55:00",
        "creationTime": "2022-07-02T22:48:39",
        "ticked": false,
        "priority": "RED"
    },
    {
        "id": 21,
        "title": "zadanie3",
        "description": "difficult",
        "deadline": "2022-06-02T10:55:00",
        "creationTime": "2022-002T22:48:39",
        "ticked": false,
        "priority": "YELLOW"
    }
]

function TaskSection(props) {
    const [tasks, setTasks] = useState(null);

    useEffect(() => {
        setTasks(Tasks);
    }, []);

    return (
        <div className='taskSection'>
            <div className="taskSection__title">
                Title
            </div>
            <div className="taskSection__wrapper">
                <div className="taskSection__filters">

                </div>
                <div className="taskSection__body">
                    {
                        Tasks.map((item) => {
                            return (
                                <TaskItem 
                                    key={item.id}
                                    id={item.id}
                                    title={item.title}
                                    description={item.description}
                                    deadline={item.deadline}
                                    creationTime={item.creationTime}
                                    priority={item.priority}
                                />
                            );
                        })
                    }
                </div>
            </div>
        </div>
    );
}

export default TaskSection;