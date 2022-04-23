import axios from 'axios';

const API_URL = "http://localhost:8080/auth";

const login = (email, password) => {
    return axios
    .post(API_URL + "/login", {
        email,
        password,
    }
    )
    .then((res) => {
        if(res.data.token) {
            localStorage.setItem("accessToken", JSON.stringify(res.data));
        }
        return res.data;
    });
};

const register = (firstName, lastName, email, password, img) => {
    return axios.post(API_URL + "/registration", {
        firstName,
        lastName,
        email,
        password,
        img
    });
};

const logout = () => {
    localStorage.removeItem("accessToken");
};


const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("accessToken"));
};

const getUserToken = () => {
    return localStorage.getItem("accessToken");
}

const AuthService = {
    register,
    login,
    logout,
    getCurrentUser,
    getUserToken
};


export default AuthService;