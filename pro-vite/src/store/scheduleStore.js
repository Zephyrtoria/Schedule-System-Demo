/*
用于存储日程信息的Pinia
*/
import { defineStore } from "pinia";

export const defineSchedule = defineStore("scheduleList", {
    state: () => {
        return {
            itemList: [],
        };
    },
    getters: {},
    actions: {},
});
