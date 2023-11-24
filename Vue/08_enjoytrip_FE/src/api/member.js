import { stationAxios } from "@/util/http-commons.js";

const local = stationAxios();
function listmylikepage(param, success, fail) {
  // local.get(`/user/Mylikepage`, { params: param }).then(success).catch(fail);
  local.get(`/tripboard`, { params: param }).then(success).catch(fail);
}

function deletemylikepage(param, success, fail) {
  local.delete(`/user/Mylikepage`, { params: param }).then(success).catch(fail);
}
async function tokenRegeneration(user, success, fail) {
  local.defaults.headers["refreshToken"] =
    sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`/user/refresh`, user).then(success).catch(fail);
}

async function userConfirm(param, success, fail) {
  await local.post(`/user/login`, param).then(success).catch(fail);
}
async function registerpage(param, success, fail) {
  await local
    .post(`/user/register`, JSON.stringify(param))
    .then(success)
    .catch(fail);
}

async function findById(userid, success, fail) {
  local.defaults.headers["Authorization"] =
    sessionStorage.getItem("accessToken");
  await local.get(`/user/info/${userid}`).then(success).catch(fail);
}

async function logout(userid, success, fail) {
  await local.get(`/user/logout/${userid}`).then(success).catch(fail);
}

export {
  listmylikepage,
  deletemylikepage,
  userConfirm,
  registerpage,
  tokenRegeneration,
  findById,
  logout,
};
