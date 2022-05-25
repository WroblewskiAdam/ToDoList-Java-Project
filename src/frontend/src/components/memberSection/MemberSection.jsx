import React, { useState, useEffect } from 'react';
import MemberItem from '../memberItem/MemberItem';
import "./MemberSection.scss"
import TextField from '@mui/material/TextField';
import TeamService from '../../services/teamService';

function MemberSection(props) {
    const [input, setInput] = useState("");
    const [members, setMembers] = useState([]);

    useEffect(() => {
        if(props.teamId){
            TeamService.getTeamMembers(props.teamId).then(res => {
                setMembers(res);
            });
        }
    }, [props.teamId]);

    const handleInputChange = (e) => {
        setInput(e.target.value);
    }

    return (
        <div className="memberSection">
            <div className="memberSection__input">
                <TextField 
                    id="outlined-search"
                    label="Search field"
                    type="search"
                    sx={{"width" : "100%", "marginRight": "15px"}}
                    value={input}
                    onChange={handleInputChange}
                />
            </div>
            <div className="memberSection__block">
                {
                    members.filter((member) => {
                        return input === "" || ((member.firstName.toLowerCase() + " " + member.lastName.toLowerCase() + " " + member.firstName.toLowerCase()).indexOf(input.toLowerCase()) !== -1);
                    }).map((item) => {
                        return(
                            <MemberItem key={item.id} teamId={props.teamId} id={item.id} firstName={item.firstName} lastName={item.lastName} updateProgres={props.updateProgres}/>
                        )
                    })
                }
            </div>
        </div>
    );
}

export default MemberSection;