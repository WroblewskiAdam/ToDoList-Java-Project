import axios from "axios";
import authHeader from "./authHeader";
const API_URL = "http://localhost:8080/tasks";


const createTask = async (
        title,
        description,
        deadline,
        priority,
        giverId,
        receiversIds,
        teamId
) => {
    // return axios.post(API_URL + "/save", {title, description, deadline, priority, giverId, receiversIds, teamId}, {headers: authHeader() });
    return axios({
        method: 'post',
        url: API_URL + "/save",
        data : {
            "title": title,
            "description": description,
            "deadline": deadline,
            "priority": priority,
            "giverId": giverId,
            "receiversIds": receiversIds,
            "teamId": teamId
        },
        headers: authHeader()
    })

}

// const todayTasks = () => {

// }

const getReceivers = async (id) => {
    return axios.get(API_URL + "/get_receivers", {headers: authHeader(), params: {taskId: id}}).then(res => res.data).catch((error) => console.log(error));
}

const getReceiversWhoDone = async (id) => {
    return axios.get(API_URL + "/get_receivers_who_done", {headers: authHeader(), params: {taskId: id}}).then(res => res.data).catch((error) => console.log(error));
}

const getDoneTasks = async (teamId) => {
    // const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/team/done_all", {headers: authHeader(), params: {teamid: teamId}}).then(res => res.data).catch((error) => console.log(error));
}

const getTodayTasks = async (teamId) => {
    return axios.get(API_URL + "/team/today", {headers: authHeader(), params: {teamid: teamId}}).then(res => res.data).catch((error) => console.log(error));
}

const getSevenDaysTasks = async (teamId) => {
    console.log(axios.get(API_URL + "/team/seven_days", {headers: authHeader(), params: {teamid: teamId}}));
    return axios.get(API_URL + "/team/seven_days", {headers: authHeader(), params: {teamid: teamId}}).then(res => {
        console.log(res);
        console.log(res.data);
        return res.data;
    }).catch((error) => console.log(error));
}

const getExpiredTasks = async (teamId) => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    
    return axios.get(API_URL + "/team/expired", {headers: authHeader(), params: {teamid: teamId, userid: userId}}).then(res => res.data).catch((error) => console.log(error));
}

const tickTask = async (taskId) => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;

    return axios({
        method: 'put',
        url: API_URL + "/tick",
        params: {
            "taskId" : taskId,
            "userId" : userId
        },
        headers: authHeader()
    }).catch(e => console.log(e));
}

const TaskService = {
    createTask,
    getReceivers,
    getReceiversWhoDone,
    getDoneTasks,
    getTodayTasks,
    getSevenDaysTasks,
    getExpiredTasks,
    tickTask
};

export default TaskService;