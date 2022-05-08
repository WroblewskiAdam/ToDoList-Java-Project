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

const TaskService = {
    createTask     
};

export default TaskService;