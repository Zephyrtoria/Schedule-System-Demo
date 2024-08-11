import { createRouter, createWebHashHistory } from "vue-router";
import Login from "../components/Login.vue";
import Register from "../components/Register.vue";
import ShowSchedule from "../components/ShowSchedule.vue";

// 路由中导入pinia数据和.vue中导入方式不同
import pinia from "../pinia.js"; // 其实是因为router.js不受main.js影响，所以还需要再导入pinia
import { defineUser } from "../store/userStore.js";
let sysUser = defineUser(pinia);

const router = createRouter({
    history: createWebHashHistory(),
    routes: [
        {
            path: "/",
            redirect: "/showSchedule",
        },
        {
            path: "/login",
            component: Login,
        },
        {
            path: "/register",
            component: Register,
        },
        {
            path: "/showSchedule",
            component: ShowSchedule,
        },
    ],
});

// 路由的全局前置守卫，判断是否可以访问showSchedule
router.beforeEach((to, from, next) => {
    // 仅在访问/showSchedule时需要进行拦截
    // 需要注意的是，在router.js中设置了/路径也是展示ShowSchedule视图
    // 由于这是展示而不是路径跳转，所以不会进行拦截，应该再增加路径的判断 to.path == "/"
    // 或者是在路由中设置为重定向
    // 但是通过大小写仍然可以绕过这个守卫，还需要改进
    if (to.path == "/showSchedule") {
        if (sysUser.username == "") {
            // 没有登录过需要跳转到登录页
            next("/login");
        } else {
            // 登录过则直接放行
            next();
        }
    } else {
        next();
    }
});

export default router;
