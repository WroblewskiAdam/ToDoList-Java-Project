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


const checkIfTaskDoneByUser = async(taskId) => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    
    return axios.get(API_URL + "/check_if_task_is_done_by_user", {headers: authHeader(), params: {taskId: taskId, userId: userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getReceivers = async (id) => {
    return axios.get(API_URL + "/get_receivers", {headers: authHeader(), params: {taskId: id}}).then(res => res.data).catch((error) => console.log(error));
}

const getReceiversWhoDone = async (id) => {
    return axios.get(API_URL + "/get_receivers_who_done", {headers: authHeader(), params: {taskId: id}}).then(res => res.data).catch((error) => console.log(error));
}

const getAllTeamTasks = async (teamId) => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/team_received", {headers: authHeader(), params: {teamId: teamId, userId:userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getAllGivenTeamTasks = async (teamId) => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/team_given", {headers: authHeader(), params: {teamId: teamId, userId:userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getTodayTasks = async (teamId) => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/team_today_received", {headers: authHeader(), params: {teamId: teamId, userId:userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getTodayGivenTasks = async (teamId) => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/team_today_given", {headers: authHeader(), params: {teamId: teamId, userId:userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getSevenDaysTasks = async (teamId) => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/team_seven_days_received", {headers: authHeader(), params: {teamId: teamId, userId:userId}}).then(res => {return res.data;}).catch((error) => console.log(error));
}

const getSevenDaysGivenTasks = async (teamId) => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/team_seven_days_given", {headers: authHeader(), params: {teamId: teamId, userId:userId}}).then(res => {return res.data;}).catch((error) => console.log(error));
}

const getExpiredTasks = async (teamId) => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    
    return axios.get(API_URL + "/team_expired", {headers: authHeader(), params: {teamId: teamId, userId: userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getDoneTasks = async (teamId) => {
    return axios.get(API_URL + "/team_done_all", {headers: authHeader(), params: {teamId: teamId}}).then(res => res.data).catch((error) => console.log(error));
}


const getPrivateTasks = async() => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;

    return axios.get(API_URL + "/private", {headers: authHeader(), params: {id: userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getUserDoneTasks = async () => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/done_received", {headers: authHeader(), params: {id: userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getGivenTasks = async () => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/private_given", {headers: authHeader(), params: {id: userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getPrivateTodayTasks = async () => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/private_today", {headers: authHeader(), params: {id: userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getPrivateSevenDaysTasks = async () => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/private_seven_days", {headers: authHeader(), params: {id: userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getPrivateExpiredTasks = async () => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/private_expired", {headers: authHeader(), params: {id: userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getPrivateGivenExpiredTasks = async () => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/private_expired_given", {headers: authHeader(), params: {id: userId}}).then(res => res.data).catch((error) => console.log(error));

}


const TaskService = {
    createTask,
    getReceivers,
    getReceiversWhoDone,
    getDoneTasks,
    getTodayTasks,
    getSevenDaysTasks,
    getExpiredTasks,
    tickTask,
    checkIfTaskDoneByUser,
    getPrivateTasks,
    getUserDoneTasks,
    getGivenTasks,
    getPrivateTodayTasks,
    getPrivateSevenDaysTasks,
    getPrivateExpiredTasks,
    getPrivateGivenExpiredTasks,
    getAllTeamTasks,
    getAllGivenTeamTasks,
    getTodayGivenTasks,
    getSevenDaysGivenTasks
};

export default TaskService;