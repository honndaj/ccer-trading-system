<template>
    <div>
        <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"
            @selection-change="handleSelectionChange">
            <el-table-column prop="id" label="申报编号" width="80"></el-table-column>
            <el-table-column prop="area" label="地区" width="140"></el-table-column>
            <el-table-column prop="kind" label="种类"></el-table-column>
            <el-table-column prop="price" label="申报价格"></el-table-column>
            <el-table-column prop="count" label="数量（吨）"></el-table-column>
            <el-table-column prop="remainTime" label="剩余时间"></el-table-column>
            <el-table-column label="操作" width="200" align="center">
                <template slot-scope="scope">
                    <el-button type="primary" @click="sell(scope.row)">卖出<i class="el-icon-sold-out"></i></el-button>
                </template>
            </el-table-column>
        </el-table>
        <div style="padding: 10px 0">
            <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="pageNum"
                :page-sizes="[2,5,10]" :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total">
            </el-pagination>
        </div>
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span>CCER买入</span>
                <el-button style="float: right; padding: 3px 0" type="text" @click="apply">申报买入</el-button>
            </div>
            <el-form>

                <el-row>
                    <el-col :span="12">
                        <el-form-item label="申报价格" style="width: 80%;">
                            <el-input v-model="form.price" style="width: 40%;">
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="地区">
                            <el-select clearable v-model="form.area" placeholder="请选择地区" style="width: 50%;">
                                <el-option v-for="item in areaNames" :key="item" :label="item" :value="item"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="申报数量" style="width: 80%;">
                            <el-input v-model="form.count" style="width: 40%;">
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="种类">
                            <el-select clearable v-model="form.kind" placeholder="请选择种类" style="width: 50%;">
                                <el-option v-for="item in kindNames" :key="item" :label="item" :value="item"></el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
        </el-card>
    </div>
</template>

<script>
import moment from 'moment';

export default {
    name: "User",
    data() {
        return {
            tableData: [],
            total: 0,
            pageNum: 1,
            pageSize: 10,
            form: {},
            dialogFormVisible: false,
            multipleSelection: [],
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
            this.request.get("/now/buy/page", {//获取所有买入申报
                params: {
                    pageNum: this.pageNum,
                    pageSize: this.pageSize,
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
        apply() {
            this.form.uid = this.uid
            this.request.post('/now/buy', this.form).then(res => {
                if (res.code == '200') {
                    this.$message.success("申报成功")
                    this.load()
                } else {
                    this.$message.error(res.msg)
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
        sell(row) {
            if(row.uid == this.uid) {
                this.$message.error("无法和自己进行交易")
            } else {
                this.request.get('/now/buy/trade',{
                params: {
                    //from表示ccer卖出方
                    id: row.id,
                    from: this.uid,
                    to: row.uid
                }}).then(res => {
                    if(res.code == '200') {
                        this.$message.success("交易成功")
                        this.load()
                    }else {
                        this.$message.error(res.msg)
                    }
                })
            }
        }
    }
}
</script>

<style>
.headerBg {
    background: #eee !important;
}

.text {
    font-size: 14px;
}

.item {
    margin-bottom: 18px;
}

.clearfix:before,
.clearfix:after {
    display: table;
    content: "";
}

.clearfix:after {
    clear: both
}

.box-card {
    width: 480px;
}
</style>
