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
        url: API_URL + "/registration",
        data: formData,
    }).catch(e => console.log(e));
};

const resetPassword = async (email) => {
    return axios({
        method: 'post',
        url: API_URL + "/reset_password_email",
        params: {email: email},
    }).catch(e => console.log(e));
};

const getUserByToken = async (token) => {
    console.log(token);
    return axios({
        method: 'get',
        url: API_URL + "/get_user_by_reset_password_token",
        params: {token: token},
    }).then(res => res.data)
    .catch(e => console.log(e));
};

const resetPasswordEditor = async (password, id) => {
    return axios({
        method: 'post',
        url: API_URL + "/reset_password",
        params: {
            password: password,
            id: id
        },
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
    resetPassword,
    getUserByToken,
    resetPasswordEditor,
    logout,
    getCurrentUser,
    getUserToken,
    getUserTeamId
};


export default AuthService;