<script setup>
import { ref, onMounted } from "vue";
import { listArticleComment } from "@/api/tripboard";
import TripBoardCommentItem from "./TripBoardCommentItem.vue";
import TripBoardCommentWrite from "./TripBoardCommentWrite.vue";

const articleComments = ref([]);

const props = defineProps({
  tripArticleId: Number,
});

onMounted(() => {
  getArticleComment();
});

const getArticleComment = () => {
  console.log(props.tripArticleId + "번글의 댓글 얻으러 가자!!!");
  listArticleComment(
    props.tripArticleId,
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
  <TripBoardCommentItem
    v-for="comment in articleComments"
    :key="comment.tripArticleCommentId"
    :comment="comment"
    @need-update="getArticleComment()"
  />
  <TripBoardCommentWrite
    :trip-article-id="props.tripArticleId"
    @need-update="getArticleComment()"
  />
</template>

<style scoped></style>
