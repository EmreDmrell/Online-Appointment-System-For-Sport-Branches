console.log("loaded");

// var currentDate = document.querySelector(".time-slot");

$('.time-slot').each(function(i, obj) {
    console.log(obj.innerHTML);
    var time = change_array_to_time(obj.innerHTML);
    console.log(typeof obj.innerHTML);
    obj.innerHTML = time;
});

function change_array_to_time(time_array){
    var time = "";
    time_array = time_array.substring(1, time_array.length-1);
    if(time_array.length === 1 || time_array.length === 2){
        var time_array_plus_one = parseInt(time_array) + 1;
        time = time_array + ":00 - " + time_array_plus_one + ":00";
    }
    else{
        console.log(time_array);

        var text_array = time_array.split(" ");
        var first_time = text_array[0].substring(0, text_array[0].length-1);
        var last_time = text_array[text_array.length - 1];
        var last_time_plus_one = parseInt(last_time) + 1;
        time = first_time + ":00 - " + last_time_plus_one + ":00"
    }
    return time
}