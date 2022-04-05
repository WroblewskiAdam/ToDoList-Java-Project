import axios from "axios";
import authHeader from "./auth-header";
const API_URL = "http://localhost:8080/";


const getAllUsers = () => {
    return axios.get(API_URL + "users/showAll", {headers: authHeader() });
}

const UserService = {
    getAllUsers,
};

export default UserService;