<script setup>
// 导入pinia数据
import { defineUser } from "../store/userStore.js";
import { defineSchedule } from "../store/scheduleStore.js";
let sysUser = defineUser();
let schedule = defineSchedule();

// 页面挂载完毕后，应该立刻查询当前用户的所有日程信息，赋值给pinia
import { onUpdated, onMounted, ref, reactive } from "vue";
import request from "../utils/request.js";
onMounted(async () => {
    showSchedule();
});

// 查询当前用户所有信息，获得当前用户的所有日程记录
async function showSchedule() {
    // 发送异步请求，获得当前用户的所有日程记录
    let { data } = await request.get("schedule/findAllSchedule", {
        params: { uid: sysUser.uid },
    });
    schedule.itemList = data.data.itemList;
}

// 为当前用户增加一个空的日程记录
async function addSchedule() {
    let { data } = await request.get("/schedule/addSchedule", {
        params: { uid: sysUser.uid },
    });
    if (200 == data.code) {
        // 增加成功，刷新页面数据
        showSchedule();
    } else {
        // 增加失败
        alert("增加失败");
    }
}

// 根据对应索引值保存对应编号的数据
async function updateSchedule(index) {
    // 找到要修改的数据，发送给服务端，更新数据库
    let { data } = await request.post(
        "schedule/updateSchedule",
        schedule.itemList[index]
    );
    if (200 == data.code) {
        showSchedule();
    } else {
        alert("保存失败！");
    }
}

// 根据对应索引删除对应编号的数据
async function removeSchedule(index) {
    // 找到要删除的数据，发送给服务端，更新数据库
    let { data } = await request.get("schedule/removeSchedule", {
        params: { sid: schedule.itemList[index].sid },
    });
    if (200 == data.code) {
        showSchedule();
    } else {
        alert("删除失败！");
    }
}
</script>

<template>
    <div>
        <h3 class="ht">您的日程如下</h3>
        <table class="tab" cellspacing="0px">
            <tr class="ltr">
                <th>编号</th>
                <th>内容</th>
                <th>进度</th>
                <th>操作</th>
            </tr>
            <tr
                class="ltr"
                v-for="(item, index) in schedule.itemList"
                :key="index"
            >
                <td v-text="index + 1"></td>
                <td>
                    <input type="text" v-model="item.title" />
                </td>
                <td>
                    <input
                        type="radio"
                        value="1"
                        v-model="item.completed"
                    /><span>已完成</span>
                    <input
                        type="radio"
                        value="0"
                        v-model="item.completed"
                    /><span>未完成</span>
                </td>
                <td class="buttonContainer">
                    <button class="btn1" @click="removeSchedule(index)">
                        删除
                    </button>
                    <button class="btn1" @click="updateSchedule(index)">
                        保存修改
                    </button>
                </td>
            </tr>
            <tr class="ltr buttonContainer">
                <td colspan="4">
                    <button class="btn1" @click="addSchedule()">
                        新增日程
                    </button>
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
    width: 80%;
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
    width: 100px;
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
