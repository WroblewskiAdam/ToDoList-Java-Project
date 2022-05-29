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

const getGivenTasks = async () => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/given", {headers: authHeader(), params: {id: userId}}).then(res => res.data).catch((error) => console.log(error)); 
}

const getReceiversWhoDone = async (id) => {
    return axios.get(API_URL + "/get_receivers_who_done", {headers: authHeader(), params: {taskId: id}}).then(res => res.data).catch((error) => console.log(error));
}

// --------------- Team ---------------------------

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

const getExpiredGivenTasks = async (teamId) => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    
    return axios.get(API_URL + "/team_expired_given", {headers: authHeader(), params: {teamId: teamId, userId: userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getDoneTasks = async (teamId) => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/team_done", {headers: authHeader(), params: {teamId: teamId, userId: userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getDoneGivenTasks = async (teamId) => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/team_done_given", {headers: authHeader(), params: {teamId: teamId, userId: userId}}).then(res => res.data).catch((error) => console.log(error));
}

// ---------- Private -------------------------

const getPrivateTasks = async() => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;

    return axios.get(API_URL + "/private", {headers: authHeader(), params: {id: userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getPrivateGivenTasks = async () => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/private_given", {headers: authHeader(), params: {id: userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getPrivateTodayTasks = async () => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/private_today", {headers: authHeader(), params: {id: userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getPrivateTodayGivenTasks = async () => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/private_today_given", {headers: authHeader(), params: {id: userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getPrivateSevenDaysTasks = async () => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/private_seven_days", {headers: authHeader(), params: {id: userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getPrivateSevenDaysGivenTasks = async () => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/private_seven_days_given", {headers: authHeader(), params: {id: userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getPrivateExpiredTasks = async () => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/private_expired", {headers: authHeader(), params: {id: userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getPrivateExpiredGivenTasks = async () => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/private_expired_given", {headers: authHeader(), params: {id: userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getPrivateDoneTasks = async () => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/private_done_received", {headers: authHeader(), params: {id: userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getPrivateDoneGivenTasks = async () => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/private_done_given", {headers: authHeader(), params: {id: userId}}).then(res => res.data).catch((error) => console.log(error));
}

const getUserDoneTasks = async () => {
    const userId = JSON.parse(localStorage.getItem("accessToken")).id;
    return axios.get(API_URL + "/done_received", {headers: authHeader(), params: {id: userId}}).then(res => res.data).catch((error) => console.log(error));
}

const deleteTask = async (taskId) => {
    return axios({
        method: 'delete',
        url: API_URL + "/delete",
        params: {
            taskId: taskId,
        },
        headers: authHeader()
    }).then(res => res.data).catch((error) => console.log(error));
}

const getTeamReceivedTasks = async (teamId, userId) => {
    return axios.get(API_URL + "/team_received", {headers: authHeader(), params: {userId: userId, teamId:teamId}}).then(res => res.data).catch((error) => console.log(error));
}

const getTeamDoneTasks = async (teamId, userId) => {
    return axios.get(API_URL + "/team_done", {headers: authHeader(), params: {userId: userId, teamId:teamId}}).then(res => res.data).catch((error) => console.log(error));
}

const TaskService = {
    createTask,
    getReceivers,
    getGivenTasks,
    getReceiversWhoDone,
    getDoneTasks,
    getTodayTasks,
    getSevenDaysTasks,
    getExpiredTasks,
    tickTask,
    checkIfTaskDoneByUser,
    getPrivateTasks,
    getUserDoneTasks,
    getPrivateGivenTasks,
    getPrivateTodayTasks,
    getPrivateTodayGivenTasks,
    getPrivateSevenDaysTasks,
    getPrivateSevenDaysGivenTasks,
    getPrivateExpiredTasks,
    getPrivateExpiredGivenTasks,
    getPrivateDoneTasks,
    getPrivateDoneGivenTasks,
    getAllTeamTasks,
    getAllGivenTeamTasks,
    getTodayGivenTasks,
    getSevenDaysGivenTasks,
    getExpiredGivenTasks,
    getDoneGivenTasks,
    deleteTask,
    getTeamReceivedTasks,
    getTeamDoneTasks
};

export default TaskService;