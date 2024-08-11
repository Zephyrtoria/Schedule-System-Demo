<script setup>
import { ref, reactive } from "vue";
import request from "../utils/request.js";
import { useRouter } from "vue-router";

const router = useRouter();

let registerUser = reactive({
  username: "",
  userPwd: "",
});

let usernameMsg = ref("");
let userPwdMsg = ref("");
let reUserPwd = ref(""); // 提交用户信息时只需要提交用户名和密码，不需要再提交确认密码
let reUserPwdMsg = ref("");

async function checkUsername() {
  let usernameReg = /^[a-zA-Z0-9]{5,10}$/;
  if (!usernameReg.test(registerUser.username)) {
    usernameMsg.value = "格式有误";
    return false;
  }
  // 继续校验用户名是否被占用
  let { data } = await request.post(
      `user/checkUsernameUsed?username=${registerUser.username}`
  );
  if (200 != data.code) {
    usernameMsg.value = "用户名被占用";
    return false;
  }
  usernameMsg.value = "OK";
  return true;
}

function checkUserPwd() {
  let userPwdReg = /^[0-9]{6}$/;
  if (!userPwdReg.test(registerUser.userPwd)) {
    userPwdMsg.value = "格式有误";
    return false;
  }
  userPwdMsg.value = "OK";
  return true;
}

function checkReUserPwd() {
  if (registerUser.userPwd != reUserPwd.value) {
    reUserPwdMsg.value = "两次密码不一致";
    return false;
  }
  reUserPwdMsg.value = "OK";
  return true;
}

async function register() {
  // 校验所有的输入框是否合法
  let flag1 = checkUsername();
  let flag2 = checkUserPwd();
  let flag3 = checkReUserPwd();
  if (flag1 && flag2 && flag3) {
    // 会将registerUser以JSON串的形式发送
    // 注意此时的后端代码使用了getParameter()来获取信息，不能获取JSON串
    let { data } = await request.post("user/register", registerUser);
    // 页面跳转
    if (200 == data.code) {
      alert("注册成功！");
      router.push("/login");
    } else {
      alert("抱歉，用户名已被抢注");
    }
  } else {
    alert("校验不通过！请检查数据！");
  }
}
</script>

<template>
  <div>
    <h3 class="ht">请注册</h3>

    <table class="tab" cellspacing="0px">
      <tr class="ltr">
        <td>请输入账号</td>
        <td>
          <input
              class="ipt"
              id="usernameInput"
              type="text"
              name="username"
              v-model="registerUser.username"
              @blur="checkUsername()"
          />

          <span
              id="usernameMsg"
              class="msg"
              v-text="usernameMsg"
          ></span>
        </td>
      </tr>
      <tr class="ltr">
        <td>请输入密码</td>
        <td>
          <input
              class="ipt"
              id="userPwdInput"
              type="password"
              name="userPwd"
              v-model="registerUser.userPwd"
              @blur="checkUserPwd()"
          />
          <span
              id="userPwdMsg"
              class="msg"
              v-text="userPwdMsg"
          ></span>
        </td>
      </tr>
      <tr class="ltr">
        <td>确认密码</td>
        <td>
          <input
              class="ipt"
              id="reUserPwdInput"
              type="password"
              v-model="reUserPwd"
              @blur="checkReUserPwd()"
          />
          <span
              id="reUserPwdMsg"
              class="msg"
              v-text="reUserPwdMsg"
          ></span>
        </td>
      </tr>
      <tr class="ltr">
        <td colspan="2" class="buttonContainer">
          <input
              class="btn1"
              type="button"
              @click="register()"
              value="注册"
          />
          <input class="btn1" type="button" value="重置" />
          <router-link to="/login">
            <button class="btn1">去登录</button>
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
.msg {
  color: gold;
}
.buttonContainer {
  text-align: center;
}
</style>
