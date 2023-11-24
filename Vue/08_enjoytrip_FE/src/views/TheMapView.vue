<script setup>
import { ref, onMounted } from 'vue';
import { listSido, listGugun, listTriparea } from '@/api/map';
import VKakaoMap from '../components/kakaomap/VKakaoMap.vue';

const gugunList = ref([{ text: '구군선택', value: '' }]);
const sidoList = ref([{ text: '시도선택', value: '' }]);

const sidokey = ref('');

const gugunkey = ref('');
const checkedbox = ref([]);
const searchtext = ref('');

const chargingStations = ref([]);
const selectStation = ref({});

const getSidoList = () => {
  listSido(
    ({ data }) => {
      let options = [];
      options.push({ text: '시도선택', value: '' });
      data.forEach((gugun) => {
        options.push({ text: gugun.sidoName, value: gugun.sidoCode });
      });
      sidoList.value = options;
      console.log(data);
      console.log('정상데이터');
    },
    (err) => {
      console.log(err);
      console.log('에러');
    }
  );
};
const onChangeSido = (val) => {
  console.log('onChangeSido 실행');
  listGugun(
    { sido: val },
    ({ data }) => {
      let options = [];
      options.push({ text: '구군선택', value: '' });
      data.forEach((gugun) => {
        options.push({ text: gugun.gugunName, value: gugun.gugunCode });
      });
      gugunList.value = options;
    },
    (err) => {
      console.log(err);
    }
  );
};

const SearchMap = () => {
  chargingStations.value = [];

  // const selectedSidoCode = sidoSelect.options[sidoSelect.selectedIndex].value;
  // const selectedSigunguCode = sigunguSelect.options[sigunguSelect.selectedIndex].value;
  // const checkedContentType = Array.from(
  // document.getElementsByClassName("checkbox")).filter(
  //   (option) => option.checked)
  //   .map(option => Number(option.value));

  const searchAttractionDto = {
    sidoCode: Number(sidokey.value),
    gugunCode: Number(gugunkey.value),
    contentType: checkedbox.value.join(),
    searchWord: searchtext.value
  };
  //const param = [{sido : selectedSidoCode },{gugun : selectedSigunguCode , contentType : checkedContentType}]
  //const Tripdto = `["sidoCode" : "${selectedSidoCode}" ,"gugunCode" : "${selectedSigunguCode}", "contentType" : "${checkedContentType}", "searchWord" : ""]`;
  // const Tripdto = `{sidoCode : ${selectedSidoCode}},{gugunCode :${selectedSigunguCode} },{contentType :${checkedContentType} },{searchWord : ""}`
  //const Tripdto = {sidoCode : selectedSidoCode}
  //const Tripdto = `sidoCode=${selectedSidoCode}&gugunCode=${selectedSigunguCode}&contentType=${checkedContentType}&searchWord=""`;
  //    const tripdto = {
  //         sidoCode: selectedSidoCode,
  //         gugunCode: selectedSigunguCode,
  //         contentType: checkedContentType,
  //         searchWord: "123"
  //       };
  //   console.log(tripdto);

  listTriparea(
    searchAttractionDto,
    ({ data }) => {
      console.log(data);
      console.log('정상');
      chargingStations.value = data;
    },
    (err) => {
      console.log(err);
      console.log('에러');
    }
  );
  //getTripStations();
};

const viewStation = (station) => {
  console.log(station.value);
  console.log(chargingStations.value);
  selectStation.value = station;
};
// const getTripStations = () => {
//   listStations(
//     param.value,
//     ({ data }) => {
//       chargingStations.value = data.items[0].item;
//     },
//     (err) => {
//       console.log(err);
//     }
//   );
// };

onMounted(() => {
  getSidoList();
});
</script>

<template>
  <div>
    <div>
      <select id="sidoSelect" v-model="sidokey" @change="onChangeSido(sidokey)" style="margin: 10px; margin-bottom: 0px">
        <option v-for="item in sidoList" :key="item.value" :value="item.value" :sidokey="item.sidoCode">
          {{ item.text }}
        </option>
      </select>
      <select id="sigunguSelect" v-model="gugunkey">
        <option v-for="item in gugunList" :key="item.value" :value="item.value">
          {{ item.text }}
        </option>
      </select>
    </div>
    <div style="margin-left: 10px; margin-top: 10px">
      <input class="checkbox" type="checkbox" name="관광지" id="관광지" value="12" v-model="checkedbox" />
      <label for="관광지" class="checkbox-label">관광지</label>
      <input class="checkbox" type="checkbox" name="문화시설" id="문화시설" value="14" v-model="checkedbox" />
      <label for="문화시설" class="checkbox-label">문화시설</label>
      <input class="checkbox" type="checkbox" name="행사/공연/축제" id="행사/공연/축제" value="15" v-model="checkedbox" />
      <label for="행사/공연/축제" class="checkbox-label">행사/공연/축제</label>
      <input class="checkbox" type="checkbox" name="여행코스" id="여행코스" value="25" v-model="checkedbox" />
      <label for="여행코스" class="checkbox-label">여행코스</label>
      <input class="checkbox" type="checkbox" name="레포츠" id="레포츠" value="28" v-model="checkedbox" />
      <label for="레포츠" class="checkbox-label">레포츠</label>
      <input class="checkbox" type="checkbox" name="숙박" id="숙박" value="32" v-model="checkedbox" />
      <label for="숙박" class="checkbox-label">숙박</label>
      <input class="checkbox" type="checkbox" name="쇼핑" id="쇼핑" value="38" v-model="checkedbox" />
      <label for="쇼핑" class="checkbox-label">쇼핑</label>
      <input class="checkbox" type="checkbox" name="음식점" id="음식점" value="39" v-model="checkedbox" />
      <label for="음식점" class="checkbox-label">음식점</label>
      <button type="button" id="searchBtn" @click="SearchMap" style="margin-left: 10px">조회하기</button>
      <input type="text" v-model="searchtext" />
    </div>
    <hr style="margin-top: 0" />
    <div></div>
    <VKakaoMap :stations="chargingStations" :selectStation="selectStation" />
    <table class="table table-hover">
      <thead>
        <tr class="text-center">
          <th scope="col">제목</th>
          <th scope="col">지역</th>
          <th scope="col">위도</th>
          <th scope="col">경도</th>
        </tr>
      </thead>
      <tbody>
        <tr class="text-center" v-for="station in chargingStations" :key="station.contentId" @click="viewStation(station)">
          <th>{{ station.title }}</th>
          <th>{{ station.addr1 + station.addr2 }}</th>
          <td>{{ station.latitude }}</td>
          <td>{{ station.longitude }}</td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.checkbox {
  margin-right: 5px;
}

.checkbox-label {
  margin-right: 7px;
}
</style>
