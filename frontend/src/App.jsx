
import { BrowserRouter, Routes, Route } from 'react-router-dom'
import 'bootstrap-icons/font/bootstrap-icons.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.bundle.min.js';

import Home from './components/Home.jsx'
import RegisterPage from './components/RegisterPage.jsx'
import Login from './components/Login.jsx'
import { Toaster } from 'react-hot-toast'
import UserPage from './components/UserPage.jsx'
import IssuePage from './components/IssuePage.jsx'
import MyPolicies from './components/MyPolicies.jsx'
import AdminPage from './components/AdminPage.jsx'



function App() {


  return (
    <BrowserRouter>
    <Toaster/>
      <Routes>
        <Route path='/' element={<Home />}/>
        <Route path='/user/login' element={<Login />}/>
        <Route path='/register' element={<RegisterPage/>}/>
        <Route path='/user' element={<UserPage/>}/>
        <Route path='/issuePage' element={<IssuePage/>}/>
        <Route path='/myPolicies' element={<MyPolicies/>}></Route>
        <Route path='/admin/login' element={<Login/>}/>
        <Route path='/admin/adminPage' element={<AdminPage/>}/>
      </Routes>
    </BrowserRouter>
  )
}

export default App
