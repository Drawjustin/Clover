<script setup>
import { ref, watch } from "vue";
import { useRoute, useRouter } from "vue-router";
import { modifyArticle, registArticle, detailArticle } from "@/api/qnaboard";
import { storeToRefs } from 'pinia';
import { useMemberStore } from '@/stores/member';
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
const router = useRouter();
const route = useRoute();

const props = defineProps({ type: String });

const isUseId = ref(true);

const article = ref({
  qnaArticleId: 0,
  subject: "",
  content: "",
  userId: userInfo.value.userId,
  userName: "",
  hit: 0,
  registerTime: "",
});
if (props.type === "modify") {
  let { qnaArticleId } = route.params;
  console.log(qnaArticleId + "번글 얻어와서 수정할거야");
  // API 호출
  isUseId.value = true;
  article.value.qnaArticleId = qnaArticleId;
  detailArticle(
    qnaArticleId,
    ({ data }) => {
      console.log(data);
      console.log("success");
      article.value.qnaArticleId = data.qnaArticleId;
      article.value.subject = data.subject;
      article.value.content = data.content;
      article.value.userId = data.userId;
      article.value.hit = data.hit;
      article.value.registerTime = data.registerTime;
    },
    (error) => {
      console.log("error");
    }
  );
}

const subjectErrMsg = ref("");
const contentErrMsg = ref("");
watch(
  () => article.value.subject,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 30) {
      subjectErrMsg.value = "제목을 확인해 주세요!!!";
    } else subjectErrMsg.value = "";
  },
  { immediate: true }
);
watch(
  () => article.value.content,
  (value) => {
    let len = value.length;
    if (len == 0 || len > 500) {
      contentErrMsg.value = "내용을 확인해 주세요!!!";
    } else contentErrMsg.value = "";
  },
  { immediate: true }
);

function onSubmit() {
  // event.preventDefault();
  if (subjectErrMsg.value) {
    alert(subjectErrMsg.value);
  } else if (contentErrMsg.value) {
    alert(contentErrMsg.value);
  } else {
    props.type === "regist" ? writeArticle() : updateArticle();
  }
}

function writeArticle() {
  console.log("글등록하자!!", article.value);
  // API 호출
  registArticle(
    article.value,
    ({ data }) => {
      console.log("success");
      moveList();
    },
    (error) => {
      console.log("error");
    }
  );
}

function updateArticle() {
  console.log(article.value.qnaArticleId + "번글 수정하자!!", article.value);
  // API 호출
  modifyArticle(
    article.value,
    ({ data }) => {
      console.log("success");
      moveDetail();
    },
    (error) => {
      console.log("error");
    }
  );
}

function moveList() {
  router.push({ name: "qna-article-list" });
}

function moveDetail() {
  router.push({
    name: "qna-article-view",
    params: { qnaArticleId: article.value.qnaArticleId },
  });
}
</script>

<template>
  <form @submit.prevent="onSubmit">
    <div class="mb-3">
      <label for="userid" class="form-label">작성자 ID : </label>
      <input
        type="text"
        class="form-control"
        v-model="article.userId"
        :placeholder="article.userId"
        :disabled="isUseId"
      />
    </div>
    <div class="mb-3">
      <label for="subject" class="form-label">제목 : </label>
      <input
        type="text"
        class="form-control"
        v-model="article.subject"
        placeholder="제목..."
      />
    </div>
    <div class="mb-3">
      <label for="content" class="form-label">내용 : </label>
      <textarea
        class="form-control"
        v-model="article.content"
        rows="10"
      ></textarea>
    </div>
    <div class="col-auto text-center">
      <button
        type="submit"
        class="btn btn-outline-primary mb-3"
        v-if="type === 'regist'"
      >
        글작성
      </button>
      <button type="submit" class="btn btn-outline-success mb-3" v-else>
        글수정
      </button>
      <button
        type="button"
        class="btn btn-outline-danger mb-3 ms-1"
        @click="moveList"
      >
        목록으로이동...
      </button>
    </div>
  </form>
</template>

<style scoped></style>
