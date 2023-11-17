import {stationAxios} from "@/util/http-commons.js"

const local = stationAxios();

function listSido(success,fail){
    local.get(`/attraction/sido`).then(success).catch(fail);
}
function listGugun(param, success, fail) {
    local.get(`/attraction/gugun`, { params: param }).then(success).catch(fail);
}


export{listSido,listGugun};
