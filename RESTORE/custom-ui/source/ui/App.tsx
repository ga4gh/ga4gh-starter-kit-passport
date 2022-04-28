import React, { useState, useEffect } from 'react';
import {
    BrowserRouter,
    Routes,
    Route,
} from 'react-router-dom';
import {
    Login,
    Profile,
    Register
 } from './components/pages';

const App = () => {

    const useIsSsr = () => {
        const [isSsr, setIsSsr] = useState(true);

        useEffect(() => {
            setIsSsr(false);
        }, [])

        return isSsr;
    }

    const isSsr = useIsSsr();
    if (isSsr) return null;

    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Login />} />
                <Route path="/login" element={<Login />} />
                <Route path="/profile" element={<Profile />} />
                <Route path="/register" element={<Register />} />
            </Routes>
        </BrowserRouter>
    )
}

export default App;
