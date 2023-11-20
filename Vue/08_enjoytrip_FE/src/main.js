import { createApp } from "vue";
import { createPinia } from "pinia";
// text editer를 위한 quill

import App from "./App.vue";
import router from "./router";

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";

const app = createApp(App);

app.use(createPinia());
app.use(router);

app.mount("#app");
