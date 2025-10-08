import React from 'react'
import Header from './Header'
import RegisterPage from './RegisterPage'
import { useNavigate } from 'react-router-dom'

function Home() {

    const navigate = useNavigate();

    const handleLoginClick = () => {
        navigate('/user/login');
    }

  return (
    <div>
        <Header/>
        <div className='text-center mt-5 fs-1 fw-bold'>
            <span className='p-1 rounded px-2 text-white' style={{backgroundColor:"red",cursor:"pointer"}} onClick={handleLoginClick}>&nbsp;LogIn </span> &nbsp;to View More
        </div>
      <RegisterPage/>
    </div>
  )
}

export default Home
