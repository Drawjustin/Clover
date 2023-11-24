<script setup>
import { storeToRefs } from 'pinia';
import { useMemberStore } from '@/stores/member';
const memberStore = useMemberStore();
const { userInfo, isValidToken } = storeToRefs(memberStore);
const { userLogout } = memberStore;
const logout = () => {
  userLogout();
};
</script>

<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary sticky-top">
    <div class="container-fluid">
      <router-link :to="{ name: 'main' }" class="navbar-brand">
        <img src="@/img/logo.png" class="rounded mx-auto d-block" alt="..." />
      </router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarScroll" aria-controls="navbarScroll" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarScroll">
        <ul class="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll" style="--bs-scroll-height: 100px">
          <!-- <li class="nav-item">
            <a class="nav-link" href="#">마이캠퍼스</a>
          </li> -->

          <li class="nav-item">
            <router-link :to="{ name: 'map' }" class="nav-link">지도</router-link>
          </li>
          <li class="nav-item">
            <router-link :to="{ name: 'qnaboard' }" class="nav-link"
              >QnA</router-link
            >
          </li>
          <li class="nav-item">
            <router-link :to="{ name: 'tripboard' }" class="nav-link"
              >여행 기록</router-link
            >
          </li>
          <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false"> HELP DESK </a>
            <ul class="dropdown-menu">
              <li>
                <router-link to="/helpdesk"><a class="dropdown-item" style="text-decoration: none">공지사항</a></router-link>
              </li>
              <li><a class="dropdown-item" href="#">Event</a></li>
              <!-- <li><hr class="dropdown-divider" /></li> -->
            </ul>
          </li>
        </ul>
        <form class="d-flex">
          <router-link :to="{ name: 'mypage' }" class="nav-link"> <button class="btn btn-outline-success" type="button">마이페이지</button></router-link>
        </form>
        <form class="d-flex" v-if="!userInfo && !isValidToken">
          <router-link :to="{ name: 'loginPage' }" class="nav-link"> <button class="btn btn-outline-success" type="button">Login</button></router-link>
        </form>
        <form class="d-flex" v-else>
          <router-link :to="{ name: 'main' }" class="nav-link"> <button class="btn btn-outline-success" type="button" @click="logout()">Logout</button></router-link>
        </form>
      </div>
    </div>
  </nav>
</template>

<style scoped>
img {
  width: 50px;
  margin-bottom: 10px;
}
</style>
