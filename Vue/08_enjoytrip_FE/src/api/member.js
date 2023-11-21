import {stationAxios} from "@/util/http-commons.js"
const local = stationAxios();
function listmylikepage(param, success, fail) {
    local.get(`/user/Mylikepage`, { params: param }).then(success).catch(fail);
}

function deletemylikepage(param, success, fail) {
    console.log(param);
    local.delete(`/user/Mylikepage`, { params: param }).then(success).catch(fail);
}
export {listmylikepage,deletemylikepage};