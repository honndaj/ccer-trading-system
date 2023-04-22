<template>
    <div>
        <div>
            <el-select clearable v-model="area" placeholder="请选择地区" style="width: 10%;">
                <el-option v-for="item in areaNames" :key="item" :label="item" :value="item"></el-option>
            </el-select>
            <el-select clearable v-model="kind" placeholder="请选择种类" style="width: 10%; margin-left: 10px;">
                <el-option v-for="item in kindNames" :key="item" :label="item" :value="item"></el-option>
            </el-select>

            <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
            <el-button type="warning" @click="reset">重置</el-button>
        </div>

        <el-table :data="tableData" border :header-cell-class-name="'headerBg'"
            @selection-change="handleSelectionChange" style="margin-top: 20px;" :row-class-name="tableRowClassName">
            <el-table-column prop="id" label="申报编号" width="80"></el-table-column>
            <el-table-column prop="buySell" label="申报类型" :formatter="formatBuySell"></el-table-column>
            <el-table-column prop="area" label="地区" width="140"></el-table-column>
            <el-table-column prop="kind" label="种类"></el-table-column>
            <el-table-column prop="price" label="申报价格"></el-table-column>
            <el-table-column prop="count" label="数量（吨）"></el-table-column>
            <el-table-column prop="remainTime" label="剩余时间"></el-table-column>
            <el-table-column label="操作" width="200" align="center">
                <template slot-scope="scope">
                    <el-button type="warning" @click="del(scope.row)">撤销<i class="el-icon-sold-out"></i></el-button>
                </template>
            </el-table-column>
        </el-table>

        <div style="padding: 10px 0">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
                :page-sizes="[2, 5, 10]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper"
                :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<script>
import moment from 'moment';

export default {
    name: "Order",
    data() {
        return {
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            name: "",
            form: {},
            kind: "",
            area: "",
            uid: 0,
            areaNames: ['北京', '天津', '上海', '深圳', '广州', '湖北', '重庆'],
            kindNames: ['树林', '风能', '太阳能', '水电', '生物质发电']
        }
    },
    created() {
        this.load()
    },
    methods: {
        load() {
            this.uid = JSON.parse(localStorage.getItem("user")).id
            this.request.get("/now/page", {
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
                    area: this.area,
                    kind: this.kind,
                    uid: this.uid
                }
            }).then(res => {
                this.tableData = res.data.records
                this.total = res.data.total
                this.tableData.forEach((item) => {
                    item.remainTime = this.calculateRemainingTime(item.createTime) + '小时'
                });
            })
        },
        del(row) {
            console.log("/now/" + row.buySell + "/" + row.id);
            this.request.delete("/now/" + row.buySell + "/" + row.id).then(res => {
                if (res.code == '200') {
                    this.$message.success("撤销成功")
                    this.load()
                } else {
                    this.$message.error("撤销失败")
                }
            })
        },
        // 将 createTime 转为 剩余有效时间
        calculateRemainingTime(createTime) {
            const createMoment = moment(createTime);
            createMoment.add(1, "days");
            const now = moment();
            const remaining = moment.duration(createMoment.diff(now));
            const remainingHours = remaining.hours();
            return remainingHours;
        },
        reset() {
            this.area = "",
                this.kind = "",
                this.load();
        },
        handleSelectionChange(val) {
            this.multipleSelection = val
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
        formatBuySell(row, column, cellValue) {
            if (cellValue === 'buy') {
                return '买入';
            } else if (cellValue === 'sell') {
                return '卖出';
            } else {
                return cellValue;
            }
        },
        tableRowClassName({ row, rowIndex }) {
            if (row.buySell === 'sell') {
                return 'warning-row';
            } else if(row.buySell === 'buy') {
                return 'success-row';
            }else return '';
        }
    }
}
</script>

<style>
.el-table .warning-row {
    background: oldlace;
}

.el-table .success-row {
    background: #f0f9eb;
}

.headerBg {
    background: #eee !important;
}
</style>
