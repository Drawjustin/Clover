<script setup>
import { useTodoStore } from "@/stores/todo";
import TodoListItem from "./TodoListItem.vue";

const todoStore = useTodoStore();
</script>

<template>
  <div class="container text-center">
    <todo-list-item v-for="todo in todoStore.todos" :key="todo.id" :todo="todo"></todo-list-item>
  </div>
</template>

<style scoped></style>
