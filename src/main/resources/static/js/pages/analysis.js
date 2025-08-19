document.addEventListener("DOMContentLoaded", function() {
	const area = document.querySelector(".user-dashboard");
	console.log(area);
	if(area){
		area.classList.add("overflow-manager");
	}
	
	enableDonutChart("#like_dislike_chart");
	enablePublicRadialBar("#publicRadialChart");
});
function enableDonutChart(chartSelector) {
	const chartData = JSON.parse(document.getElementById("chart-data").textContent);

	var options = {
		series: chartData.weekly,
		chart: {
			type: 'donut',
		},
		labels: ["Likes", "Dislikes"],
		colors: ["#4FD675", "#2B2B29"],
		legend: {
			position: "bottom"
		}
	};

	var chart = new ApexCharts(document.querySelector(chartSelector), options);
	chart.render();

	document.getElementById("chartFilter").addEventListener("change", function(e) {
		chart.updateSeries(chartData[e.target.value]);
	});
}
function enablePublicRadialBar(publicRadialBarSelector) {
	const chartData = JSON.parse(document.getElementById("public-report-chart-data").textContent);
	console.log(chartData);

	var options = {
		series: chartData.weekly,
		chart: {
			height: 350,
			type: 'radialBar',
		},
		plotOptions: {
			radialBar: {
				hollow: {
					size: '30%',
				},
				dataLabels: {
					name: {
						fontSize: '16px',
						show: true
					},
					value: {
						show: true,
						fontSize: '14px',
						formatter: function (val, opts) {
							return opts.w.config.series[opts.seriesIndex];
						}
					},
					total: {
						show: true,
						label: 'Total',
						formatter: function (w) {
							return w.config.series.reduce((a, b) => a + b, 0);
						}
					}
				}
			}
		},
		labels: ['Followings', 'Followers', 'Comments'],
		colors: ["#4FD675", "#2B2B29", "#FA7677"],
		legend: {
			show: true,
			position: 'bottom',
			fontSize: '14px',
			formatter: function (seriesName, opts) {
				return seriesName + ": " + opts.w.globals.series[opts.seriesIndex];
			}
		}
	};

	var chart = new ApexCharts(document.querySelector(publicRadialBarSelector), options);
	chart.render();

	document.getElementById("publicReportChartFilter").addEventListener("change", function (e) {
		chart.updateSeries(chartData[e.target.value]);
	});
}
