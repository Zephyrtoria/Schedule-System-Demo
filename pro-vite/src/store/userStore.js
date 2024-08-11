/*
用于存储用户状态信息的Pinia
*/
import { defineStore } from "pinia";

export const defineUser = defineStore("loginUser", {
    state: () => {
        return {
            uid:0,
            username:""
            // 密码不应该存在客户端
        };
    },
    getters: {},
    actions: {},
});
