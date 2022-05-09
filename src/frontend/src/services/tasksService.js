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

const TaskService = {
    createTask,
    getReceivers,
    getReceiversWhoDone
};

export default TaskService;