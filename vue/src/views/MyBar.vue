<template>
    <div style="width: 200px">
        <el-card class="box-card">
            <div slot="header" class="clearfix">
                <span>选择CCER的地区和种类</span>
                <el-button style="float: right; padding: 3px 0" type="text" @click="submitAreaAndKind">提交</el-button>
            </div>
            <el-form>

                <el-row>
                    <el-form-item label="地区">
                        <el-select clearable v-model="form.area" placeholder="请选择地区" style="width: 50%;">
                            <el-option v-for="item in areaNames" :key="item" :label="item" :value="item"></el-option>
                        </el-select>
                    </el-form-item>
                </el-row>
                <el-row>
                    <el-form-item label="种类">
                        <el-select clearable v-model="form.kind" placeholder="请选择种类" style="width: 50%;">
                            <el-option v-for="item in kindNames" :key="item" :label="item" :value="item"></el-option>
                        </el-select>
                    </el-form-item>
                </el-row>
            </el-form>
        </el-card>
        <div id="barDay" style="width: 431px; height: 345px"></div>
        <div id="barMonth" style="width: 431px; height: 345px"></div>
    </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
    name: "MyBar",
    data() {
        return {
            form: {},
            areaNames: ['北京', '天津', '上海', '深圳', '广州', '湖北', '重庆'],
            kindNames: ['树林', '风能', '太阳能', '水电', '生物质发电']
        }
    },
    mounted() {
        var dayChartDom = document.getElementById('barDay');
        var dayChart = echarts.init(dayChartDom);
        var monthChartDom = document.getElementById('barMonth');
        var monthChart = echarts.init(monthChartDom);

        //day
        this.request.get('/echarts/barDay', {
            params: {
                area: '北京',
                kind: '树林'
            }
        }).then(res => {
            if (res.code == '200') {
                var dayOption = {
                    title: {
                        text: '最近一周各种类CCER交易总量',
                        left: 'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: res.data.map(item => item.name),
                    },
                    yAxis: {
                        type: 'value',
                    },
                    series: [
                        {
                            data: res.data,
                            type: 'bar',
                        },
                    ],
                };
                dayChart.setOption(dayOption);
            }
        })
        //month
        this.request.get('/echarts/barMonth', {
            params: {
                area: '北京',
                kind: '树林'
            }
        }).then(res => {
            if (res.code == '200') {
                var monthOption = {
                    title: {
                        text: '最近一年各种类CCER交易总量',
                        left: 'center'
                    },
                    xAxis: {
                        type: 'category',
                        data: res.data.map(item => item.name),
                    },
                    yAxis: {
                        type: 'value',
                    },
                    series: [
                        {
                            data: res.data,
                            type: 'bar',
                        },
                    ],
                };
                monthChart.setOption(monthOption);
            }
        })

    },
    methods: {
        submitAreaAndKind() {
            var dayChartDom = document.getElementById('barDay');
            var dayChart = echarts.init(dayChartDom);
            var monthChartDom = document.getElementById('barMonth');
            var monthChart = echarts.init(monthChartDom);
            //day
            this.request.get('/echarts/barDay', {
                params: {
                    area: this.form.area,
                    kind: this.form.kind
                }
            }).then(res => {
                if (res.code == '200') {
                    var dayOption = {
                        title: {
                            text: '最近一周各种类CCER交易总量',
                            left: 'center'
                        },
                        xAxis: {
                            type: 'category',
                            data: res.data.map(item => item.name),
                        },
                        yAxis: {
                            type: 'value',
                        },
                        series: [
                            {
                                data: res.data,
                                type: 'bar',
                            },
                        ],
                    };
                    dayChart.setOption(dayOption);
                }
            })
            //month
            this.request.get('/echarts/barMonth', {
                params: {
                    area: this.form.area,
                    kind: this.form.kind
                }
            }).then(res => {
                if (res.code == '200') {
                    var monthOption = {
                        title: {
                            text: '最近一年各种类CCER交易总量',
                            left: 'center'
                        },
                        xAxis: {
                            type: 'category',
                            data: res.data.map(item => item.name),
                        },
                        yAxis: {
                            type: 'value',
                        },
                        series: [
                            {
                                data: res.data,
                                type: 'bar',
                            },
                        ],
                    };
                    monthChart.setOption(monthOption);
                }
            })
        },

    }
}
</script>

<style></style>