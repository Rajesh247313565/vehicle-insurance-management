import API from '../server/API'

export const loginUser = async (email, password) => {
    try {
        const res = await API.post('/user/login', {
            email: email,
            password: password
        })

        return res.data;
    } catch (error) {
        if (error.response && error.response.data) {
            const data = error.response.data;
            throw {
                fieldErrors: data.fieldErrors || null,
                message: data.message || data.error || "Something went wrong",
            };
        }
        throw { message: "Network error" };
    }
}

export const registerUser = async (name, email, password) => {
    try {
        const res = await API.post('/admin/registerUser', {
            userName: name,
            email: email,
            password: password
        })

        return res.data;
    } catch (error) {
        if (error.response && error.response.data) {
            const data = error.response.data;

            throw {
                fieldErrors: data.fieldErrors || null,
                message: data.message || data.error || "Something went wrong",
            };
        }
        throw { message: "Network error" };
    }
}