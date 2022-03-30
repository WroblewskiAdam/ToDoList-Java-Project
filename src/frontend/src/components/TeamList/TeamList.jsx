import React from 'react';
import "./TeamList.scss";
import { useState, useEffect } from 'react';
import TeamListItem from '../TeamList-item/TeamListItem';
import TeamModal from '../teamModal/TeamModal';

function TeamList(props) {
    const [teams, setTeam] = useState([]);
    const [modalState, setModalState] = useState(false);

    const addTeam = (newTeam) => {
        setTeam((teams) => [...teams, newTeam]);
    }

    const handlePlusButton = () => {
        setModalState(true);
    }

    useEffect(() => {
        setTeam(
            [
                {
                    "icon": "😁",
                    "title": "Fanny",
                    "count": 12
                },
                {
                    "icon": "🤘",
                    "title": "Rock",
                    "count": 9
                }
            ]
        )
    }, [])

    const items = teams.map((item, i) => {
        return(
            <TeamListItem key={i} icon={item.icon} title={item.title} count={item.count} />
        )
    });

    return (
        <div className='teamList'>
            <div className="teamList__addButton" onClick={handlePlusButton}>
                +
            </div>
            <div className="teamList__block">
                {items}
            </div>
            <TeamModal 
                modalState={modalState}
                setModalState={setModalState}
                addTeam={addTeam}
            />
        </div>
    );
}

export default TeamList;