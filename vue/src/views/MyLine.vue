<template>
    <div>
        <div id="line" style="width: 431px; height: 345px"></div>
    </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
    name: "MyLine",
    data() {
        return {
            lineData: [],

        }
    },
    created() {
    },
    mounted() {
        // 初始化折线图
        var chartDom = document.getElementById('line');
        var myChart = echarts.init(chartDom);

        // 请求数据
        this.request.get('/echarts/line').then(res => {
            if (res.code == '200') {
                this.lineData = res.data;
                // 处理数据并设置图表选项
                this.initIncomeChart(this.lineData, myChart);
            }
        });
    },
    methods: {
        initIncomeChart(rawData, chart) {
            console.log(rawData)
            const areas = ['北京', '天津', '上海', '深圳', '广州', '湖北', '重庆'];
            const datasetWithFilters = [];
            const seriesList = [];
            const xAxisData = rawData.map(item => item[0]); // Initialize xAxis data
            const filterDate = new Date('2022-04-01').getTime(); // Convert date to timestamp

            echarts.util.each(areas, function (area) {
                var datasetId = 'dataset_' + area;
                datasetWithFilters.push({
                    id: datasetId,
                    fromDatasetId: 'dataset_raw',
                    transform: {
                        type: 'filter',
                        config: {
                            and: [
                                { dimension: 'Date', gte: filterDate }, // Use timestamp for comparison
                                { dimension: 'Area', '=': area }
                            ]
                        }
                    }
                });
                seriesList.push({
                    type: 'line',
                    datasetId: datasetId,
                    showSymbol: false,
                    name: area,
                    endLabel: {
                        show: true,
                        formatter: function (params) {
                            return params.value[1] + ': ' + params.value[2];
                        }
                    },
                    labelLayout: {
                        moveOverlap: 'shiftY'
                    },
                    emphasis: {
                        focus: 'series'
                    }
                });
            });

            const option = {
                animationDuration: 10000,
                dataset: [
                    {
                        id: 'dataset_raw',
                        dimensions: ['Date', 'Area', 'AveragePrice'],
                        source: rawData
                    },
                    ...datasetWithFilters
                ],
                title: {
                    text: 'CCER交易数据'
                },
                tooltip: {
                    order: 'valueDesc',
                    trigger: 'axis'
                },
                xAxis: {
                    type: 'category',
                    nameLocation: 'middle',
                    data: xAxisData // Use xAxis data
                },
                yAxis: {
                    name: 'Average Price'
                },
                grid: {
                    right: 140
                },
                series: seriesList
            };

            chart.setOption(option);
        }
    }




}
</script>

<style></style>