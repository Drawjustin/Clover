<template>
  <div class="business-card-list">
    <router-link to="/userinfo">
      <img src="@/img/my_user.png" style="width: 50px; cursor: pointer" />
      <div style="cursor: pointer">계정 관리</div></router-link
    >

    <div>
      <!-- <p @click="viewlike()">내가 좋아요 누른 일정</p> -->
      <p @click="myboard()">내가 쓴 일정</p>
    </div>
    <div class="col-lg-10 mx-auto">
      <h2 class="my-3 py-3 shadow-sm bg-light text-center">
        <mark class="sky">여행기록들</mark>
      </h2>
    </div>

    <div class="col-lg-10 mx-auto">
      <div class="row mb-4">
        <div class="col-md-2 text-start"></div>
        <div class="col-md-5 offset-md-5">
          <form class="d-flex">
            <VSelect :selectOption="selectOption" @onKeySelect="changeKey" />
            <div class="input-group input-group-sm">
              <input
                type="text"
                class="form-control"
                v-model="param.word"
                placeholder="검색어..."
              />
              <button
                class="btn btn-dark"
                type="button"
                @click="getArticleList"
              >
                검색
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
    <!-- <div v-if="lengthOfBusinessCards && viewkey">
      <div class="cards-container">
        <thumbnail
          v-for="businessCard in businessCards"
          :key="businessCard.name"
          :business-card="businessCard"
          @delete-card-event="deleteCard"
        />
      </div>
    </div> -->
    <div class="row justify-content-center mt-4">
      <div class="row row-cols-1 row-cols-md-3 g-4">
        <TripBoardListItem
          v-for="article in articles"
          :key="article.tripArticleId"
          :article="article"
        />
      </div>
    </div>
  </div>
  <PageNavigation
    :current-page="currentPage"
    :total-page="totalPage"
    @pageChange="onPageChange"
  ></PageNavigation>
</template>

<script setup>
import thumbnail from "../components/thumbnail.vue";
import PageNavigation from "../components/common/PageNavigation.vue";
import { ref, computed, onMounted } from "vue";
import { listmylikepage, deletemylikepage } from "@/api/member.js";
import TripBoardListItem from "../components/tripboard/item/TripBoardListItem.vue";

import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const param = ref({
  //pgno: currentPage.value,
  // userId: userInfo.value.userId,
  pgno: 1,
  spp: VITE_ARTICLE_LIST_SIZE,
  key: "user_id",
  word: userInfo.value.userId,
  order: "",
  // sortkey: 1,
});
const viewkey = ref(false);

const lengthOfBusinessCards = computed(() => {
  // return articles.value.length;
});
const viewlike = () => {
  viewkey.value = true;
  getbusinessCards();
};

const myboard = () => {
  viewkey.value = false;
  getmybusinessCards();
};
const onPageChange = (val) => {
  console.log(val + "번 페이지로 이동 준비 끝!!!");
  currentPage.value = val;
  param.value.pgno = val;
  getbusinessCards();
};
const deleteCard = (target) => {
  target.userId = userInfo.value.userId;
  deletemylikepage(
    target,
    ({ data }) => {
      console.log("잘지워졌나요" + data);
      getbusinessCards();
    },
    (error) => {
      console.log(error);
    }
  );
};
onMounted(() => {
  getmybusinessCards();
});
const getbusinessCards = () => {
  param.value.sortkey = 1;
  listmylikepage(
    param.value,
    ({ data }) => {
      articles.value = data.articles;
      currentPage.value = data.currentPage;
      totalPage.value = data.totalPageCount;
      businessCards.value = articles.value;
    },
    (error) => {
      console.log(error);
    }
  );
};
const getmybusinessCards = () => {
  console.log("이거실행댐");
  param.value.sortkey = 2;
  listmylikepage(
    param.value,
    ({ data }) => {
      articles.value = data.articles;
      currentPage.value = data.currentPage;
      totalPage.value = data.totalPageCount;
      businessCards.value = articles.value;
    },
    (error) => {
      console.log(error);
    }
  );
};
const businessCards = ref([]);
</script>

<style scoped>
.business-card-list {
  text-align: center;
}

.cards-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  margin-top: 20px;
}
</style>
