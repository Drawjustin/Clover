import {stationAxios} from "@/util/http-commons.js"

const local = stationAxios();

function listSido(success,fail){
    local.get(`/attraction/sido`).then(success).catch(fail);
}
function listGugun(param, success, fail) {
    local.get(`/attraction/gugun`, { params: param }).then(success).catch(fail);
}
async function listTriparea(param,success,fail){
   await local.get(`/attraction/search`, {params : param} ).then(success).catch(fail);
}

export{listSido,listGugun,listTriparea};
