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
    return axios.post(API_URL + "/save", {title, description, deadline, priority, giverId, receiversIds, teamId}, {headers: authHeader() });
}

const TaksService = {
    createTask     
};

export default TaksService;