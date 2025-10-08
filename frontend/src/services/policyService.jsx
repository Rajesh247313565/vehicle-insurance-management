import API from "../server/API"

export const getPolicies = async () => {
    try {
        const res = await API.get('/policy/getAllPolicies');
        return res.data;
    } catch (error) {
        const message = error.response?.data?.message;
        throw new Error(message)
    }
}

export const getPolicyAmount = async (policyId, installmentType) => {
    try {
        const res = await API.post('/policy/getPolicyAmount', {
            policyId,
            installmentType
        })
    } catch (error) {
        const message = error.response?.data?.message;
        throw new Error(message)
    }
}

export const issuePolicy = async (data) => {
    try {
        console.log("Sending issueData:", data);
        const res = await API.post('/user/issuePolicy', {
            userId: data.userId,
            policyId: data.policyId,
            vehicalType: data.vehicalType,
            documentType: data.documentType,
            regNo: data.regNo,
            documentNumber: data.documentNumber,
            installmentType: data.installmentType,
            amountPaid: data.amountPaid
        });

        return res.data;
    } catch (error) {
        const message = error.response?.data?.message;
        throw new Error(message)
    }
}

export const getMyPolicies = async (userId) => {
    try {
        const res = await API.get(`/user/myPolicies/${userId}`)
        return res.data;
    } catch (error) {
        const message = error.response?.data?.message;
        throw new Error(message)
    }
}