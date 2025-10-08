import React, { useContext } from 'react'
import Header from './Header'
import { UserContext } from '../context/UserContext'

function MyPolicies() {

    const { myPolicies } = useContext(UserContext);
    console.log(myPolicies);
    return (
        <div>
            <Header />
            {
                myPolicies.length === 0 && (
                    <div>
                        <span className='fw-bold text-center mt-5 text-danger'>No Policies are Issued</span>
                    </div>
                )
            }

            {
                myPolicies.length > 0 && (
                    <div>
                        <table className='table table-bordered mt-4'>
                            <thead>
                                <tr>
                                    <th>Id</th>
                                    <th>Name</th>
                                    <th>vehicalType</th>
                                    <th>Registration No</th>
                                    <th>Issued At</th>
                                    <th>Installment Type</th>
                                    <th>Installments Left</th>
                                    <th>Remaining amount</th>
                                    <th>Installment End Date</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    myPolicies.map((policy) => (
                                        <tr>
                                            <td>{policy.policyId}</td>
                                            <td>{policy.policyName}</td>
                                            <td>{policy.vehicalType}</td>
                                            <td>{policy.registrationNo}</td>
                                            <td>{policy.issuedAt}</td>
                                            <td>{policy.installmentType}</td>
                                            <td>{policy.installmentsLeft}</td>
                                            <td>{policy.remainingAmount}</td>
                                            <td>{policy.endDate}</td>
                                            <td></td>
                                        </tr>
                                    ))
                                }
                            </tbody>
                        </table>
                    </div>
                )
            }
        </div>
    )
}

export default MyPolicies
