<template>
    <div class="wrapper">
        <div
            style="margin: 200px auto; background-color: #ffffcc; width: 350px; height: 300px; padding: 20px; border-radius: 10px">
            <div style="margin: 5px 0; text-align: center; font-size: 24px"><b>CCER交易系统</b></div>
            <div style="margin: 5px 0; text-align: center; font-size: 24px"><b>登 录</b></div>
            <el-form :model="user" :rules="rules" ref="userForm">
                <el-form-item prop="username">
                    <el-input placeholder="请输入账号" size="medium" style="margin: 10px 0" prefix-icon="el-icon-user"
                        v-model="user.username"></el-input>
                </el-form-item>
                <el-form-item prop="password">
                    <el-input placeholder="请输入密码" size="medium" style="margin: 10px 0" prefix-icon="el-icon-lock"
                        show-password v-model="user.password"></el-input>
                </el-form-item>
                <el-form-item style="margin: 10px 0; text-align: right">
                    <el-button type="primary" size="small" autocomplete="off" @click="login">登录</el-button>
                    <el-button type="warning" size="small" autocomplete="off"
                        @click="$router.push('/register')">注册</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>
  
<script>
import { setRoutes } from "@/router"

export default {
    name: "Login",
    data() {
        return {
            user: {},
            rules: {
                username: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '请输入密码', trigger: 'blur' },
                    { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
                ],
            }
        }
    },
    methods: {
        login() {
            this.$refs['userForm'].validate((valid) => {
                if (valid) {
                    this.request.post("/auth/login", this.user).then(res => {
                        if (res.code === '200') {
                            localStorage.setItem("user", JSON.stringify(res.data))
                            localStorage.setItem("menus", JSON.stringify(res.data.menus))
                            setRoutes()
                            this.$router.push("/home")
                            this.$message.success("登录成功")
                        } else {
                            this.$message.error(res.msg)
                        }
                    })
                }
            });
        }
    }
}
</script>
  
<style scoped>
.wrapper {
    background-image: url('../assets/background.jpg');
    background-size: cover;
    background-repeat: no-repeat;
    height: 100vh;
    overflow: hidden;
}
</style>