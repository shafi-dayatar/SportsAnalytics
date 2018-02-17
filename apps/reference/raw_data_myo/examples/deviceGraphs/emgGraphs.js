//This tells Myo.js to create the web sockets needed to communnicate with Myo Connect


Myo.on('connected', function(){
	console.log('Connected to myo');
	this.streamEMG(true);
	connected = true;
	Myo.on('emg', function(data){
		rawData = data;
	});
	console.log(rawData.toString());
	Myo.on('imu', function(imu_data, timestamp){
	//console.log(timestamp);
		//console.log(imu_data);
	})
	Myo.on('status', function(data, timestamp){
		console.log(data);
	//console.log(imu_data);
	})

	Myo.on('battery_status', function(data, timestamp){
		console.log(data);
	//console.log(imu_data);
	})

	/*setInterval(function(){
		updateGraph(rawData);
	}, 25);*/
})

Myo.on('disconnected', function(){
	Myo.off('emg');
	console.log(rawData.toString());
	Myo.off('imu')
	Myo.off('status')

	Myo.off('battery_status')
})


//This tells Myo.js to create the web sockets needed to communnicate with Myo Connect

var strokeType = "forehand"
var swingType = "topspin"
var connected = false;

$(document).ready(function() {
	$("#connect").click(function(){
		Myo.connect('com.myojs.emgGraphs');
		console.log(Myo)
	});

});

console.log(Myo)
var rawData = [0,0,0,0,0,0,0,0];
if(connected){
	Myo.on('emg', function(data){
		rawData = data;
	});
	console.log(rawData.toString());
}

if(connected){
Myo.on('imu', function(imu_data, timestamp){
	console.log(timestamp);
	//console.log(imu_data);
})
}


var range = 150;
var resolution = 50;
var emgGraphs;

var graphData= [
	Array.apply(null, Array(resolution)).map(Number.prototype.valueOf,0),
	Array.apply(null, Array(resolution)).map(Number.prototype.valueOf,0),
	Array.apply(null, Array(resolution)).map(Number.prototype.valueOf,0),
	Array.apply(null, Array(resolution)).map(Number.prototype.valueOf,0),
	Array.apply(null, Array(resolution)).map(Number.prototype.valueOf,0),
	Array.apply(null, Array(resolution)).map(Number.prototype.valueOf,0),
	Array.apply(null, Array(resolution)).map(Number.prototype.valueOf,0),
	Array.apply(null, Array(resolution)).map(Number.prototype.valueOf,0)
]

/*$(document).ready(function(){

	emgGraphs = graphData.map(function(podData, podIndex){
		return $('#pod' + podIndex).plot(formatFlotData(podData), {
			colors: ['#8aceb5'],
			xaxis: {
				show: false,
				min : 0,
				max : resolution
			},
			yaxis : {
				min : -range,
				max : range,
			},
			grid : {
				borderColor : "#427F78",
				borderWidth : 1
			}
		}).data("plot");
	});


});

var formatFlotData = function(data){
		return [data.map(function(val, index){
				return [index, val]
			})]
}


var updateGraph = function(emgData){

	graphData.map(function(data, index){
		graphData[index] = graphData[index].slice(1);
		graphData[index].push(emgData[index]);

		emgGraphs[index].setData(formatFlotData(graphData[index]));
		emgGraphs[index].draw();


	})

}




*/