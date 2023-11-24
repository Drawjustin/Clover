<script>
console.log('userInfo접속');
import axios from 'axios';
import { storeToRefs } from 'pinia';
import { useMemberStore } from '@/stores/member';
const memberStore = useMemberStore();
const { userInfo } = storeToRefs(memberStore);

export default {
  data: function () {
    return {
      userId: userInfo.value.userId,
      userName: userInfo.value.userName,
      userEmail: userInfo.value.userEmail,
      userQuestionResult: userInfo.value.userQuestionResult
    };
  },
  methods: {
    submitForm: async function () {
      console.log(this.userName, this.userEmail);
      // console.log(this.isFind);
      var url = 'http://localhost:8080/enjoytrip/user/update';
      var data = {
        userId: this.userId,
        userName: this.userName,
        userEmail: this.userEmail,
        userPass: this.userPass,
        userQuestionResult: this.userQuestionResult
      };
      this.responseData = await axios
        .put(url, data)
        .then(function (response) {
          return response.data;
        })
        .catch(function (error) {
          console.log(error);
          return null;
        });
    },
    deleteUser: async function () {
      console.log(this.userName, this.userEmail);
      // console.log(this.isFind);
      var url = `http://localhost:8080/enjoytrip/user/delete/${this.userId}`;
      this.responseData = await axios
        .delete(url)
        .then(function (response) {
          return response.data;
        })
        .catch(function (error) {
          console.log(error);
          return null;
        });
    }
  }
};
</script>

<template>
  <link rel="stylesheet" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous" />
  <body>
    <div class="container">
      <div class="input-form-backgroud row">
        <div class="input-form col-md-12 mx-auto">
          <h4 class="mb-3">회원 정보</h4>
          <form class="validation-form" v-on:submit.prevent novalidate>
            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="name">아이디</label>
                <input type="text" class="form-control" id="id" placeholder="아이디" v-model="userId" clearable="true" maxlength="20" required readonly />
              </div>
            </div>
            <div class="mb-3">
              <label for="text">이름</label>
              <input type="text" class="form-control" id="nickname" placeholder="Nickname" v-model="userName" required readonly />
              <div class="invalid-feedback">이름을 입력해주세요.</div>
            </div>
            <div class="mb-3">
              <label for="email">이메일</label>
              <input type="email" class="form-control" id="userEmail" placeholder="email" v-model="userEmail" clearable="true" maxlength="30" required readonly />
            </div>
            <div class="mb-3">
              <label for="text">비밀번호 찾기 힌트</label>
              <input type="text" class="form-control" id="findPassHint" v-model="userQuestionResult" placeholder="학번입력" required readonly />
            </div>

            <div class="row"></div>
            <div style="display: flex; float: right">
              <div>
                <router-link to="/userinfomodify"> <button class="btn btn-primary btn-lg btn-block">수정</button></router-link>
              </div>
              <div style="margin: 5px"></div>
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
}

.input-form {
  max-width: 500px;

  margin-top: 80px;
  padding: 32px;

  -webkit-border-radius: 10px;
  -moz-border-radius: 10px;
  border-radius: 10px;
  -webkit-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
  -moz-box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
  box-shadow: 0 8px 20px 0 rgba(0, 0, 0, 0.15);
}

.find-container {
  width: 100%;
}
.btn-find {
  width: 200px;
  display: inline-block;
  text-align: center;
}
</style>
