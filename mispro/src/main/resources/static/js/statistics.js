var chartDom = document.getElementById('main');
var myChart = echarts.init(chartDom);
var option;
var correct = document.getElementById('correct').innerText;
var error = document.getElementById('incorrect').innerText;
var space = document.getElementById('space').innerText;

option = {
	legend: {
		top: 'bottom'
	},
	toolbox: {
		show: true,
		feature: {
			mark: {
				show: true
			},
			dataView: {
				show: true,
				readOnly: false
			},
			restore: {
				show: true
			},
			saveAsImage: {
				show: true
			}
		}
	},
	series: [{
		name: 'Nightingale Chart',
		type: 'pie',
		radius: [50, 250],
		center: ['50%', '50%'],
		roseType: 'area',
		itemStyle: {
			borderRadius: 8
		},
		data: [{
				value: correct,
				name: '正确'
			},
			{
				value: error,
				name: '错误'
			},
			{
				value: space,
				name: '未作答'
			}
		]
	}]
};

option && myChart.setOption(option);
