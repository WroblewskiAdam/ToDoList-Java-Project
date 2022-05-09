import axios from "axios";
import authHeader from "./authHeader";
const API_URL = "http://localhost:8080/teams";


const createTeam = async (name, teamLeaderId, membersIds) => {
    // return axios.post(API_URL + "/save", {name, teamLeaderId, membersIds}, {headers: authHeader() });
    return axios({
        method: 'post',
        url: API_URL + "/save",
        data : {
            "name": name,
            "teamLeaderId": teamLeaderId,
            "membersIds": membersIds,
        },
        headers: authHeader()
    })
}

const getTeamTasks = async (teamId) => {
    return axios.get(API_URL + "/getTeamTasks", {headers: authHeader(), params: {teamId: teamId}}).then(res => res.data).catch((error) => console.log(error));
}

const getTeamMembers = async (teamId) => {
    return axios.get(API_URL + "/getMembers", {headers: authHeader(), params: {teamId: teamId}}).then(res => res.data).catch((error) => console.log(error));
}

const TeamService = {
    createTeam,
    getTeamTasks,
    getTeamMembers
};

export default TeamService;