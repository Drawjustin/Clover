<script setup>
import MapPage from "./views/MapPage.vue";
import { RouterView } from "vue-router";
import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue";
</script>

<template>
  <div>
    <TheHeadingNavbar />
    <!-- <map-page /> -->
    <RouterView />
  </div>
</template>

<style scoped></style>
