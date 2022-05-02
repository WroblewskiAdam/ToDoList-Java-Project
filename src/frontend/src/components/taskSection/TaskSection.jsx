import React, { useState, useEffect } from 'react';

function TaskSection(props) {
    const [tasks, setTasks] = useState(null);

    useEffect(() => {
        
    }, []);

    return (
        <div className='taskSection'>
            <div className="taskSection__title">
                {props.title}
            </div>
            <div className="taskSection__wrapper">
                <div className="taskSection__filters">

                </div>
                <div className="taskSection__body">
                    
                </div>
            </div>
        </div>
    );
}

export default TaskSection;