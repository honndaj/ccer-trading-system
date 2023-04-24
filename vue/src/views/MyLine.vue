<template>
    <div>
        <el-row>
            <el-col :span="10"><div id="line" style="width: 500px; height: 400px"></div></el-col>
            <el-col :span="3">
                <div>
                    <el-row v-for="(row, index) in tableData" :key="index">
                        <el-col>
                            <el-card>
                                <div>{{ row.area }} - {{ row.price }}</div>
                            </el-card>
                        </el-col>
                    </el-row>
                </div>
            </el-col>
        </el-row>
    </div>
</template>
  
<script>
import * as echarts from 'echarts';

export default {
    name: 'MyLine',
    data() {
        return {
            lineData: [],
            tableData: [],
            bj: [],
            tj: [],
            sh: [],
            sz: [],
            gz: [],
            hb: [],
            cq: [],

        };
    },
    created() {
        this.fetchTableData();
        this.timer = setInterval(this.fetchTableData, 3000); // 每隔3秒请求新数据
    },
    beforeDestroy() {
        clearInterval(this.timer); // 清除定时器
    },
    mounted() {
        var lineChartDom = document.getElementById('line');
        var lineChart = echarts.init(lineChartDom);

        this.request.get('/echarts/line').then((res) => {
            if (res.code == '200') {
                this.lineData = res.data;
                this.bj = this.lineData.filter(v => v[1] === '北京');
                this.tj = this.lineData.filter(v => v[1] === '天津');
                this.sh = this.lineData.filter(v => v[1] === '上海');
                this.sz = this.lineData.filter(v => v[1] === '深圳');
                this.gz = this.lineData.filter(v => v[1] === '广州');
                this.hb = this.lineData.filter(v => v[1] === '湖北');
                this.cq = this.lineData.filter(v => v[1] === '重庆');
                var option;


                option = {
                    // title: {
                    //     text: '各地区CCER平均价格每日变化图'
                    // },
                    tooltip: {
                        trigger: 'axis'
                    },
                    legend: {
                        data: ['北京', '天津', '上海', '深圳', '广州', '湖北', '重庆']
                    },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    toolbox: {
                        feature: {
                            saveAsImage: {}
                        }
                    },
                    xAxis: {
                        type: 'category',
                        // boundaryGap: false,
                        data: this.lineData.filter(v => v[1] === '北京').map(v => v[0])
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [
                        { data: this.bj.map(v => v[2]), type: 'line', name: '北京' },
                        { data: this.tj.map(v => v[2]), type: 'line', name: '天津' },
                        { data: this.sh.map(v => v[2]), type: 'line', name: '上海' },
                        { data: this.sz.map(v => v[2]), type: 'line', name: '深圳' },
                        { data: this.gz.map(v => v[2]), type: 'line', name: '广州' },
                        { data: this.hb.map(v => v[2]), type: 'line', name: '湖北' },
                        { data: this.cq.map(v => v[2]), type: 'line', name: '重庆' },
                    ]
                };
                lineChart.setOption(option)
            }
        });
    },
    methods: {
        async fetchTableData() {
            try {
                console.log(this.tableData);
                const response = await this.request.get('/echarts/table');
                this.tableData = response.data;
            } catch (error) {
                console.error('Error fetching table data:', error);
            }
        }
    }
};
</script>

<style>
table {
    width: 100%;
    border-collapse: collapse;
}

th,
td {
    padding: 8px;
    text-align: left;
    border-bottom: 1px solid #ccc;
}

th {
    background-color: #f2f2f2;
}

.table-enter-active,
.table-leave-active {
    transition: all 0.5s;
}

.table-enter,
.table-leave-to {
    opacity: 0;
    transform: translateY(-20px);
}
</style>
