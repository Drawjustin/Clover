import axios from "axios";

const {VITE_ENJOYTRIP_API_URL} = import.meta.env;

function stationAxios(){
    const instance = axios.create({
        baseURL: VITE_ENJOYTRIP_API_URL,
        headers: {
            "Content-Type":"application/json;charset=utf-8",
        },
    });
    return instance;
}



export {stationAxios};