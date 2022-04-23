import { combineReducers } from "redux";
import authReducer from "./user/auth/authReducer";

const rootReducer = combineReducers({
  auth: authReducer,
});

export default rootReducer;