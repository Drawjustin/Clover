<script>
console.log('findId접속');
import axios from 'axios';
// import { ref } from 'vue';
export default {
  data: function () {
    return {
      userName: '',
      userEmail: '',
      responeData: '',
      isFind: false
    };
  },
  methods: {
    submitForm: async function () {
      console.log(this.userName, this.userEmail);
      console.log(this.isFind);
      var url = 'http://localhost:8080/enjoytrip/user/findId';
      var data = {
        userName: this.userName,
        userEmail: this.userEmail
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

      if (this.responeData !== null) {
        this.isFind = true;
      }
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
          <h4 class="mb-3">아이디 찾기</h4>
          <form class="validation-form" v-on:submit.prevent="submitForm" method="get" novalidate>
            <div class="row">
              <div class="mb-3">
                <label for="name">이름</label>
                <input type="text" v-model="userName" class="form-control" id="userName" placeholder="nickname" required />
                <div class="invalid-feedback">이름을 입력해주세요.</div>
              </div>
            </div>
            <div class="mb-3">
              <label for="email">이메일</label>
              <input type="email" v-model="userEmail" class="form-control" id="userEmail" placeholder="Email" required />
              <div class="invalid-feedback">이메일을 입력해주세요.</div>
            </div>
            <label for="" v-if="isFind"> 아이디 : {{ this.responeData.userId }}</label>

            <div class="mb-3" style="justify-content: center">
              <button class="btn btn-outline-success" type="submit">아이디 찾기</button>
            </div>
            <!-- <p v-if="errorIdEmail">아이디와 이메일이 맞지 않습니다. 다시 시도해주세요</p> -->

            <!-- router-link 테그는 페이지 이동을 위한 링크 태그 -->
            <div></div>
            <div class="mb-3"><label for="name">비밀번호가 기억나지 않는다면?</label><router-link to="/findPass">비밀번호 찾기</router-link></div>
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
