<script setup>
import { deleteArticleComment } from "@/api/qnaboard";
import { useRoute, useRouter } from "vue-router";

import { storeToRefs } from 'pinia';
import { useMemberStore } from '@/stores/member';
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);
const router = useRouter();


// defineProps({
//   comment: Object,
// });

const props = defineProps({
  comment: Object,
});
const emit = defineEmits(["needUpdate"]);

function onDeleteArticleComment() {
  console.log(props.comment.qnaArticleCommentId + "번 댓글 삭제하러 가자!!!");
  // API 호출
  deleteArticleComment(
    props.comment.qnaArticleCommentId,
    ({ data }) => {
      console.log("success");
      emit("needUpdate");
    },
    (error) => {
      console.log("error");
    }
  );
}
</script>

<template>
  <div class="row">
    <div class="col-md-8">
      <div class="clearfix align-content-center">
        <img
          class="avatar me-2 float-md-start bg-light p-2"
          src="https://raw.githubusercontent.com/twbs/icons/main/icons/person-fill.svg"
        />
        <p>
          <span class="fw-bold">{{ comment.userName }}</span> <br />
          <span class="text-secondary fw-light">
            {{ comment.registerTime }}
          </span>
          <span class="d-flex justify-content-end"> </span>
        </p>
      </div>
    </div>
    <div class="divider mb-3"></div>
    <div class="text-secondary">
      {{ comment.qnaArticleCommentContent }}
    </div>
    <div class="d-flex justify-content-end">
      <button
        v-if="userInfo.userName == comment.userName"
        type="button"
        class="btn btn-outline-danger mb-3 ms-1"
        @click="onDeleteArticleComment"
      >
        댓글 삭제
      </button>
    </div>
  </div>
  <hr />
</template>

<style scoped></style>
