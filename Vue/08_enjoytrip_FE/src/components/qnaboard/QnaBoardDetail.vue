<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { detailArticle, deleteArticle } from "@/api/qnaboard";
import QnaBoardCommentList from "./comment/QnaBoardCommentList.vue";

import { storeToRefs } from 'pinia';
import { useMemberStore } from '@/stores/member';
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
const route = useRoute();
const router = useRouter();

const { qnaArticleId } = route.params;

const qnaArticleIdNum = Number(qnaArticleId);

const article = ref({
  qnaArticleId: 0,
  subject: "",
  content: "",
  userId: "",
  hit: 0,
  registerTime: "",
});

onMounted(() => {
  getArticle();
});

const getArticle = () => {
  console.log(qnaArticleId + "번글 얻으러 가자!!!");
  // API 호출
  detailArticle(
    qnaArticleId,
    ({ data }) => {
      console.log(data);
      console.log("success");
      article.value = data;
      console.log(article.value);
    },
    (error) => {
      console.log("error");
    }
  );
};

function moveList() {
  router.push({ name: "qna-article-list" });
}

function moveModify() {
  router.push({ name: "qna-article-modify", params: { qnaArticleId } });
}

function onDeleteArticle() {
  // const { articleno } = route.params;
  console.log(qnaArticleId + "번글 삭제하러 가자!!!");
  // API 호출
  deleteArticle(
    qnaArticleId,
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
            {{ article.qnaArticleId }}. {{ article.subject }}
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

          <div class="divider mb-3"></div>
          <div class="text-secondary">
            {{ article.content }}
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
            <button v-if="article.userId == userInfo.userId"
              type="button"
              class="btn btn-outline-success mb-3 ms-1"
              @click="moveModify"
            >
              글수정
            </button>
            
            <button v-if="article.userId == userInfo.userId"
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
        <QnaBoardCommentList :qna-article-id="qnaArticleIdNum" />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
