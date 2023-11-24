<script setup>
import { ref, onMounted } from "vue";
import { listArticleComment } from "@/api/qnaboard";
import QnaBoardCommentItem from "./QnaBoardCommentItem.vue";
import QnaBoardCommentWrite from "./QnaBoardCommentWrite.vue";

const articleComments = ref([]);

const props = defineProps({
  qnaArticleId: Number,
});

onMounted(() => {
  getArticleComment();
});

const getArticleComment = () => {
  console.log(props.qnaArticleId + "번글의 댓글 얻으러 가자!!!");
  listArticleComment(
    props.qnaArticleId,
    ({ data }) => {
      articleComments.value = data;
    },
    (error) => {
      console.log("error");
    }
  );
};
</script>

<template>
  <QnaBoardCommentItem
    v-for="comment in articleComments"
    :key="comment.qnaArticleCommentId"
    :comment="comment"
    @need-update="getArticleComment()"
  />
  <QnaBoardCommentWrite
    :qna-article-id="props.qnaArticleId"
    @need-update="getArticleComment()"
  />
</template>

<style scoped></style>
