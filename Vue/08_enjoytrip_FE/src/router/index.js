import { createRouter, createWebHistory } from 'vue-router';
import TheMainView from '../views/TheMainView.vue';
import { storeToRefs } from 'pinia';
import { useMemberStore } from '@/stores/member';

const onlyAuthUser = async (to, from, next) => {
  const memberStore = useMemberStore();
  const { userInfo, isValidToken } = storeToRefs(memberStore);
  const { getUserInfo } = memberStore;

  let token = sessionStorage.getItem('accessToken');

  console.log('토큰 검사중입니다');
  if (userInfo.value != null && token) {
    console.log('유효성 검사중입니다');
    await getUserInfo(token);
  }

  if (!isValidToken.value || userInfo.value === null) {
    console.log('토큰이없어요');
    next({ name: 'login' });
  } else {
    console.log('정상토큰이있어요');
    next();
  }
};

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: TheMainView
    },
    {
      path: '/map',
      name: 'map',
      component: () => import('../views/TheMapView.vue')
    },
    {
      path: '/loginregisterpage',
      name: 'loginregisterpage',
      component: () => import('../views/LoginRegisterPage.vue')
    },
    {
      path: '/mypage',
      name: 'mypage',
      beforeEnter: onlyAuthUser,
      component: () => import('../views/MyPage.vue')
    },
    {
      path: '/helpdesk',
      name: 'helpdesk',
      component: () => import('../views/HelpDesk.vue')
    },
    {
      path: '/userinfo',
      name: 'userinfo',
      component: () => import('../views/UserInfoPage.vue')
    },
    {
      path: '/loginPage',
      name: 'loginPage',
      component: () => import('../views/LoginRegisterPage.vue'),
      redirect: { name: 'login' },
      children: [
        {
          path: '/login',
          name: 'login',
          component: () => import('@/components/member/Login.vue')
        },
        {
          path: '/register',
          name: 'register',
          component: () => import('@/components/member/RegisterVue.vue')
        },
        {
          path: '/findId',
          name: 'findId',
          component: () => import('@/components/member/FindId.vue')
        },
        {
          path: '/findPass',
          name: 'findPass',
          component: () => import('@/components/member/FindPass.vue')
        }
      ]
    },
    {
      path: '/qnaboard',
      name: 'qnaboard',

      component: () => import('../views/TheQnaBoardView.vue'),
      redirect: { name: 'qna-article-list' },
      children: [
        {
          path: 'list',
          name: 'qna-article-list',
          component: () => import('@/components/qnaboard/QnaBoardList.vue')
        },
        {
          path: 'view/:qnaArticleId',
          name: 'qna-article-view',
          beforeEnter: onlyAuthUser,
          component: () => import('@/components/qnaboard/QnaBoardDetail.vue')
        },
        {
          path: 'write',
          name: 'qna-article-write',
          beforeEnter: onlyAuthUser,
          component: () => import('@/components/qnaboard/QnaBoardWrite.vue')
        },
        {
          path: 'modify/:qnaArticleId',
          name: 'qna-article-modify',
          beforeEnter: onlyAuthUser,
          component: () => import('@/components/qnaboard/QnaBoardModify.vue')
        }
      ]
    },
    {
      path: '/userInfo',
      name: 'userInfo',
      component: () => import('../views/UserInfoPage.vue'),
      redirect: { name: 'userinfo' },
      children: [
        {
          path: '/userinfo',
          name: 'userinfo',
          component: () => import('@/components/member/UserInfo.vue')
        },
        {
          path: '/userinfomodify',
          name: 'userinfomodify',
          component: () => import('@/components/member/UserInfoModify.vue')
        }
      ]
    },
  {
      path: "/tripboard",
      name: "tripboard",

      component: () => import("../views/TheTripBoardView.vue"),
      redirect: { name: "trip-article-list" },
      children: [
        {
          path: "list",
          name: "trip-article-list",
          component: () => import("@/components/tripboard/TripBoardList.vue"),
        },
        {
          path: "view/:tripArticleId",
          name: "trip-article-view",
          component: () => import("@/components/tripboard/TripBoardDetail.vue"),
        },
        {
          path: "write",
          name: "trip-article-write",
          component: () => import("@/components/Tripboard/TripBoardWrite.vue"),
        },
        {
          path: "modify/:tripArticleId",
          name: "trip-article-modify",
          component: () => import("@/components/tripboard/TripBoardModify.vue"),
        },
      ],
    },
  ],
});

export default router;
