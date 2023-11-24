<script setup></script>

<template>
  <div style="border: none; background: transparent">
    <video src="../img/pexels_videos_2530263 (2160p).mp4" style="width: 100%; height: 100%" autoplay="" loop="" muted=""></video>

    <form class="d-flex" role="search">
      <input class="form-control me-2" type="search" placeholder="당신의 클로버 여행지는 어디인가요?" aria-label="Search" />
      <button class="btn btn-outline-success" type="button">search</button>
    </form>
    <button onclick="scrollToTop()" id="scrollToTopBtn">Top</button>
  </div>
</template>

<style scoped>
body {
  margin: 0;
  padding: 0;
  overflow: hidden; /* 비디오의 너비 및 높이를 100%로 설정하려면 body에 overflow: hidden;을 사용합니다. */
  /* overflow-y: auto;
  height: 5000px; */
}

video {
  width: 100%;
  height: 100vh;
  object-fit: cover; /* 비디오가 전체 화면을 채우도록 설정 */
  position: absolute;
  top: 0;
  left: 0;
}

form {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  padding: 20px;
  background-color: rgba(255, 255, 255, 0.8);
  border-radius: 8px;
  width: 40%;
}

/* Optional: Add styles for better appearance */
label,
input,
button {
  margin-bottom: 10px;
}

#scrollToTopBtn {
  display: none;
  position: fixed;
  bottom: 20px;
  right: 20px;
  background-color: #ffffff;
  color: white;
  border: none;
  border-radius: 5px;
  padding: 10px 15px;
  cursor: pointer;
}

#scrollToTopBtn:hover {
  background-color: #0056b3;
}
</style>
