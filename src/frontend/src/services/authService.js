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

// const register = (firstName, lastName, email, password, img) => {
//     return axios({
//         method: 'post',
//         url: API_URL + "/registration",
//         data : {
//             "firstName": firstName,
//             "lastName": lastName,
//             "email": email,
//             "password": password,
//             "image": img
//         },
//     }).catch(e => console.log(e));
// };


const register = (firstName, lastName, email, password, img) => {
    let formData = new FormData();
    formData.append("firstName", firstName);
    formData.append("lastName", lastName);
    formData.append("email", email);
    formData.append("password", password);
    formData.append("image", img);
    
    return axios({
        method: 'post',
        url: API_URL + "/registration_postman",
        data: formData,
    }).catch(e => console.log(e));
};

const logout = () => {
    localStorage.removeItem("accessToken");
    window.location.reload();
};


const getCurrentUser = () => {
    return JSON.parse(localStorage.getItem("accessToken"));
};

const getUserToken = () => {
    return localStorage.getItem("accessToken");
}

const getUserTeamId = () =>{
    return localStorage.getItem("id");
}

const AuthService = {
    register,
    login,
    logout,
    getCurrentUser,
    getUserToken,
    getUserTeamId
};


export default AuthService;