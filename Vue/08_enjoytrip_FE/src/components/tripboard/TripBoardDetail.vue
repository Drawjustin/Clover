<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle } from "@/api/tripboard";
import TripBoardCommentList from "./comment/TripBoardCommentList.vue";
import { storeToRefs } from "pinia";
import { useMemberStore } from "@/stores/member";
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
const { VITE_ENJOYTRIP_API_URL } = import.meta.env;

const route = useRoute();
const router = useRouter();

const { tripArticleId } = route.params;

const tripArticleIdNum = Number(tripArticleId);

var vehicletags = ref([]);
var hashtags = ref([]);

const article = ref({
  tripArticleId: 0,
  subject: "",
  content: "",
  userId: "",
  userName: "",
  hit: 0,
  registerTime: "",
  totalClover: 0,
  cost: 0,
  tripBoardFileDto: null,
  tags: null,
});

onMounted(() => {
  getArticle();
});

const getArticle = () => {
  console.log(tripArticleId + "번글 얻으러 가자!!!");
  // API 호출
  detailArticle(
    tripArticleId,
    ({ data }) => {
      console.log(data);
      console.log("success");
      article.value = data;
      console.log(article.value);

      if (article.value.tags != null && article.value.tags != "") {
        article.value.tags.forEach((item) => {
          if (item.tagType === 1) {
            vehicletags.value.push(item.tagName);
            console.log(item.tagName);
          } else if (item.tagType === 2) {
            hashtags.value.push(item.tagName);
            console.log(item.tagName);
          }
        });
      }
    },
    (error) => {
      console.log("error");
    }
  );
};

function moveList() {
  router.push({ name: "trip-article-list" });
}

function moveModify() {
  router.push({ name: "trip-article-modify", params: { tripArticleId } });
}

function onDeleteArticle() {
  // const { articleno } = route.params;
  console.log(tripArticleId + "번글 삭제하러 가자!!!");
  // API 호출
  deleteArticle(
    tripArticleId,
    ({ data }) => {
      console.log("success");
      moveList();
    },
    (error) => {
      console.log("error");
    }
  );
}
</script>
<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">글보기</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <div class="row my-2">
          <h2 class="text-secondary px-5">
            {{ article.subject }}
          </h2>
        </div>
        <div class="row">
          <div class="col-md-8">
            <div class="clearfix align-content-center">
              <img
                class="avatar me-2 float-md-start bg-light p-2"
                src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
              />
              <p>
                <span class="fw-bold">{{ article.userName }}</span> <br />
                <span class="text-secondary fw-light">
                  {{ article.registerTime }}조회 : {{ article.hit }}
                </span>
              </p>
            </div>
          </div>
          <div>
            이동수단:
            <span v-for="vehicletag in vehicletags" :key="vehicletag">
              #{{ vehicletag }}&nbsp;&nbsp;</span
            ><br />
            가격: {{ article.cost }}
          </div>

          <div class="divider mb-3"></div>
          <div class="text-secondary">
            <div v-html="article.content"></div>
            <div>
              <span v-for="hashtag in hashtags" :key="hashtag">
                #{{ hashtag }}&nbsp;&nbsp;</span
              >
            </div>
          </div>
          <div class="divider mt-3 mb-3"></div>
          <div class="d-flex justify-content-end">
            <button
              type="button"
              class="btn btn-outline-primary mb-3"
              @click="moveList"
            >
              글목록
            </button>
            <button
              v-if="article.userId == userInfo.userId"
              type="button"
              class="btn btn-outline-success mb-3 ms-1"
              @click="moveModify"
            >
              글수정
            </button>
            <button
              v-if="article.userId == userInfo.userId"
              type="button"
              class="btn btn-outline-danger mb-3 ms-1"
              @click="onDeleteArticle"
            >
              글삭제
            </button>
          </div>
        </div>
        <hr />
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="sky">댓글 </mark>
        </h2>
        <br />
        <TripBoardCommentList :trip-article-id="tripArticleIdNum" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
