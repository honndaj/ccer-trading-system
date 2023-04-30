<template>
    <div>
        <el-select clearable v-model="area" placeholder="请选择地区" style="width: 10%;">
                <el-option v-for="item in areaNames" :key="item" :label="item" :value="item"></el-option>
            </el-select>
            <el-select clearable v-model="kind" placeholder="请选择种类" style="width: 10%; margin-left: 10px;">
                <el-option v-for="item in kindNames" :key="item" :label="item" :value="item"></el-option>
            </el-select>

            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>

        <div style="margin: 10px 0">
            <el-popconfirm class="ml-5" confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info"
                icon-color="red" title="您确定批量删除吗？" @confirm="delBatch">
                <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
            </el-popconfirm>
        </div>

        <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"
            @selection-change="handleSelectionChange">
            <el-table-column type="selection"></el-table-column>
            <el-table-column prop="id" label="ID"></el-table-column>
            <el-table-column prop="ccerFrom" label="CCER转出用户ID"></el-table-column>
            <el-table-column prop="ccerTo" label="CCER转入用户ID"></el-table-column>
            <el-table-column prop="area" label="地区"></el-table-column>
            <el-table-column prop="kind" label="种类"></el-table-column>
            <el-table-column prop="price" label="申报价格"></el-table-column>
            <el-table-column prop="count" label="数量（吨）"></el-table-column>
            <el-table-column prop="createTime" label="完成交易时间" width="160px"></el-table-column>
            <el-table-column label="操作" width="200" align="center">
                <template slot-scope="scope">
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
    </div>
</template>

<script>
export default {
    name: "History",
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
            areaNames: ['北京', '天津', '上海', '深圳', '广州', '湖北', '重庆'],
            kindNames: ['树林', '风能', '太阳能', '水电', '生物质发电'],
            area: "",
            kind: ""
        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.request.get("/history/page", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    area: this.area,
                    kind: this.kind
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total
            })
        },
        del(id) {
            this.request.delete("/history/" + id).then(res => {
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
            this.request.post("/history/del/multi", ids).then(res => {
                if (res.code == '200') {
                    this.$message.success("批量删除成功")
                    this.load()
                } else {
                    this.$message.error("批量删除失败")
                }
            })
        },
        reset() {
            this.area = ""
            this.kind = ""
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
