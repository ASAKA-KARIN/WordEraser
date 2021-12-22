// import * as echarts from 'echarts';

var app = {};

var chartDom = document.getElementById('main');
var myChart = echarts.init(chartDom);A
var option;
const posList = [
	'left',
	'right',
	'top',
	'bottom',
	'inside',
	'insideTop',
	'insideLeft',
	'insideRight',
	'insideBottom',
	'insideTopLeft',
	'insideTopRight',
	'insideBottomLeft',
	'insideBottomRight'
];
app.configParameters = {
	rotate: {
		min: -90,
		max: 90
	},
	align: {
		options: {
			left: 'left',
			center: 'center',
			right: 'right'
		}
	},
	verticalAlign: {
		options: {
			top: 'top',
			middle: 'middle',
			bottom: 'bottom'
		}
	},
	position: {
		options: posList.reduce(function(map, pos) {
			map[pos] = pos;
			return map;
		}, {})
	},
	distance: {
		min: 0,
		max: 100
	}
};
app.config = {
	rotate: 90,
	align: 'left',
	verticalAlign: 'middle',
	position: 'insideBottom',
	distance: 15,
	onChange: function() {
		const labelOption = {
			rotate: app.config.rotate,
			align: app.config.align,
			verticalAlign: app.config.verticalAlign,
			position: app.config.position,
			distance: app.config.distance
		};
		myChart.setOption({
			series: [{
					label: labelOption
				},
				{
					label: labelOption
				},
				{
					label: labelOption
				},
				{
					label: labelOption
				}
			]
		});
	}
};
const labelOption = {
	show: true,
	position: app.config.position,
	distance: app.config.distance,
	align: app.config.align,
	verticalAlign: app.config.verticalAlign,
	rotate: app.config.rotate,
	formatter: '{c}  {name|{a}}',
	fontSize: 16,
	rich: {
		name: {}
	}
};
option = {
	tooltip: {
		trigger: 'axis',
		axisPointer: {
			type: 'shadow'
		}
	},
	legend: {
		data: ['总数', '正确数']
	},
	toolbox: {
		show: true,
		orient: 'vertical',
		left: 'right',
		top: 'center',
		feature: {
			mark: {
				show: true
			},
			dataView: {
				show: true,
				readOnly: false
			},
			magicType: {
				show: true,
				type: ['line', 'bar', 'stack']
			},
			restore: {
				show: true
			},
			saveAsImage: {
				show: true
			}
		}
	},
	xAxis: [{
		type: 'category',
		axisTick: {
			show: false
		},
		data: (function() {
			var arr = [];
			$.ajax({
				type: "post",
				async: false, //同步执行
				url: "/word/xAxis",
				data: {},
				dataType: "json", //返回数据形式为json
				success: function(result) {
					if (result) {
						for (var i = 0; i < result.length; i++) {
							console.log(result[i]);
							arr.push(result[i]);
						}
					}

				},
				error: function(errorMsg) {
					alert("不好意思，图表请求数据失败啦!");
					myChart.hideLoading();
				}
			})
			return arr;
		})(),
	}],
	yAxis: [{
		type: 'value'
	}],
	series: [{
			name: '总数',
			type: 'bar',
			barGap: 0,
			label: labelOption,
			emphasis: {
				focus: 'series'
			},
			data: (function() {
				var arr = [];
				$.ajax({
					type: "post",
					async: false, //同步执行
					url: "/word/total/",
					data: {},
					dataType: "json", //返回数据形式为json
					success: function(result) {
						if (result) {
							for (var i = 0; i < result.length; i++) {
								console.log(result[i]);
								arr.push(result[i]);
							}
						}

					},
					error: function(errorMsg) {
						alert("不好意思,图表请求数据失败啦!");
						myChart.hideLoading();
					}
				})
				return arr;
			})()
		},
		{
			name: '正确数',
			type: 'bar',
			label: labelOption,
			emphasis: {
				focus: 'series'
			},
			data: (function() {
				var arr = [];
				$.ajax({
					type: "post",
					async: false, //同步执行
					url: "/word/correct/",
					data: {},
					dataType: "json", //返回数据形式为json
					success: function(result) {
						if (result) {
							for (var i = 0; i < result.length; i++) {
								console.log(result[i]);
								arr.push(result[i]);
							}
						}

					},
					error: function(errorMsg) {
						alert("不好意思,图表请求数据失败啦!");
						myChart.hideLoading();
					}
				})
				return arr;
			})()
		},
	]
};

option && myChart.setOption(option);
