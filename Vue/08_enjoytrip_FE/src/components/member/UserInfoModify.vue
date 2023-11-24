<script>
console.log('userInfo접속');
import axios from 'axios';
import { storeToRefs } from 'pinia';
import { useMemberStore } from '@/stores/member';
import router from '../../router';
const memberStore = useMemberStore();
const { isLogin, userInfo, isValidToken } = storeToRefs(memberStore);

export default {
  data: function () {
    return {
      userId: userInfo.value.userId,
      userName: userInfo.value.userName,
      userEmail: userInfo.value.userEmail,
      userPass: '',
      userPassConfirm: '',
      responseData: '',
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
      if (data.userPass == '' || data.userName == '' || data.userEmail == '' || data.userQuestionResult == '') {
        alert('모두 입력해주세요!!!');
        return;
      }
      if (data.userPass !== this.userPassConfirm) {
        alert('비밀번호가 일치하지 않습니다.');
        return;
      }
      await axios
        .put(url, data)
        .then(function (response) {
          alert('수정 되었습니다.');
          console.log('수정 완료');
        })
        .catch(function (error) {
          console.log(error);
          return null;
        });

      router.push('/userinfo');
    },
    deleteUser: async function () {
      console.log(this.userName, this.userEmail);
      // console.log(this.isFind);
      var url = `http://localhost:8080/enjoytrip/user/delete/${this.userId}`;
      await axios
        .delete(url)
        .then(function (response) {
          alert('삭제 되었습니다.');
          console.log('삭제 완료');

          isLogin.value = false;
          userInfo.value = null;
          isValidToken.value = false;
          sessionStorage.clear();
          router.push({ name: 'login' });
        })
        .catch(function (error) {
          console.log(error);
          return null;
        });
      router.push('/'); //삭제하면 모두 없어져서 홈으로 이동
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
                <p v-if="errorUserId">아이디는 변경할 수 없습니다.</p>
              </div>
            </div>
            <div class="mb-3">
              <label for="text">이름</label>
              <input type="text" class="form-control" id="nickname" placeholder="Nickname" v-model="userName" required />
              <div class="invalid-feedback">이름을 입력해주세요.</div>
            </div>
            <div class="mb-3">
              <label for="password">비밀번호</label>
              <input type="password" class="form-control" id="userPass" placeholder="Password" v-model="userPass" clearable="true" maxlength="30" required />
              <p v-if="errorUserPass">패스워드는 영문, 숫자를 포함한 4자이상 20자 이내여야 합니다.</p>
              <div class="invalid-feedback">비밀번호는 영문+숫자를 포함한 4자이상 20자 이내이여야 합니다.</div>
            </div>
            <div class="mb-3">
              <label for="password">비밀번호 확인</label>
              <input type="password" class="form-control" placeholder="Password Confirm" v-model="userPassConfirm" clearable="true" maxlength="30" required />
              <p v-if="errorUserPassConfirm">비밀번호가 일치하지 않습니다.</p>
              <div class="invalid-feedback">비밀번호를 다시 입력해주세요.</div>
            </div>
            <div class="mb-3">
              <label for="email">이메일</label>
              <input type="email" class="form-control" id="userEmail" placeholder="email" v-model="userEmail" clearable="true" maxlength="30" required />
              <p v-if="errorUserEmail">이메일 형식이 올바르지 않습니다. 다시 한번 확인 해주세요.</p>
              <div class="invalid-feedback">이메일을 입력해주세요.</div>
            </div>
            <div class="mb-3">
              <label for="text">비밀번호 찾기 힌트</label>
              <input type="text" class="form-control" id="findPassHint" v-model="userQuestionResult" placeholder="학번입력" required />
              <div class="invalid-feedback">학번을 입력해주세요.</div>
            </div>

            <div class="row"></div>
            <div style="display: flex; float: right">
              <div>
                <button class="btn btn-primary btn-lg btn-block" @click="submitForm">수정</button>
              </div>
              <div style="margin: 5px"></div>
              <div>
                <button class="btn btn-primary btn-lg btn-block" @click="deleteUser">삭제</button>
              </div>
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
