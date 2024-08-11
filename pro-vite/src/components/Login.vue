<script setup>
import { ref, reactive } from "vue";
import { useRouter } from "vue-router";
import request from "../utils/request.js";
import { defineUser } from "../store/userStore.js";

const router = useRouter();

let loginUser = reactive({
  username: "",
  userPwd: "",
});

let usernameMsg = ref("");
let userPwdMsg = ref("");

let sysUser = defineUser();

function checkUsername() {
  let usernameReg = /^[a-zA-Z0-9]{5,10}$/;
  if (!usernameReg.test(loginUser.username)) {
    usernameMsg.value = "格式有误";
    return false;
  }
  usernameMsg.value = "OK";
  return true;
}

function checkUserPwd() {
  let userPwdReg = /^[0-9]{6}$/;
  if (!userPwdReg.test(loginUser.userPwd)) {
    userPwdMsg.value = "格式有误";
    return false;
  }
  userPwdMsg.value = "OK";
  return true;
}

async function login() {
  // 先确认表单数据格式都正确再提交
  let flag1 = checkUsername();
  let flag2 = checkUserPwd();

  if (!(flag1 && flag2)) {
    return false;
  }

  // 将loginUser转换为JSON串进行传递
  let { data } = await request.post("user/login", loginUser);
  if (200 == data.code) {
    alert("登录成功！");
    // 将用户信息存入到userStore中，但此时只有用户名，没有用户id，所以仍需通过后端进行查询
    // 获取登录的用户信息，存储到Pinia数据中
    sysUser.uid = data.data.loginUser.uid;
    sysUser.username = data.data.loginUser.username;
    // 跳转到showSchedule
    router.push("/showSchedule");
  } else if (501 == data.code) {
    alert("用户名有误");
  } else if (502 == data.code) {
    alert("密码有误");
  } else {
    alert("未知错误");
  }
}
</script>

<template>
  <div>
    <h3 class="ht">请登录</h3>
    <table class="tab" cellspacing="0px">
      <tr class="ltr">
        <td>请输入账号</td>
        <td>
          <input
              class="ipt"
              type="text"
              v-model="loginUser.username"
              @blur="checkUsername()"
          />
          <span id="usernameMsg" v-text="usernameMsg"></span>
        </td>
      </tr>
      <tr class="ltr">
        <td>请输入密码</td>
        <td>
          <input
              class="ipt"
              type="password"
              v-model="loginUser.userPwd"
              @blur="checkUserPwd()"
          />
          <span id="userPwdMsg" v-text="userPwdMsg"></span>
        </td>
      </tr>
      <tr class="ltr">
        <td colspan="2" class="buttonContainer">
          <input
              class="btn1"
              type="button"
              @click="login()"
              value="登录"
          />
          <input class="btn1" type="button" value="重置" />
          <router-link to="/register">
            <button class="btn1">去注册</button>
          </router-link>
        </td>
      </tr>
    </table>
  </div>
</template>

<style scoped>
.ht {
  text-align: center;
  color: cadetblue;
  font-family: 幼圆;
}
.tab {
  width: 500px;
  border: 5px solid cadetblue;
  margin: 0px auto;
  border-radius: 5px;
  font-family: 幼圆;
}
.ltr td {
  border: 1px solid powderblue;
}
.ipt {
  border: 0px;
  width: 50%;
}
.btn1 {
  border: 2px solid powderblue;
  border-radius: 4px;
  width: 60px;
  background-color: antiquewhite;
}
#usernameMsg,
#userPwdMsg {
  color: gold;
}
.buttonContainer {
  text-align: center;
}
</style>
