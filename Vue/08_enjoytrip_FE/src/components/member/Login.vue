<script setup>
import { useMemberStore } from '@/stores/member';
import { ref } from 'vue';

import { storeToRefs } from 'pinia';
import { useRouter } from 'vue-router';

const router = useRouter();

const memberStore = useMemberStore();

const { isLogin, isLoginError } = storeToRefs(memberStore);
console.log('login접속');

const { userLogin, getUserInfo } = memberStore;
const userId = ref('');
const userPass = ref('');

const login = async () => {
  const param = {
    userId: userId.value,
    userPass: userPass.value
  };
  await userLogin(param);
  let token = sessionStorage.getItem('accessToken');
  if (isLogin.value) {
    getUserInfo(token);
    alert('로그인 되었습니다.'); //로그인
    router.push('/');
  }
  if (isLoginError.value) {
    alert('로그인 실패했습니다.'); //로그인 실패
  }
};
</script>

<template>
  <link rel="stylesheet" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous" />
  <body>
    <div class="container">
      <div class="input-form-backgroud row">
        <div class="input-form col-md-12 mx-auto">
          <h4 class="mb-3">로그인</h4>
          <form class="validation-form" action="http://localhost:8080/enjoytrip/user/login" method="post" novalidate>
            <div class="row">
              <div class="mb-3">
                <label for="name">아이디</label>
                <input type="text" class="form-control" id="userId" placeholder="ID" required v-model="userId" />
                <div class="invalid-feedback">아이디 입력해주세요.</div>
              </div>
            </div>
            <div class="mb-3">
              <label for="password">비밀번호</label>
              <input type="password" class="form-control" id="userPass" placeholder="Password" required v-model="userPass" />
              <div class="invalid-feedback">비밀번호를 입력해주세요.</div>
            </div>
            <div class="mb-3 forget-pass" style="text-align: center">
              <router-link to="/findId" style="float: left; color: grey">아이디 찾기</router-link>
              <label for="name"> </label>
              <router-link to="/findPass" style="float: right; color: grey">비밀번호 찾기</router-link>
            </div>
            <div class="mb-3" style="justify-content: center">
              <button class="btn btn-outline-success" type="submit" @click.prevent="login()">로그인</button>
            </div>
            <router-view></router-view>

            <!-- router-link 테그는 페이지 이동을 위한 링크 태그 -->
            <div></div>
            <div class="mb-3"><label for="name">아직 회원이 아니신가요?</label><router-link to="/register" style="color: orange">회원가입</router-link></div>
          </form>
        </div>
      </div>
      <footer class="my-3 text-center text-small">
        <p class="mb-1">© 2023 YD</p>
      </footer>
    </div>
  </body>
</template>

<style scoped>
body {
  min-height: 100vh;
  background-color: #f7f8f9;
  /* background: -webkit-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%); */
  /* background: -moz-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
  background: -o-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%); */
  /* background: linear-gradient(to top right, #92b5db 0%, #1d466c 100%); */
}

.input-form {
  max-width: 350px;

  margin-top: 80px;
  padding: 32px;

  background: #fff;
  -webkit-border-radius: 10px;
  -moz-border-radius: 10px;
  border-radius: 10px;
  -webkit-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
  -moz-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
  box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
}
</style>
