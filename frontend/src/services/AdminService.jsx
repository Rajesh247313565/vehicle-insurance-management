
import React from "react"
import API from "../server/API"


export const CreatePolicy = async (policyName, ploicyAmt, vehicleType, description) => {

    try {
        const res = API.post('/admin/createpolicy', {
            policyName: policyName,
            policyAmount: ploicyAmt,
            vehichleType: vehicleType,
            description: description
        })
        return res.data
    } catch (error) {
        const message = error.response?.data?.message;
        throw new Error(message)
    }
}