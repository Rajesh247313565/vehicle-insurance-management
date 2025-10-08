import React, { useContext, useState } from 'react'
import { useNavigate } from 'react-router-dom';
import { UserContext } from '../context/UserContext'

function Header({ showRegister, toggle }) {

    const { user } = useContext(UserContext);
    const [login, setLogin] = useState(true);
    const navigate = useNavigate();

    const handleIconClick = () => {
        navigate('/');
    }

    const handleLoginClick = () => {
        if (location.pathname === '/login') {
            toggle();
        } else {
            navigate('/login'); 
        }
    }

    return (
        <div className='py-2 d-flex justify-content-between align-items-center' style={{ backgroundColor: "#2488ed" }}>
            <span className='fs-3 fw-bold ms-4' onClick={handleIconClick} style={{ cursor: "pointer" }}>S BANK</span>
            <div className='me-4'>
                {
                    user && user.userName ? (
                       <div className='p-2 rounded-pill me-3' style={{backgroundColor:"white"}}>
                         <span className='text-dark fw-bold fs-5 ' style={{ cursor: "pointer" }}>Welcome, {user.userName}</span>
                       </div>
                    ) : (
                        <button className='btn btn-danger' onClick={handleLoginClick}>
                            {location.pathname === '/login' ? (showRegister ? "Logout" : "Login") : "Login"}
                        </button>
                    )
                }
            </div>
        </div>
    )
}

export default Header
