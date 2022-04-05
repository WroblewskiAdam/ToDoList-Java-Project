import {lazy, Suspense} from 'react'
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import LogIn from '../logIn/LogIn';
import Registration from '../logIn/Registration';
import SideBar from '../sideBar/SideBar';
import Spinner from '../spinner/Spinner';
import './App.css';

const MainPage = lazy(() => import("../pages/mainPage"));

const App = () => {
  return (
    <Router>
      <div className="App">
        <main>
          <Suspense fallback={<Spinner/>}>
            <Switch>
              <Route exact path="/">
                <MainPage/>
              </Route>
              <Route exact path="/login">
                <LogIn/>
              </Route>
              <Route exact path="/registration">
                <Registration/>
              </Route>
            </Switch>
          </Suspense>
        </main>
      </div>
    </Router>
  );
}

export default App;
