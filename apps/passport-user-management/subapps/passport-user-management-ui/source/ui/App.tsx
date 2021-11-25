import React, { useState, useEffect } from 'react';
import {
    BrowserRouter,
    Routes,
    Route,
} from 'react-router-dom';
import Home from './components/pages/Home';
import PageA from './components/pages/PageA';
import PageB from './components/pages/PageB';

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
                <Route path="/" element={<Home />} />
                <Route path="/a" element={<PageA />} />
                <Route path="/b" element={<PageB />} />
            </Routes>
        </BrowserRouter>
    )
}

export default App;
