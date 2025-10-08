import { createContext, useState } from "react";

export const UserContext = createContext();

export const UserProvider = ({children}) => {
    const [user, setUser] = useState(null);
    const [policy, setPolicy] = useState(null);
    const [issuedPolicy, setIssuedPolicy] = useState(null);
    const [myPolicies, setMyPoliciies] = useState([]);
    const [token, setToken] = useState(null);

    return(
        <UserContext.Provider value={{user, setUser, policy, setPolicy, issuedPolicy, setIssuedPolicy, myPolicies, setMyPoliciies, token, setToken}}>
            {children}
        </UserContext.Provider>
    )
}