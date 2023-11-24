<script setup>
import { listArticle } from "@/api/tripboard";
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";

import VSelect from "@/components/common/VSelect.vue";
import TripBoardListItem from "@/components/tripboard/item/TripBoardListItem.vue";
import PageNavigation from "@/components/common/PageNavigation.vue";

const router = useRouter();

const selectOption = ref([
  { text: "검색조건", value: "" },
  { text: "해시태그", value: "tag_name" },
  { text: "제목", value: "subject" },
  { text: "작성자아이디", value: "user_id" },
]);

const sortOption = ref([
  { text: "정렬조건", value: "" },
  { text: "최신 순", value: "register_time" },
  { text: "조회 순", value: "hit" },
  { text: "비용 순", value: "cost" },
  { text: "추천 순", value: "total_clover" },
]);

const articles = ref([]);
const currentPage = ref(1);
const totalPage = ref(0);
const { VITE_ARTICLE_LIST_SIZE } = import.meta.env;
const param = ref({
  pgno: currentPage.value,
  spp: VITE_ARTICLE_LIST_SIZE,
  key: "",
  word: "",
  order: "",
});

onMounted(() => {
  getArticleList();
});

const changeKey = (val) => {
  console.log("BoarList에서 선택한 조건 : " + val);
  param.value.key = val;
};

const changeOrder = (val) => {
  console.log("BoarList에서 선택한 순서 : " + val);
  param.value.order = val;
};

const getArticleList = () => {
  console.log("서버에서 글목록 얻어오자!!!", param.value);
  // API 호출
  listArticle(
    param.value,
    ({ data }) => {
      console.log(data);
      articles.value = data.articles;
      currentPage.value = data.currentPage;
      totalPage.value = data.totalPageCount;
    },
    (error) => {
      console.log(error);
    }
  );
};

const onPageChange = (val) => {
  console.log(val + "번 페이지로 이동 준비 끝!!!");
  currentPage.value = val;
  param.value.pgno = val;
  getArticleList();
};

const moveWrite = () => {
  router.push({ name: "trip-article-write" });
};
</script>
<template>
  <div class="container">
    <div class="row justify-content-center mt-4">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">여행기록들</mark>
        </h2>
      </div>
      <div class="col-lg-10">
        <div class="row mb-4">
          <div class="col-md-2 text-start">
            <button
              type="button"
              class="btn btn-outline-primary btn-sm"
              @click="moveWrite"
            >
              글쓰기
            </button>
          </div>
          <div class="col-md-5 offset-md-5">
            <form class="d-flex">
              <VSelect :selectOption="sortOption" @onKeySelect="changeOrder" />
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

        <div class="row row-cols-1 row-cols-md-3 g-4">
          <TripBoardListItem
            v-for="article in articles"
            :key="article.tripArticleId"
            :article="article"
          />
        </div>
      </div>
      <PageNavigation
        :current-page="currentPage"
        :total-page="totalPage"
        @pageChange="onPageChange"
      ></PageNavigation>
    </div>
  </div>
</template>

<style scoped></style>
