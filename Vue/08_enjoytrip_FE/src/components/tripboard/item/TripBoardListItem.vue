<script setup>
import { defineProps, onMounted } from "vue";
const { VITE_ENJOYTRIP_API_URL } = import.meta.env;

const props = defineProps({ article: Object });
var vehicletags = [];

onMounted(() => {
  if (props.article.tags != null && props.article.tags != "") {
    vehicletags = props.article.tags
      .filter((item) => item.tagType === 1)
      .map((item) => item.tagName);
  }
  console.log(vehicletags);
});
</script>

<template>
  <div class="h-100">
    <div class="card h-100">
      <img
        :src="`${VITE_ENJOYTRIP_API_URL}/upload/file/${article.tripBoardFileDto.saveFolder}/${article.tripBoardFileDto.saveFile}`"
        class="card-img-top"
        alt="여행 이미지"
      />

      <div class="card-body">
        <h5 class="card-title" id="title">{{ article.subject }}</h5>
        <p class="card-text">
          {{ article.userName }} <br />
          비용: {{ article.cost }} | 조회수: {{ article.hit }} | 클로버 수:
          {{ article.totalClover }} <br />

          {{ article.registerTime }}
        </p>

        <!-- <img
          v-for="vehicle in vehicletags"
          :key="vehicle"
          :src="require(`@img/${vehicle}.png`)"
        /> -->
        <span v-for="vehicle in vehicletags" :key="vehicle">
          <!-- <img :src="require(`@img/${vehicle}.png`).default" /> -->

          {{ vehicle }}
        </span>
        <br />

        <router-link
          :to="{
            name: 'trip-article-view',
            params: { tripArticleId: article.tripArticleId },
          }"
        >
          <button class="btn btn-primary">자세히 보기</button>
        </router-link>
      </div>
    </div>
  </div>

  <!-- <tr class="text-center">
    <th scope="row">{{ article.tripArticleId }}</th>
    <td class="text-start">
      <router-link
        :to="{
          name: 'trip-article-view',
          params: { tripArticleId: article.tripArticleId },
        }"
        class="article-title link-dark"
      >
        {{ article.subject }}
      </router-link>
    </td>
    <td>{{ article.userName }}</td>
    <td>{{ article.hit }}</td>
    <td>{{ article.registerTime }}</td>
  </tr>
  클로버 수 : {{ article.totalClover }} <br />
  비용 : {{ article.cost }} <br />
  태그: {{ article.tags }}
  <img
    :src="`${VITE_ENJOYTRIP_API_URL}/upload/file/${article.tripBoardFileDto.saveFolder}/${article.tripBoardFileDto.saveFile}`"
  /> -->
</template>

<style scoped>
a {
  text-decoration: none;
}

#title {
  white-space: no wrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 300px;
}
</style>
