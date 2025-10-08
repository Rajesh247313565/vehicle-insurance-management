import React, { useCallback, useContext, useState } from 'react'
import Header from './Header'
import { loginUser, registerUser } from '../services/userServices';
import toast from 'react-hot-toast';
import { useLocation, useNavigate } from 'react-router-dom';
import { UserContext } from '../context/UserContext';

function Login() {

  const navigate = useNavigate();

  const [login, setLogin] = useState(true);

  const { setUser } = useContext(UserContext);


  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [conformPassword, setConformPassword] = useState('');
  const { token, setToken } = useContext(UserContext);
  const location = useLocation();


  const handleLogin = async () => {
    try {
      const res = await loginUser(email, password);
      setUser(res);
      setToken(res.token);
      localStorage.setItem("token", res.token);

      console.log(res);
      toast.success('Login successfull');
      console.log("Location::" + location.pathname)
      console.log("Location::" + location.pathname + location.search)


      if (location.pathname === '/admin/login') {
        navigate('/admin/adminPage');
      } else {
        navigate('/user/login');
      }
    } catch (error) {
      setEmail('');
      setPassword('');

      if (error.fieldErrors) {
        const firstErrorMsg = Object.values(error.fieldErrors)[0];
        console.log(firstErrorMsg);
        toast.error(firstErrorMsg);
      } else {
        console.log(error.message);
        toast.error(error.message || "Something went wrong");
      }
    }
  }

  const handleRegister = async () => {
    if (password !== conformPassword) {
      setConformPassword('');
      toast.error('incorrect password')
      return;
    }

    try {
      const res = await registerUser(name, email, password);
      console.log(res);
      toast.success('registered successfully')
      setEmail('');
      setName('');
      setPassword('');
      setConformPassword('');
      setLogin(true);
    } catch (error) {
      setEmail('');
      setName('');
      setPassword('');
      setConformPassword('');

      if (error.fieldErrors) {
        const firstErrorMsg = Object.values(error.fieldErrors)[0];
        console.log(firstErrorMsg);
        toast.error(firstErrorMsg);
      } else {
        console.log(error.message);
        toast.error(error.message || "Something went wrong");
      }
    }


  }

  return (
    <div style={{}}>
      <Header showRegister={login} toggle={() => setLogin(!login)} />
      <div className='d-flex justify-content-center align-items-center mt-5'>
        <div className='card' style={{ backgroundColor: "#2488ed", font: "black", height: login ? "300px" : "400px", width: "400px" }}>
          <div className='card-body text-center p-2'>
            <div className='border-bottom border-dark'>
              <span className='card-title text-dark fw-bold fs-3'>{login ? "Login" : "Register"}</span>
            </div>
            {
              login && (
                <div>

                  <div className='mt-4 d-flex flex-column align-items-center gap-4'>
                    <input
                      type="email"
                      placeholder='Email'
                      className='form-control w-75'
                      onChange={(e) => setEmail(e.target.value)}
                      value={email}
                    />
                    <input type="password"
                      placeholder='password'
                      className='form-control w-75'
                      onChange={(e) => setPassword(e.target.value)}
                      value={password}
                    />

                    <button className='btn btn-danger w-50 mt-3' onClick={handleLogin}>Login</button>
                  </div>
                </div>
              )
            }
            {
              !login && (
                <div>

                  <div className='mt-4 d-flex flex-column align-items-center gap-4'>
                    <input
                      type="text"
                      placeholder='Name'
                      className='form-control w-75'
                      onChange={(e) => setName(e.target.value)}
                      value={name}
                    />
                    <input
                      type="email"
                      placeholder='Email'
                      className='form-control w-75'
                      onChange={(e) => setEmail(e.target.value)}
                      value={email}
                    />
                    <input type="password"
                      placeholder='password'
                      className='form-control w-75'
                      onChange={(e) => setPassword(e.target.value)}
                      value={password}
                    />
                    <input
                      type="password"
                      placeholder='conform password'
                      className='form-control w-75'
                      onChange={(e) => setConformPassword(e.target.value)}
                      value={conformPassword}
                    />

                    <button className='btn btn-danger w-50 mt-3' onClick={handleRegister}>Register</button>
                  </div>
                </div>
              )
            }
          </div>
        </div>
      </div>
    </div>
  )
}

export default Login
