import { stationAxios } from "@/util/http-commons";

const station = stationAxios();

function listStations(param, success, fail) {
  station.get("", { params: param }).then(success).catch(fail);
}

export { listStations };
