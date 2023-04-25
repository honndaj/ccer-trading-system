<template>
    <div>
        <el-row>
            <el-col :span="10">
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
                        <el-form-item style="margin-left: 115px">
                            <el-button type="primary" @click="save">提交修改</el-button>
                        </el-form-item>
                    </el-form>
                </el-card>
            </el-col>
            <el-col style="margin-left: 12px;" :span="3">
                <el-row>
                    <el-card shadow="always">
                        剩余可用资金：<br />{{ this.form.money }} ¥
                    </el-card>
                </el-row>
            </el-col>
            <el-col style="margin-left: 30px;" :span="6">
                <el-row>
                    <el-col>
                        <div id="areaPie" style="width: 431px; height: 345px"></div>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col>
                        <div id="kindPie" style="width: 431px; height: 345px"></div>
                    </el-col>
                </el-row>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
    name: "Person",
    data() {
        return {
            form: {},
            user: localStorage.getItem("user") ? JSON.parse(localStorage.getItem("user")) : {},
        }
    },
    created() {
        this.request.get("/user/username/" + this.user.username).then(res => {
            if (res.code == '200') {
                this.form = res.data
            }
        })
    },
    mounted() {
        //地区占比饼图
        var areaPieDom = document.getElementById('areaPie');
        var areaPieChart = echarts.init(areaPieDom);

        this.request.get("/echarts/ccerArea/" + this.user.id).then(res => {
            if (res.code == '200') {
                this.areaData = res.data
                console.log(this.areaData);

            }
            var areaPieOption = {
                title: {
                    text: '所拥有各地区CCER量',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left'
                },
                series: [
                    {
                        type: 'pie',
                        radius: '50%',
                        data: this.areaData,
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
            areaPieChart.setOption(areaPieOption)

        })


        //种类占比饼图
        var kindPieDom = document.getElementById('kindPie');
        var kindPieChart = echarts.init(kindPieDom);
        this.request.get("/echarts/ccerKind/" + this.user.id).then(res => {
            if (res.code == '200') {
                this.kindData = res.data
            }
            var kindPieOption = {
                title: {
                    text: '所拥有各种类CCER量',
                    left: 'center'
                },
                tooltip: {
                    trigger: 'item'
                },
                legend: {
                    orient: 'vertical',
                    left: 'left'
                },
                series: [
                    {
                        type: 'pie',
                        radius: '50%',
                        data: this.kindData,
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)'
                            }
                        }
                    }
                ]
            };
            kindPieChart.setOption(kindPieOption)
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