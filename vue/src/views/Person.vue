<template>
    <el-card style="width: 500px; border: 1px solid #ccc">
        <el-form label-width="80px" size="small">
            <el-form-item label="用户名">
                <el-input v-model="form.username" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="公司名">
                <el-input v-model="form.companyName" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="地址">
                <el-input v-model="form.address" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="法定代表">
                <el-input v-model="form.legalRepresent" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="统一社会信用代码">
                <el-input v-model="form.companyCode" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="交易代表">
                <el-input v-model="form.tradeRepresent" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="手机号码">
                <el-input v-model="form.phone" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
                <el-input v-model="form.email" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="邮编">
                <el-input v-model="form.expressCode" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="座机电话">
                <el-input v-model="form.tel" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="传真">
                <el-input v-model="form.fax" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="save">确 定</el-button>
            </el-form-item>
        </el-form>
    </el-card>
</template>

<script>
export default {
    name: "Person",
    data() {
        return {
            form:{},
            user : localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {}
        }
    },
    created() {
        this.request.get("/user/username/" + this.user.username).then(res => {
            if (res.code == '200') {
                this.form = res.data
            }
        })
    },
    methods: {
        save() {
            this.request.post("/user", this.form).then(res => {
                if (res.data) {
                    this.$message.success("保存成功")
                    this.dialogFormVisible = false
                    this.load()
                } else {
                    this.$message.error("保存失败")
                }
            })
        }
    }
}
</script>

<style></style>