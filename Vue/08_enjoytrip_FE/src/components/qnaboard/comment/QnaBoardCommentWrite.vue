<script setup>
import { registArticleComment } from "@/api/qnaboard";
import { ref } from "vue";

import { storeToRefs } from 'pinia';
import { useMemberStore } from '@/stores/member';
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);


const props = defineProps({
  qnaArticleId: Number,
});

const emit = defineEmits(["needUpdate"]);

const comment = ref({
  articleCommentId: "",
  qnaArticleId: props.qnaArticleId,
  qnaArticleCommentContent: "",
  userId: userInfo.value.userId,
  registerTime: "",
});

function writeArticleComment() {
  console.log("댓글등록하자!!", comment.value);
  // API 호출
  registArticleComment(
    comment.value,
    ({ data }) => {
      console.log("success");
      console.log(comment.value);
      emit("needUpdate");
      comment.value.qnaArticleCommentContent = "";
    },
    (error) => {
      console.log("error");
    }
  );
}
</script>

<template>
  <form @submit.prevent="writeArticleComment">
    <div class="mb-3">
      <img
        class="avatar me-2 float-md-start bg-light p-2"
        src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
      />
      <span class="fw-bold">{{ comment.userName }}</span> <br />
      <input
        type="text"
        class="form-control"
        v-model="comment.qnaArticleCommentContent"
        placeholder="댓글 입력"
      />
    </div>
    <div class="col-auto text-end">
      <button type="submit" class="btn btn-outline-primary mb-3">
        댓글 작성
      </button>
    </div>
  </form>
</template>

<style scoped></style>
