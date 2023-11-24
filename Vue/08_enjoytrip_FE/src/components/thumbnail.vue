<template>
    <!-- <div class="business-card-detail">
      <h3>여행 계획 : {{ businessCard.subject }}</h3>
      <p>글쓴이 : {{ businessCard.userId }}</p>
      <p>좋아요 : {{ businessCard.totalClover }}</p>
      <button @click="deleteCardFunc(businessCard)">계획 삭제</button>
    </div> -->

    <div class="business-card-detail">
    <router-link :to="{
          name: 'trip-article-view',
          params: { tripArticleId: businessCard.tripArticleId },
        }">
            <div class="card h-100">
              <img src="../../img/data/경복궁.jpeg" class="card-img-top" alt="여행 이미지" />
              <div class="card-body">
                <h5 class="card-title">제목 : {{ businessCard.title }}</h5>
                <p class="card-text">{{ businessCard.author }} | 조회수: {{ businessCard.views }} | {{ businessCard.date }}</p>
                <button class="btn btn-primary">자세히 보기</button>
              </div>
            </div>
          </router-link>
    </div>
  </template>
  
  <script setup>
  defineProps({
    businessCard: Object
  })
  
  const emit = defineEmits(['deleteCardEvent'])
  const deleteCardFunc = function (targetCard) {
    emit('deleteCardEvent', targetCard)
  }
  
  </script>
  
  <style>
  .business-card-detail {
    width: 40%;
    margin: 20px;
    border: 1px solid #ccc;
    background-color: #f9f9f9;
  }
  </style>
  