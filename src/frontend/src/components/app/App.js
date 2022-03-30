import BasicGroupListItem from '../basicGroupList-item/BasicGroupListItem';
import BasicGroupList from '../basicGroupList/BasicGroupList';
import Groups from '../groups/Groups';
import Header from '../header/Header';
import List from '../list/List';
import LogIn from '../logIn/LogIn';
import FirstPage from '../pages/firstPage/FirstPage';
import LoginPage from '../pages/loginPage/LoginPage';
import SideBar from '../sideBar/SideBar';
import TeamModal from '../teamModal/TeamModal';
import Test from '../test/test';
import './App.css';

function App() {
  return (
    <div className="App">
      {/* <Header/>
      <List/> */}
      <SideBar/>
      <Groups/>
      {/* <TeamModal/> */}
      {/* <Test/> */}
      {/* <LogIn/> */}
      {/* <LoginPage/> */}
      {/* <FirstPage/> */}
    </div>
  );
}

export default App;
