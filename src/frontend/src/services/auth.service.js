import axios from 'axios';

const API_URL = "http://localhost:8080/";

const login = (email, password) => {
    return axios
    .post(API_URL + "login", {
        email,
        password,
    }
    )
    .then((res) => {
        if(res.data.accessToken) {
            localStorage.setItem("user", JSON.stringify(res.data));
        }
        return res.data;
    });
};

const register = (firstName, lastName, email, password, img) => {
    return axios.post(API_URL + "registration", {
        firstName,
        lastName,
        email,
        password,
        img
    });
};

const logout = () => {
    localStorage.removeItem("user");
};


const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("user"));
};


const AuthService = {
    register,
    login,
    logout,
    getCurrentUser
};


export default AuthService;