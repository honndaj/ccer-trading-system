<template>
    <div>
        <div>
            <el-input style="width: 200px" placeholder="角色名" suffix-icon="el-icon-search" v-model="name"></el-input>
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
            <el-table-column prop="id" label="ID"></el-table-column>
            <el-table-column prop="roleName" label="角色名"></el-table-column>
            <el-table-column prop="detail" label="具体功能"></el-table-column>
            <el-table-column prop="uniqueKey" label="唯一标识符"></el-table-column>
            <el-table-column label="操作" width="280" align="center">
                <template slot-scope="scope">
                    <el-button type="info" @click="selectMenu(scope.row)">权限 <i class="el-icon-menu"></i></el-button>
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

        <el-dialog title="角色信息" :visible.sync="dialogFormVisible" width="30%">
            <el-form label-width="80px" size="small">
                <el-form-item label="角色名">
                    <el-input v-model="form.roleName" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="具体功能">
                    <el-input v-model="form.detail" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="唯一标识符">
                    <el-input v-model="form.uniqueKey" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="save">确 定</el-button>
            </div>
        </el-dialog>

        <el-dialog title="权限分配" :visible.sync="menuDialogFormVisible" width="30%" style="padding: 0 50px">
            <el-tree :data="menuData"
                     :props="props"
                      show-checkbox
                      node-key="id"
                      ref="tree"
                      :default-expanded-keys="expands"
                      :default-checked-keys="checks"
                      >
                      <span class="custom-tree-node" slot-scope="{ node, data }">
                          <span><i :class="data.menuIcon"></i> {{ data.menuName }}</span>
                      </span>
            </el-tree>
            <div slot="footer" class="dialog-footer">
                <el-button @click="menuDialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="saveRoleMenu">确 定</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name: "Role",
    data() {
        return {
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            name: "",
            form: {},
            dialogFormVisible: false,
            menuDialogFormVisible: false,
            multipleSelection: [],
            menuData: [],
            props: {
                label: 'name'
            },
            expands: [],
            checks: [],
            roleId: 0,
            uniqueKey: ''
        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.request.get("/role/page", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    name: this.name,
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total
            })
        },
        save() {
            this.request.post("/role", this.form).then(res => {
                if (res.code == '200') {
                    this.$message.success("保存成功")
                    this.dialogFormVisible = false
                    this.load()
                } else {
                    this.$message.error("保存失败")
                }
            })
        },
        saveRoleMenu() {
            this.request.post("/role/roleMenu/" + this.roleId, this.$refs.tree.getCheckedKeys()).then(res => {
                if (res.code == '200') {
                    this.$message.success("保存成功")
                    this.menuDialogFormVisible = false
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
            this.request.delete("/role/" + id).then(res => {
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
            this.request.post("/role/del/multi", ids).then(res => {
                if (res.code == '200') {
                    this.$message.success("批量删除成功")
                    this.load()
                } else {
                    this.$message.error("批量删除失败")
                }
            })
        },
        reset() {
            this.name = "",
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
        },
        selectMenu(role) {
            this.menuDialogFormVisible = true
            this.roleId = role.id
            this.uniqueKey = role.uniqueKey
            //请求菜单数据
            this.request.get("/menu", {
                params: {
                    name: ""
                }
            }).then(res => {
                this.menuData = res.data
                this.expands = this.menuData.map(v => v.id)
            })

            this.request.get("/role/roleMenu/" + role.id).then(res =>{
                this.checks = res.data
            })
        }
    }
}
</script>

<style>
.headerBg {
    background: #eee !important;
}
</style>
