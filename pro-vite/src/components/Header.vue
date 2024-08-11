<script setup>
// 导入pinia数据
import { defineUser } from "../store/userStore";
import { defineSchedule } from "../store/scheduleStore";
import { useRouter } from "vue-router";
const router = useRouter();

let sysUser = defineUser();
let schedule = defineSchedule();

function logout() {
  // 清除所有pinia数据
  sysUser.$reset();
  schedule.$reset();
  // 跳转到登录页
  router.push("/login");
}
</script>

<template>
  <div>
    <h1 class="ht">欢迎使用日程管理系统</h1>
    <div>
      <!-- 根据用户名是否为空判断是否有登录，进而判断展示什么页面 -->
      <div class="optionDiv" v-if="sysUser.username == ''">
        <router-link to="/login">
          <button class="b1s">登录</button>
        </router-link>
        <router-link to="/register">
          <button class="b1s">注册</button>
        </router-link>
      </div>
      <div class="optionDiv" v-else>
        <span>欢迎{{ sysUser.username }}</span>
        <button class="b1b" @click="logout()">退出登录</button>
        <router-link to="/showSchedule">
          <button class="b1b">查看我的日程</button>
        </router-link>
      </div>
      <br />
    </div>
  </div>
</template>

<style scoped>
.ht {
  text-align: center;
  color: cadetblue;
  font-family: 幼圆;
}
.b1s {
  border: 2px solid powderblue;
  border-radius: 4px;
  width: 60px;
  background-color: antiquewhite;
}

.b1b {
  border: 2px solid powderblue;
  border-radius: 4px;
  width: 100px;
  background-color: antiquewhite;
}
.optionDiv {
  width: 300px;
  float: right;
}
</style>
