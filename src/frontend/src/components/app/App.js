import {lazy, Suspense, useEffect, useState} from 'react'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import AuthService from '../../services/authService';
import LogIn from '../logIn/LogIn';
import Registration from '../registration/Registration';
import RegistrationConfirmation from '../registration/RegistrationConfirmation';
import FirstPage from '../../pages/firstPage/FirstPage';
import Spinner from '../spinner/Spinner';
import './App.css';
import ResetPassword from '../resetPassword/ResetPassword';
import ResetPasswordEditor from '../resetPassword/ResetPasswordEditor';
import UserPage from '../../pages/userPage/UserPage';

const MainPage = lazy(() => import("../../pages/mainPage/mainPage"));

const App = () => {
  const [currentUser, setCurrentUser] = useState(null);

  useEffect(() => {
    const user = AuthService.getCurrentUser();
    if(user){
      setCurrentUser(user);
    }
  }, []);


  return (

    <div>
      <Router>
      <div className="App">
        <Suspense fallback={<Spinner/>}>
          <Switch>
            <Route exact path="/">
              <FirstPage/>
            </Route>
            <Route exact path="/home">
              <MainPage/>
            </Route>
            <Route exact path="/user">
              <UserPage/>
            </Route>
            <Route exact path="/login">
              <LogIn/>
            </Route>
            <Route exact path="/registration">
              <Registration/>
            </Route>
            <Route exact path="/confirmation">
              <RegistrationConfirmation/>
            </Route>
            <Route exact path="/resetPassword">
              <ResetPassword/>
            </Route>
            <Route exact path="/resetPasswordEditor">
              <ResetPasswordEditor/>
            </Route>
          </Switch>
        </Suspense>
      </div>
    </Router>
    </div>
  );
}

export default App;
