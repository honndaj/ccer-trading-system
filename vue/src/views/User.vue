<template>
    <div>
        <div>
            <el-input style="width: 200px" placeholder="请输入用户名" suffix-icon="el-icon-search" v-model="username"></el-input>
            <el-input style="width: 200px" placeholder="请输入公司名" suffix-icon="el-icon-message" class="ml-5"
                v-model="companyName"></el-input>
            <el-input style="width: 200px" placeholder="请输入地址" suffix-icon="el-icon-position" class="ml-5"
                v-model="address"></el-input>
            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>
        </div>

        <div style="margin: 10px 0">
            <el-button type="primary" @click="handleAdd">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
            <el-popconfirm class="ml-5" confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info"
                icon-color="red" title="您确定批量删除吗？" @confirm="delBatch">
                <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"></el-table-column>
            <el-table-column prop="id" label="ID" width="80"></el-table-column>
            <el-table-column prop="username" label="用户名" width="140"></el-table-column>
            <el-table-column prop="uniqueKey" label="角色"></el-table-column>
            <el-table-column prop="money" label="资金"></el-table-column>
            <el-table-column prop="companyName" label="公司名"></el-table-column>
            <el-table-column prop="address" label="地址"></el-table-column>
            <el-table-column prop="legalRepresent" label="法定代表"></el-table-column>
            <el-table-column prop="tradeRepresent" label="交易代表"></el-table-column>
            <el-table-column label="操作" width="200" align="center">
                <template slot-scope="scope">
                    <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
                    <el-popconfirm class="ml-5" confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info"
                        icon-color="red" title="您确定删除吗？" @confirm="del(scope.row.id)">
                        <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
                    </el-popconfirm>
                </template>
            </el-table-column>
        </el-table>
        <div style="padding: 10px 0">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
                :page-sizes="[2,5,10]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div>

        <el-dialog title="用户信息" :visible.sync="dialogFormVisible" width="30%">
            <el-form label-width="80px" size="small">
                <el-form-item label="用户名">
                    <el-input v-model="form.username" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="角色">
                    <el-select clearable v-model="form.uniqueKey" placeholder="请选择角色" style="width: 100%">
                        <el-option v-for="item in roles" :key="item.name" :label="item.name" :value="item.uniqueKey"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="资金">
                    <el-input v-model="form.money" autocomplete="off"></el-input>
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
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "User",
    data() {
        return {
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            username: "",
            companyName: "",
            address: "",
            form: {},
            dialogFormVisible: false,
            multipleSelection: [],
            roles: []
        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.request.get("/user/page", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    username: this.username,
                    companyName: this.companyName,
                    address: this.address
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total
            })

            this.request.get("/role").then(res => {//获取dialog中的
                this.roles = res.data
            })
        },
        save() {
            this.request.post("/user", this.form).then(res => {
                if (res.code == '200') {
                    this.$message.success("保存成功")
                    this.dialogFormVisible = false
                    this.load()
                } else {
                    this.$message.error("保存失败")
                }
            })
        },
        handleAdd() {
            this.dialogFormVisible = true,
                this.form = {}
        },
        handleEdit(row) {
            this.form = row
            this.dialogFormVisible = true

        },
        del(id) {
            this.request.delete("/user/" + id).then(res => {
                if (res.code == '200') {
                    this.$message.success("删除成功")
                    this.load()
                } else {
                    this.$message.error("删除失败")
                }
            })
        },
        handleSelectionChange(val) {
            this.multipleSelection = val
        },
        delBatch() {
            let ids = this.multipleSelection.map(v => v.id)
            this.request.post("/user/del/multi", ids).then(res => {
                if (res.code == '200') {
                    this.$message.success("批量删除成功")
                    this.load()
                } else {
                    this.$message.error("批量删除失败")
                }
            })
        },
        reset() {
            this.username = "",
                this.companyName = "",
                this.address = ""
            this.load();
        },
        handleSizeChange(pageSize) {
            console.log(pageSize)
            this.pageSize = pageSize
            this.load()
        },
        handleCurrentChange(pageNum) {
            console.log(pageNum)
            this.pageNum = pageNum
            this.load()
        }
    }
}
</script>

<style>
.headerBg {
  background: #eee !important;
}
</style>
