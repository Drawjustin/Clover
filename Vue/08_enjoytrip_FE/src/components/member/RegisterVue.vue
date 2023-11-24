<script>
import { ref } from 'vue';
import { registerpage } from '../../api/member';
import router from '../../router';
import LoginRegisterPage from '../../views/LoginRegisterPage.vue';
export default {
  setup() {
    const userId = ref('');
    const errorUserId = ref(true);

    const userPass = ref('');
    const errorUserPass = ref(true);

    const userEmail = ref('');
    const errorUserEmail = ref(true);

    const userPassConfirm = ref('');
    const errorUserPassConfirm = ref(true);

    const userName = ref('');

    const userQuestionResult = ref('');

    const param = ref({
      userId: userId,
      userName: userName,
      userPass: userPass,
      userEmail: userEmail,
      userQuestionResult: userQuestionResult
    });
    const register123 = () => {
      console.log('회원가입 실행');
      if (param.value.userId === '' || param.value.userName === '' || param.value.userPass === '' || param.value.userEmail === '' || param.value.userQuestionResult === '') {
        alert('모두 입력해주세요!!!');
        return;
      }
      registerpage(
        param.value,
        ({ data }) => {
          console.log(data);
          alert('회원가입에 성공했습니다! 짝 짝 짝');
          router.push('/loginPage');
        },
        ({ error }) => {
          console.log(error);
          alert('실패했습니다 ㅜ^ㅜ');
        }
      );
    };

    function checkId() {
      const validateId = /^[A-Za-z0-9]{4,20}$/;
      if (!validateId.test(userId.value) || !userId.value) {
        errorUserId.value = true;
        return;
      }
      errorUserId.value = false;
    }

    function checkPass() {
      const validatePass = /^[A-Za-z0-9]{4,20}$/;
      if (!validatePass.test(userPass.value) || !userPass.value) {
        errorUserPass.value = true;
        return;
      }
      errorUserPass.value = false;
    }
    function checkEmail() {
      const validateEmail = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Z|a-z]{2,30}$/;
      if (!validateEmail.test(userEmail.value) || !userEmail.value) {
        errorUserEmail.value = true;
        return;
      }
      errorUserEmail.value = false;
    }

    function checkConfirmPass() {
      if (userPassConfirm.value === userPass.value) {
        errorUserPassConfirm.value = false;
        return;
      }
      errorUserPassConfirm.value = true;
    }

    return {
      userId,
      errorUserId,
      userName,
      checkId,
      userPass,
      checkPass,
      errorUserPass,
      errorUserEmail,
      checkEmail,
      userEmail,
      checkConfirmPass,
      errorUserPassConfirm,
      userPassConfirm,
      register123,
      userQuestionResult
    };
  },
  watch: {
    userId: function () {
      this.checkId();
    },
    userPass: function () {
      this.checkPass();
    },
    userEmail: function () {
      this.checkEmail();
    },
    userPassConfirm: function () {
      this.checkConfirmPass();
    },
    register: function () {
      this.register123();
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
          <h4 class="mb-3">회원가입</h4>
          <form class="validation-form" novalidate>
            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="name">아이디</label>
                <input type="text" class="form-control" id="id" placeholder="아이디" v-model="userId" clearable="true" maxlength="20" required />
                <p v-if="errorUserId">아이디는 영문, 숫자를 포함한 4자이상 20자 이내여야 합니다.</p>
                <div class="invalid-feedback">아이디 입력해주세요.</div>
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
              <input type="email" class="form-control" id="userEmail" placeholder="ssafy@com.com" v-model="userEmail" clearable="true" maxlength="30" required />
              <p v-if="errorUserEmail">이메일 형식이 올바르지 않습니다. 다시 한번 확인 해주세요.</p>
              <div class="invalid-feedback">이메일을 입력해주세요.</div>
            </div>
            <div class="mb-3">
              <label for="text">비밀번호 찾기 힌트</label>
              <input type="text" class="form-control" id="findPassHint" v-model="userQuestionResult" placeholder="학번입력" required />
              <div class="invalid-feedback">학번을 입력해주세요.</div>
            </div>
            <div class="mb-4"></div>
            <button class="btn btn-outline-success" type="button" @click="register123()">가입 완료</button>
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

  /* background: -webkit-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
  background: -moz-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
  background: -o-linear-gradient(bottom left, #92b5db 0%, #1d466c 100%);
  background: linear-gradient(to top right, #92b5db 0%, #1d466c 100%); */
}

.input-form {
  max-width: 680px;

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
