<script>
console.log("findId접속");
import axios from "axios";

export default {
  data: function () {
    return {
      userId: "",
      userEmail: "",
      userQuestionResult: "",
      responeData: null,
      isFind: 0,
    };
  },
  methods: {
    submitForm: async function () {
      console.log(this.userId, this.userEmail);
      console.log(this.isFind);
      var url = "http://localhost:8080/enjoytrip/user/findPass";
      var data = {
        userId: this.userId,
        userEmail: this.userEmail,
        userQuestionResult: this.userQuestionResult,
      };
      this.responeData = await axios
        .get(url, { params: data })
        .then(function (response) {
          return response.data;
        })
        .catch(function (error) {
          console.log(error);
          return null;
        });
      console.log(this.responeData);
      if (this.responeData == "") {
        this.isFind = 2;
      } else {
        this.isFind = 1;
      }
    },
  },
};
</script>

<template>
  <link
    rel="stylesheet"
    integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
    crossorigin="anonymous"
  />
  <body>
    <div class="container">
      <div class="input-form-backgroud row">
        <div class="input-form col-md-12 mx-auto">
          <h4 class="mb-3">비밀번호 찾기</h4>
          <form
            class="validation-form"
            v-on:submit.prevent="submitForm"
            method="get"
            novalidate
          >
            <div class="row">
              <div class="mb-3">
                <label for="name">아이디</label>
                <input
                  type="text"
                  v-model="userId"
                  class="form-control"
                  id="userId"
                  placeholder="ID"
                  required
                />
                <div class="invalid-feedback">아이디 입력해주세요.</div>
              </div>
            </div>
            <div class="mb-3">
              <label for="email">이메일</label>
              <input
                type="email"
                class="form-control"
                v-model="userEmail"
                id="userEmail"
                placeholder="Email"
                required
              />
              <div class="invalid-feedback">이메일을 입력해주세요.</div>
            </div>
            <div class="mb-3">
              <label for="user-question">비밀번호 질문 : 당신의 학번은?</label>
              <input
                type="email"
                class="form-control"
                v-model="userQuestionResult"
                id="userQuestionResult"
                placeholder="학번을 입력하세요."
                required
              />
              <div class="invalid-feedback">학번을 입력해주세요.</div>
            </div>

            <label
              for=""
              v-if="isFind == 1"
              style="font-weight: bold; color: #21ace8"
              >임시비밀번호 발급되었습니다.</label
            >
            <label for="" v-else-if="isFind == 2" style="color: red">
              틀렸습니다. 다시 입력하세요.</label
            >
            <div class="mb-3">
              <label for="login">로그인 하러 가기</label
              ><router-link to="/login">로그인 하러가기</router-link>
            </div>

            <div class="mb-3" style="justify-content: center">
              <button class="btn btn-outline-success" type="submit">
                확인
              </button>
            </div>
            <router-view></router-view>

            <!-- router-link 테그는 페이지 이동을 위한 링크 태그 -->
            <div></div>
            <div class="mb-3">
              <label for="name">아이디가 기억나지 않는다면?</label
              ><router-link to="/findId">아이디 찾기</router-link>
            </div>
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
