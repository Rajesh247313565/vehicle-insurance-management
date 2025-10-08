import React, { useContext, useState } from 'react'
import Header from './Header'
import toast from 'react-hot-toast';
import { registerUser } from '../services/userServices';
import { CreatePolicy } from '../services/AdminService';
import { UserContext } from '../context/UserContext'
import { getPolicies } from '../services/policyService';

function AdminPage() {

    const { policy, setPolicy } = useContext(UserContext)

    const [showCreateUser, setShowCreateUser] = useState(false);
    const [showCreatePolicy, setShowCreatePolicy] = useState(false);
    const [showAllUser, setShowAllUsers] = useState(false);
    const [showAllPolicies, setSohwAllPolicies] = useState(false);

    const [createUser, setCreateUser] = useState(false);
    const [allUser, setAllUser] = useState([]);
    const [createPolicy, setCreatePolicy] = useState(false);
    const [allPolicy, setAllPolicy] = useState([]);
    const [policies, setPolices] = useState([]);

    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');


    const [policyName, setPolicyName] = useState('');
    const [policyAmount, setPolicyAmount] = useState('');
    const [vehicleType, setVehicleType] = useState('');
    const [description, setDescription] = useState('');


    const handleRegisterClick = async () => {


        try {
            const res = await registerUser(name, email, password);
            console.log(res);
            toast.success("Registered successfully");
            setShowCreateUser(false);
            setName('');
            setEmail('');
            setPassword('');
        } catch (error) {
            setEmail('');
            setName('');
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

    const handleShowPolicyClick = () => {
        setShowCreateUser(false);
        setShowAllUsers(false);
        setShowCreatePolicy(!showCreatePolicy);
    }

    const handleShowRegisterClick = () => {
        setShowCreatePolicy(false);
        setShowAllUsers(false);
        setShowCreateUser(!showCreateUser);
    }

    const handleCreatePolicy = async () => {
        console.log(policyName, policyAmount, vehicleType, description);
        toast.success("Policy created successfully");
        setPolicyAmount('');
        setPolicyName('');
        setVehicleType('');
        setDescription('');

        try {
            const res = await CreatePolicy(policyName, policyAmount, vehicleType, description);
            console.log(res);
            toast.success("Policy created successfully");
            setCreatePolicy(false);
        } catch (error) {

            setPolicyAmount('');
            setPolicyName('');
            setVehicleType('');
            setDescription('');

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

    const handleShowAllPolicies = async () => {
        setShowCreateUser(false);
        setShowCreatePolicy(false);
        setShowAllUsers(false);
        setSohwAllPolicies(!showAllPolicies);

        try {
            const res = await getPolicies();
            console.log(res);
            setPolices(res);
        } catch (error) {
            toast.error(error.message);
            console.log(error.message)
        }

    }

    return (
        <div className='d-flex flex-column'>
            <Header />
            <div className='d-flex justify-content-center align-items-center gap-2 mt-5'>
                <button className='btn btn-danger' onClick={handleShowRegisterClick}>Create User</button>
                <button className='btn btn-danger' >All User Info</button>
                <button className='btn btn-danger' onClick={handleShowPolicyClick}>Create Policy</button>
                <button className='btn btn-danger' onClick={handleShowAllPolicies}>All Policy Info</button>
            </div>
            {
                showCreateUser && (
                    <div className=''>

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

                            <button className='btn btn-success w-50 mt-3' onClick={handleRegisterClick}>Register</button>
                        </div>
                    </div>
                )
            }

            {
                showCreatePolicy && (
                    <div>
                        <div className='mt-4 d-flex flex-column align-items-center gap-4'>
                            <input
                                type="text"
                                placeholder='Policy Name'
                                className='form-control w-75'
                                onChange={(e) => setPolicyName(e.target.value)}
                                value={policyName}
                            />
                            <input
                                type="number"
                                placeholder='Policy Amount'
                                className='form-control w-75'
                                onChange={(e) => setPolicyAmount(e.target.value)}
                                value={policyAmount}
                            />
                            <select className='form-select w-75' onChange={(e) => setVehicleType(e.target.value)}>
                                <option value="">Vehicle Type</option>
                                <option value="CAR">Car</option>
                                <option value="BIKE">Bike</option>
                            </select>

                            <input type="textarea"
                                placeholder='Description'
                                className='form-control w-75'
                                onChange={(e) => setDescription(e.target.value)}
                                value={description}
                            />

                            <button className='btn btn-success w-50 mt-3' onClick={handleCreatePolicy}>Create Policy</button>
                        </div>
                    </div>
                )
            }
            {
                showAllPolicies && (
                    <div>
                        {
                            policies.length > 0 ? (
                                <div>
                                    <table className='table table-bordered mt-4'>
                                        <thead>
                                            <tr className=''>
                                                <th>Id</th>
                                                <th>Name</th>
                                                <th>vehicalType</th>
                                                <th>Description</th>
                                                <th>Action</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            {
                                                policies.map((policy) => (
                                                    <tr key={policy.policyId}>
                                                        <td >{policy.policyId}</td>
                                                        <td>{policy.policyName}</td>
                                                        <td>{policy.vehichleType}</td>
                                                        <td>{policy.description}</td>
                                                        <td className='d-flex justify-content-around'>
                                                            <button className='btn btn-success' data-bs-toggle="modal" data-bs-target="#ploicyUpdateModal">
                                                                <span className='bi bi-pencil-square'></span>
                                                            </button>
                                                            <button className='btn btn-danger'>
                                                                <span className='bi bi-trash3'></span>
                                                            </button>
                                                        </td>
                                                    </tr>
                                                ))
                                            }
                                        </tbody>
                                    </table>
                                </div>
                            ) :
                                <div>
                                    <h1 className='text-center mt-5 text-danger'>No Policies found</h1>
                                </div>
                        }
                    </div>

                )
            }


            <div className="modal fade" id="ploicyUpdateModal" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div className="modal-dialog">
                    <div className="modal-content">
                        <div className="modal-header">
                            <h1 className="modal-title fs-5" id="exampleModalLabel">Policy Details</h1>
                            <button type="button" className="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div className="modal-body">
                           <div className='mt-4 d-flex flex-column align-items-center gap-4'>
                            <input
                                type="text"
                                placeholder='Policy Name'
                                className='form-control w-75'
                                onChange={(e) => setPolicyName(e.target.value)}
                                value={policyName}
                            />
                            <input
                                type="number"
                                placeholder='Policy Amount'
                                className='form-control w-75'
                                onChange={(e) => setPolicyAmount(e.target.value)}
                                value={policyAmount}
                            />
                            <select className='form-select w-75' onChange={(e) => setVehicleType(e.target.value)}>
                                <option value="">Vehicle Type</option>
                                <option value="CAR">Car</option>
                                <option value="BIKE">Bike</option>
                            </select>

                            <input type="textarea"
                                placeholder='Description'
                                className='form-control w-75'
                                onChange={(e) => setDescription(e.target.value)}
                                value={description}
                            />
                        </div>
                        </div>
                        <div className="modal-footer">
                            <button type="button" className="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                            <button type="button" className="btn btn-primary">Save changes</button>
                        </div>
                    </div>
                </div>
            </div>
        </div >
    )
}

export default AdminPage
