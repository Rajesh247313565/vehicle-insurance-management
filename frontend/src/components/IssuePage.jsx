import React, { useContext, useEffect, useState } from 'react'
import Header from './Header'
import { UserContext } from '../context/UserContext';
import { issuePolicy } from '../services/policyService';
import toast from 'react-hot-toast';
import { useNavigate } from 'react-router-dom';
import { DocumentRules } from '../context/enums';

function IssuePage() {


    const [vehicleType, setVehicleType] = useState('');
    const [documentType, setDocumentType] = useState('');
    const [regNo, setRegNo] = useState('');
    const [installmentType, setInstallmentType] = useState('');
    const [amount, setAmount] = useState('');
    const [docNumber, setDocNumber] = useState('');


    const [info, setInfo] = useState(false);
    const [showPay, setShowPay] = useState(false);

    const navigate = useNavigate();

    const { policy, user, setIssuedPolicy } = useContext(UserContext);

    const [showPolicy, setShowPolicy] = useState(true);


    const installmentConfig = {
        YEARLY: { multiplier: 0.00, count: 1 },
        HALFYEARLY: { multiplier: 0.02, count: 2 },
        QUARTERLY: { multiplier: 0.02, count: 3 },
        MONTHLY: { multiplier: 0.02, count: 12 }
    };

    const handlePolicyClick = () => {

        if (!policy) {
            console.warn("Policy or installment type not selected");
            return;
        }
        if (!documentType) {
            toast.error("Select Document Type");
            return;
        } else if (!docNumber) {
            toast.error("Enter Document Number");
            return;
        } else if (!regNo) {
            toast.error("Enter Vehichle Number");
            return;
        } else if (!installmentType) {
            toast.error("Select Installment Type");
            return;
        }

        const rules = DocumentRules[documentType];
        const value = docNumber;


        if (docType === "AADHAAR" && !/^\d*$/.test(value)){
            return;
        } else{
            toast.error("Enter valid AADHAAR Number")
        }

         if (docType === "AADHAAR" && !/^\d*$/.test(value)){
            return;
        } else{
            toast.error("Enter valid AADHAAR Number")
        }



        if (rules && value.length > rules.max) return;


        const baseAmount = policy.policyAmt;
        const config = installmentConfig[installmentType];

        if (config) {
            const totalWithInterest = baseAmount + baseAmount * config.multiplier;
            const perInstallment = totalWithInterest / config.count;

            setAmount(perInstallment.toFixed(2)); // Save formatted amount
            console.log("Installment amount:", perInstallment);
        }

        setVehicleType(policy.vehichleType);
        console.log(policy);
        setShowPolicy(false);
        setInfo(true);


    }

    const issueData = {
        userId: user.userId,
        policyId: policy.policyId,
        vehicalType: vehicleType,
        documentType: documentType,
        documentNumber: docNumber,
        regNo: regNo,
        installmentType: installmentType,
        amountPaid: amount

    }


    useEffect(() => {
        console.log("Vehicle type updated to:", vehicleType);
    }, [vehicleType]);


    const handleVehicleChange = (e) => {
        setVehicleType(e.target.value);
    }

    const handleDocumentChange = (e) => {
        setDocumentType(e.target.value);
    }

    const handleInstallmentChange = (e) => {
        setInstallmentType(e.target.value);
    }

    const handlePayClick = async () => {
        try {
            const res = await issuePolicy(issueData);
            setIssuedPolicy(res);
            navigate('/user')
            toast.success('Policy issued')
        } catch (error) {
            console.log("Error occurred:", error);
            toast.error(error?.response?.data?.message || "Something went wrong");
        }
    }

    const handleClick= () => {
        setInfo(false);
        setShowPay(true);
    }

    const handleCancelClick = () => {
        navigate('/user');
        setShowPolicy(true);
        setInfo(false);
        setShowPay(false);
    }

    return (
        <div>
            <Header />

            <div className='d-flex justify-content-center align-items-center mt-4'>
                {
                    showPolicy && (
                        <div>
                            <div className='card' style={{ backgroundColor: "#2488ed", width: "400px" }}>
                                <div className='mt-4 d-flex flex-column align-items-center gap-4'>
                                    <div className='d-flex justify-content-center align-items-center gap-3'>
                                        <select className="form-select" style={{ width: "130px" }} onChange={handleDocumentChange}>
                                            <option value="" selected>Document</option>
                                            <option value="AADHAR">AADHAR</option>
                                            <option value="VOTERID">VOTERID</option>
                                            <option value="PAN">PAN</option>
                                            <option value="PASSPORT">PASSPORT</option>
                                            <option value="DRIVINGLICENCE">DRIVINGLICENCE</option>
                                        </select>
                                        <input type="text"
                                            placeholder='document no'
                                            className='form-control'
                                            style={{ width: "150px" }}
                                            onChange={(e) => setDocNumber(e.target.value)}
                                            value={docNumber}
                                        />
                                    </div>
                                    <input type="text"
                                        placeholder='registration no'
                                        className='form-control w-75'
                                        style={{ width: "150px" }}
                                        onChange={(e) => setRegNo(e.target.value)}
                                        value={regNo}
                                    />
                                    <select className='form-select w-75' value={installmentType} onChange={(e) => setInstallmentType(e.target.value)}>
                                        <option value="" selected>Installment Type</option>
                                        <option value="YEARLY">YEARLY</option>
                                        <option value="HALFYEARLY">HALFYEARLY</option>
                                        <option value="QUARTERLY">QUARTERLY</option>
                                        <option value="MONTHLY">MONTHLY</option>
                                    </select>
                                    <button className='btn btn-success w-50 mb-4' onClick={handlePolicyClick}>Get Policy</button>
                                </div>
                            </div>
                        </div>
                    )
                }

                {
                    info && (
                        <div className='d-flex justify-content-center align-items-center flex-column'>
                            <table className='table table-bordered mt-4'>
                                <thead>
                                    <tr>
                                        <th>Id</th>
                                        <th>Name</th>
                                        <th>vehicalType</th>
                                        <th>Registration No</th>
                                        <th>Installment Type</th>
                                        <th>Document Type</th>
                                        <th>Document Number</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>{policy.policyId}</td>
                                        <td>{policy.policyName}</td>
                                        <td>{policy.vehichleType}</td>
                                        <td>{regNo}</td>
                                        <td>{installmentType}</td>
                                        <td>{documentType}</td>
                                        <td>{docNumber}</td>
                                    </tr>
                                </tbody>
                            </table>
                           <div>
                             <button className='btn btn-success' onClick={handleClick}>Get Policy</button>
                             <button className='btn btn-danger ms-3' onClick={handleCancelClick}>Cancel</button>
                           </div>
                        </div>
                    )
                }

                {
                    showPay && (
                        <div className='card mt-5' style={{ width: "300px", height: "200px", backgroundColor: "#2488ed" }}>
                            <div className='d-flex justify-content-center rounded align-items-center flex-column mt-3 gap-3'>
                                <label className='fs-4 fw-bold' htmlFor="amount">{installmentType == "YEARLY" ? 'Toatl amount' : 'Installment amount'}</label>
                                <input type="text"
                                    className='form-control w-75'
                                    id='amount'
                                    value={amount} />
                                <button className='btn btn-success' onClick={handlePayClick}>Pay</button>
                            </div>
                        </div>
                    )
                }

            </div>
        </div>
    )
}

export default IssuePage

// YEARLY(1.0,1),
// 	HALFYEARLY(0.02,2),
// 	QUARTERLY(0.02,3),
// 	MONTHLY(0.02,12);