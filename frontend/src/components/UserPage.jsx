import React, { useContext } from 'react'
import Header from './Header'
import { getMyPolicies, getPolicies } from '../services/policyService'
import { useState } from 'react'
import toast from 'react-hot-toast';
import { useNavigate } from 'react-router-dom';
import { UserContext } from '../context/UserContext';

function UserPage() {

    const [policies, setPolices] = useState([]);
    const { setPolicy, setMyPoliciies, user } = useContext(UserContext);
    const navigate = useNavigate();

    const handleShowPolicy = async () => {
        try {
            const res = await getPolicies();
            console.log(res);
            setPolices(res);
        } catch (error) {
            toast.error(error.message);
            console.log(error.message)
        }
    }

    const handleButtonClick = (selectedPolicy) => {
        setPolicy(selectedPolicy);
        navigate('/issuePage');
    }

    const handleMyPoliciesClick = async () => {
        try {
            const res = await getMyPolicies(user.userId);
            setMyPoliciies(res);
            console.log(res);
            navigate('/myPolicies')
        } catch (error) {
            toast.error(error.message);
            console.log(error.message)
        }
    }

    return (
        <div>
            <Header />
            <div className='mt-5 d-flex justify-content-center align-items-center gap-4'>
                <button className='btn text-white' style={{ backgroundColor: "red" }} onClick={handleShowPolicy} >Show Policies</button>
                <button className='btn text-white' style={{ backgroundColor: "red" }} onClick={handleMyPoliciesClick}>My Policies</button>
            </div>
            <div className='mt-5 d-flex justify-content-center align-items-center gap-4'>
                {
                    policies.length > 0 && (
                        <div>
                            <table className='table table-bordered mt-3 table-striped'>
                                <thead className='table-dark text-center'>
                                    <tr>
                                        <th>Id</th>
                                        <th>Name</th>
                                        <th>Yearly payment</th>
                                        <th>Description</th>
                                        <th>Vehicle</th>
                                        <th>Get Policy</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {
                                        policies.map((policy) => (
                                            <tr key={policy.policyId}>
                                                <td>{policy.policyId}</td>
                                                <td>{policy.policyName}</td>
                                                <td>{policy.policyAmt}</td>
                                                <td>{policy.description}</td>
                                                <td>{policy.vehichleType}</td>
                                                <td>
                                                    <button className='btn btn-success' onClick={() => handleButtonClick(policy)}>
                                                        Get Policy
                                                    </button>
                                                </td>
                                            </tr>
                                        ))
                                    }
                                </tbody>
                            </table>
                        </div>
                    )
                }
            </div>
        </div>
    )
}

export default UserPage
