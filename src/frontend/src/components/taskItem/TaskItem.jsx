import React from 'react';

function TaskItem(props) {
    return (
        <div className='taskItem'>
            <div className="taskItem__title">
                {props.title}
            </div>
            <div className="taskItem__priority">
                {props.priority}
            </div>
            <div className="taskItem__deadline">
                {props.deadline}
            </div>
        </div>
    );
}

export default TaskItem;