import axios from "axios";
import authHeader from "./authHeader";
const API_URL = "http://localhost:8080/teams";


const createTeam = async (name, teamLeaderId, membersIds) => {
    return axios.post(API_URL + "/save", {name, teamLeaderId, membersIds});
}


const TeamService = {
    createTeam     
};

export default TeamService;